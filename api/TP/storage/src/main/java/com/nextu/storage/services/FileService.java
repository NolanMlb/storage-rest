package com.nextu.storage.services;

import com.nextu.storage.entities.FileData;
import com.nextu.storage.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileService {
    private final FileRepository fileRepository;
    public boolean checkIfFileExist(String fileName){
        return fileRepository.findByLabel(fileName) !=null?true:false;
    }
}
