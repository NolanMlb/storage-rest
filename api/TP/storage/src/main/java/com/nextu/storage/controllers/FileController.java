package com.nextu.storage.controllers;

import com.nextu.storage.dto.UserGetDTO;
import com.nextu.storage.services.FileService;
import com.nextu.storage.services.StoragService;
import com.nextu.storage.utils.FileUtils;
import com.nextu.storage.utils.MimeTypeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
@Slf4j
public class FileController {
    private final FileService fileService;
    private final StoragService storagService;
    @GetMapping(value = "/{name}")
    public ResponseEntity<?> find(@PathVariable String name){
        if(fileService.checkIfFileExist(name)){
            try {
                File file = this.storagService.load(name);
                var extension = FileUtils.getExtension(file.getName());
                Path path = Paths.get(file.getAbsolutePath());
                ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
                return ResponseEntity
                        .ok()
                        .contentLength(path.toFile().length())
                        .contentType(MediaType.parseMediaType(MimeTypeUtils.getMimeType(extension)))
                        .body(resource);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
