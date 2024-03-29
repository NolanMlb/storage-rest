package com.nextu.storage.controllers;

import com.nextu.storage.dto.BucketDTO;
import com.nextu.storage.entities.FileData;
import com.nextu.storage.entities.User;
import com.nextu.storage.services.BucketService;
import com.nextu.storage.services.StoragService;
import com.nextu.storage.services.UserDetailsImpl;
import com.nextu.storage.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.nextu.storage.entities.Bucket;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/buckets")
@Slf4j
public class BucketController {
    private final BucketService bucketService;
    private final UserService userService;
    private final StoragService storagService;

    @PostMapping(value = "/", produces = { "application/json", "application/xml" })
    public ResponseEntity<?> create(@RequestBody BucketDTO bucketDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findUserById(userDetails.getId());
        bucketService.create(bucketDTO);
        Bucket bucket = bucketService.findById(bucketDTO.getId());
        user.addBucket(bucket);
        userService.update(user);
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/{idBucket}", produces = { "application/json", "application/xml" })
    public ResponseEntity<Bucket> getFilesByIdBucket(@PathVariable String idBucket) {
        return ResponseEntity.ok(bucketService.findById(idBucket));
    }

    @GetMapping(value = "/", produces = { "application/json", "application/xml" })
    public ResponseEntity<List<Bucket>> getBuckets() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findUserById(userDetails.getId());
        return ResponseEntity.ok(user.getBuckets());
    }

    @PostMapping(value = "/{id}/files")
    public ResponseEntity<List<FileData>> saveFiles(@PathVariable String id, @RequestParam("files") MultipartFile[] files) {
        List<FileData> filesData = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                String fileName = storagService.save(file);
                FileData fileData = bucketService.saveFileByBucketId(id, fileName);
                filesData.add(fileData);
            }
            return ResponseEntity.ok(filesData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @PutMapping(value = "/{bucketId}", produces = { "application/json", "application/xml" })
    public ResponseEntity<?> update(@RequestBody BucketDTO bucketDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findUserById(userDetails.getId());
        bucketService.create(bucketDTO);
        Bucket bucket = bucketService.findById(bucketDTO.getId());
        user.addBucket(bucket);
        userService.update(user);
        return ResponseEntity.ok(null);
    }
}
