package com.example.springdoc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubscribeDto {
    private String classId;
    private String employerId;
    private String status;
}
