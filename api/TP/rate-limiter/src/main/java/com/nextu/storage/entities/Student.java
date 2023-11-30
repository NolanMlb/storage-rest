package com.nextu.storage.entities;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Student {
    private final String fristName;
    private final int  rollNo;
}
