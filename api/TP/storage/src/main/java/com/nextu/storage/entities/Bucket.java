package com.nextu.storage.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.OffsetDateTime;
import java.util.List;

@Document
public class Bucket {
    @Id
    private String id;
    private String label;
    private String description;
    private OffsetDateTime dateCreated;
    @DocumentReference
    private List<FileData> files;
}
