package com.example.springdoc.service;

import com.example.springdoc.dto.ClassDto;
import com.example.springdoc.dto.RankingDto;
import com.example.springdoc.dto.SubscribeDto;

import java.util.List;

public interface FrontService {
    List<ClassDto> getPossibleClassList();

    int regSubscribe(SubscribeDto subscribeDto);

    List<ClassDto> getSubscribeList(String employerId);

    int updSubscribe(SubscribeDto subscribeDto);

    List<RankingDto> getPopularClassList();
}
