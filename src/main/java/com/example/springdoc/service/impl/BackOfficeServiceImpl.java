package com.example.springdoc.service.impl;

import com.example.springdoc.dto.ClassDto;
import com.example.springdoc.dto.EmployerDto;
import com.example.springdoc.mapper.BackOfficeMapper;
import com.example.springdoc.service.BackOfficeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackOfficeServiceImpl implements BackOfficeService {
    @Resource
    private BackOfficeMapper backOfficeMapper;

    @Override
    public List<ClassDto> getClassList() {
        return backOfficeMapper.getClassList();
    }

    @Override
    public synchronized int regClass(ClassDto classDto) {
        int chkRegClass = backOfficeMapper.chkRegClass(classDto);
        if (chkRegClass == 0) {
            return backOfficeMapper.regClass(classDto);
        } else {
            return 2;
        }
    }

    @Override
    public List<EmployerDto> getSubscribeList(String classId) {
        return backOfficeMapper.getSubscribeList(classId);
    }
}
