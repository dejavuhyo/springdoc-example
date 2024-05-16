package com.example.springdoc.mapper;

import com.example.springdoc.dto.ClassDto;
import com.example.springdoc.dto.EmployerDto;

import java.util.List;

public interface BackOfficeMapper {
    List<ClassDto> getClassList();

    int chkRegClass(ClassDto classDto);

    int regClass(ClassDto classDto);

    List<EmployerDto> getSubscribeList(String classId);
}
