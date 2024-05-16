package com.example.springdoc;

import com.example.springdoc.dto.ClassDto;
import com.example.springdoc.service.BackOfficeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BackOfficeServiceTest {

    @Autowired
    BackOfficeService backOfficeService;

    ClassDto classDto = null;

    @BeforeEach
    void beforeEach() {
        classDto = new ClassDto();
        classDto.setSpeaker("안발표");
        classDto.setLocation("강연장1");
        classDto.setMaxPeople("100");
        classDto.setStartTime("2024-05-30 09:00:00");
        classDto.setEndTime("2024-05-30 11:00:00");
        classDto.setContent("강연내용1");
    }

    @Test
    @DisplayName("강연목록 테스트")
    void getClassListSuccess() {
        int givenCnt = 7;
        int getClassListCnt = backOfficeService.getClassList().size();
        assertThat(givenCnt).isEqualTo(getClassListCnt);
    }

    @Test
    @DisplayName("강연등록 테스트")
    void getClassListFail() {
        int givenCnt = 1;
        int regClassCnt = backOfficeService.regClass(classDto);
        assertThat(givenCnt).isEqualTo(regClassCnt);
    }

    @Test
    @DisplayName("강연신청자목록 테스트")
    void getSubscribeList() {
        int givenCnt = 1;
        int getSubscribeListCnt = backOfficeService.getSubscribeList("1").size();
        assertThat(givenCnt).isEqualTo(getSubscribeListCnt);
    }
}
