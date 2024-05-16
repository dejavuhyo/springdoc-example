package com.example.springdoc.service.impl;

import com.example.springdoc.dto.ClassDto;
import com.example.springdoc.dto.RankingDto;
import com.example.springdoc.dto.SubscribeDto;
import com.example.springdoc.mapper.FrontMapper;
import com.example.springdoc.service.FrontService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FrontServiceImpl implements FrontService {

    @Resource
    private FrontMapper frontMapper;

    @Override
    public List<ClassDto> getPossibleClassList() {
        return frontMapper.getPossibleClassList();
    }

    @Override
    public synchronized int regSubscribe(SubscribeDto subscribeDto) {
        int chkSubscribe = frontMapper.chkSubscribe(subscribeDto);
        if (chkSubscribe != 0) {
            return 0;
        } else {
            int subscribeCnt = frontMapper.getSubscribeCnt(subscribeDto);
            int maxPeopleCnt = frontMapper.getMaxPeopleCnt(subscribeDto);
            if (subscribeCnt >= maxPeopleCnt) {
                return 2;
            } else {
                return frontMapper.regSubscribe(subscribeDto);
            }
        }
    }

    @Override
    public List<ClassDto> getSubscribeList(String employerId) {
        return frontMapper.getSubscribeList(employerId);
    }

    @Override
    public int updSubscribe(SubscribeDto subscribeDto) {
        return frontMapper.updSubscribe(subscribeDto);
    }

    @Override
    public List<RankingDto> getPopularClassList() {
        return frontMapper.getPopularClassList();
    }
}
