package com.example.springdoc.mapper;

import com.example.springdoc.dto.ClassDto;
import com.example.springdoc.dto.RankingDto;
import com.example.springdoc.dto.SubscribeDto;

import java.util.List;

public interface FrontMapper {
    List<ClassDto> getPossibleClassList();

    int regSubscribe(SubscribeDto subscribeDto);

    int chkSubscribe(SubscribeDto subscribeDto);

    List<ClassDto> getSubscribeList(String employerId);

    int updSubscribe(SubscribeDto subscribeDto);

    int getSubscribeCnt(SubscribeDto subscribeDto);

    int getMaxPeopleCnt(SubscribeDto subscribeDto);

    List<RankingDto> getPopularClassList();
}
