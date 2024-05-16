package com.example.springdoc;

import com.example.springdoc.dto.SubscribeDto;
import com.example.springdoc.service.FrontService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class FrontServiceTest {

    @Autowired
    FrontService frontService;

    SubscribeDto subscribeDto = null;

    @BeforeEach
    void beforeEach() {
        subscribeDto = new SubscribeDto();
        subscribeDto.setClassId("1");
        subscribeDto.setEmployerId("10001");
    }

    @Test
    @DisplayName("강연목록 테스트")
    void getPossibleClassList() {
        int givenCnt = 5;
        int getPossibleClassListCnt = frontService.getPossibleClassList().size();
        assertThat(givenCnt).isEqualTo(getPossibleClassListCnt);
    }

    @Test
    @DisplayName("강연신청 테스트")
    void regSubscribe() {
        int givenCnt = 1;
        int regSubscribeCnt = frontService.regSubscribe(subscribeDto);
        assertThat(givenCnt).isEqualTo(regSubscribeCnt);
    }

    @Test
    @DisplayName("신청내역조회 테스트")
    void getSubscribeList() {
        int givenCnt = 1;
        int getSubscribeListCnt = frontService.getSubscribeList("1").size();
        assertThat(givenCnt).isEqualTo(getSubscribeListCnt);
    }

    @Test
    @DisplayName("신청한강연취소 테스트")
    void updSubscribe() {
        int givenCnt = 1;
        int updSubscribeCnt = frontService.updSubscribe(subscribeDto);
        assertThat(givenCnt).isEqualTo(updSubscribeCnt);
    }

    @Test
    @DisplayName("실시간 인기강연 테스트")
    void getPopularClassList() {
        int givenCnt = 3;
        int getPopularClassListCnt = frontService.getPopularClassList().size();
        assertThat(givenCnt).isEqualTo(getPopularClassListCnt);
    }
}
