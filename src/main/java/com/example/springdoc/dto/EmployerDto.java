package com.example.springdoc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployerDto {
    private String employerId;
    private String name;
    private String status;
}
