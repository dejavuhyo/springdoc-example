package com.example.springdoc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassDto {
    private String classId;
    private String speaker;
    private String location;
    private String maxPeople;
    private String content;
    private String startTime;
    private String endTime;
}
