package com.example.springdoc.service;

import com.example.springdoc.dto.ClassDto;
import com.example.springdoc.dto.EmployerDto;

import java.util.List;

public interface BackOfficeService {
    List<ClassDto> getClassList();

    int regClass(ClassDto classDto);

    List<EmployerDto> getSubscribeList(String classId);
}
