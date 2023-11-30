package com.nextu.storage.services;

import com.nextu.storage.dto.UserGetDTO;
import com.nextu.storage.entities.FileData;
import com.nextu.storage.entities.User;
import com.nextu.storage.repository.FileRepository;
import com.nextu.storage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    public UserGetDTO create(User user){
        User userAfterSave = userRepository.save(user);
        UserGetDTO userGetDTO = getUserGetDTO(userAfterSave);
        return userGetDTO;
    }
    public UserGetDTO findById(String id){
        User user = userRepository.findById(id).orElse(null);
        if(user==null){
            return null;
        }
        return getUserGetDTO(user);
    }
    public void saveFileByUserId(String userId,String fileName) throws Exception {
        User user = userRepository.findById(userId).orElse(null);
        if(user!=null){
            FileData file = new FileData();
            file.setLabel(fileName);
            file.setDescription(fileName);
            FileData fileSaved = fileRepository.save(file);
            user.addFile(fileSaved);
            userRepository.save(user);
        }else{
            throw new Exception("save file for the current user id"+userId+" encountered an error");
        }

    }
    public void deleteById(String id){
        User user = userRepository.findById(id).orElse(null);
        if(user!=null){
            userRepository.delete(user);
        }
    }
    private static UserGetDTO getUserGetDTO(User userAfterSave) {
        UserGetDTO userGetDTO = new UserGetDTO();
        userGetDTO.setId(userAfterSave.getId());
        userGetDTO.setFirstName(userAfterSave.getFirstName());
        userGetDTO.setLastName(userAfterSave.getLastName());
        userGetDTO.setLogin(userAfterSave.getLogin());
        return userGetDTO;
    }

}
