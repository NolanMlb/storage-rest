package com.nextu.storage.services;

import com.nextu.storage.dto.BucketDTO;
import com.nextu.storage.entities.Bucket;
import com.nextu.storage.entities.FileData;
import com.nextu.storage.entities.User;
import com.nextu.storage.repository.BucketRepository;
import com.nextu.storage.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@RequiredArgsConstructor
@Slf4j
@Service
public class BucketService {
    private final BucketRepository bucketRepository;
    private final FileRepository fileRepository;

    public BucketDTO create(BucketDTO bucketDTO) {
        Bucket bucket = new Bucket();
        bucket.setLabel(bucketDTO.getLabel());
        bucket.setDescription(bucketDTO.getDescription());
        Bucket bucketAfterSave = bucketRepository.save(bucket);
        bucketDTO.setId(bucketAfterSave.getId());
        return bucketDTO;
    }

    public Bucket findById(String id) {
        return bucketRepository.findById(id).orElseGet(null);
    }

    public FileData saveFileByBucketId(String bucketId,String fileName) throws Exception {
        Bucket bucket = bucketRepository.findById(bucketId).orElse(null);
        if(bucket!=null){
            FileData file = new FileData();
            file.setLabel(fileName);
            file.setDescription(fileName);
            FileData fileSaved = fileRepository.save(file);
            bucket.addFile(fileSaved);
            bucketRepository.save(bucket);
            return fileSaved;
        }else{
            throw new Exception("save file for the current user id"+bucketId+" encountered an error");
        }
    }
}
