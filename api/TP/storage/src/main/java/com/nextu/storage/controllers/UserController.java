package com.nextu.storage.controllers;

import com.nextu.storage.dto.UserCreateDTO;
import com.nextu.storage.dto.UserGetDTO;
import com.nextu.storage.entities.User;
import com.nextu.storage.exceptions.FileContentException;
import com.nextu.storage.services.StoragService;
import com.nextu.storage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class UserController {
    private final UserService userService;
    private final StoragService storagService;
    private final PasswordEncoder encoder;

    @PostMapping(value = "/",produces = { "application/json", "application/xml" })
    public ResponseEntity<UserGetDTO> create(@RequestBody UserCreateDTO userCreateDTO){
        User user = new User();
        user.setFirstName(userCreateDTO.getFirstName());
        user.setLastName(userCreateDTO.getLastName());
        user.setLogin(userCreateDTO.getLogin());
        user.setPassword(encoder.encode(userCreateDTO.getPassword()));
        return ResponseEntity.ok(userService.create(user));
    }

    @PostMapping(value = "/{id}/file")
    public ResponseEntity<String> saveFile(@PathVariable String id,@RequestParam MultipartFile file){
        String messageError = "";
        try{
            String fileName = storagService.save(file);
            userService.saveFileByUserId(id,fileName);
            return ResponseEntity.ok(fileName);
        } catch (Exception e){
            messageError = e.getMessage();
        }
        return ResponseEntity.badRequest().body(messageError);
    }


    @PatchMapping(value = "/{id}",produces = { "application/json", "application/xml" })
    public ResponseEntity<UserGetDTO> update(@PathVariable String id, @RequestBody UserCreateDTO userCreateDTO){
        User user = new User();
        user.setFirstName(userCreateDTO.getFirstName());
        user.setLastName(userCreateDTO.getLastName());
        user.setPassword(userCreateDTO.getPassword());
        return ResponseEntity.ok(userService.create(user));
    }
    @GetMapping(value = "/{id}",produces = { "application/json", "application/xml" })
    public ResponseEntity<UserGetDTO> find(@PathVariable String id){
        UserGetDTO userGetDTO = userService.findById(id);
        if(userGetDTO!=null){
            return ResponseEntity.ok(userGetDTO);
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
