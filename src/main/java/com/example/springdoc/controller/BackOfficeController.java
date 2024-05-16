package com.example.springdoc.controller;

import com.example.springdoc.dto.ClassDto;
import com.example.springdoc.dto.EmployerDto;
import com.example.springdoc.dto.Message;
import com.example.springdoc.dto.StatusEnum;
import com.example.springdoc.service.BackOfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "BackOfficeController", description = "BackOffice - 강연목록, 강연등록, 강연신청자목록")
@RestController
@RequestMapping("/backoffice")
public class BackOfficeController {

    public static final String SERVER_ERROR = "서버 오류";

    @Autowired
    private BackOfficeService backOfficeService;

    @Operation(summary = "강연목록(전체강연목록)")
    @ApiResponse(responseCode = "200", description = "강연목록 조회", content = @Content(schema = @Schema(implementation = ClassDto.class)))
    @GetMapping("/classes")
    public ResponseEntity<Message> getClassList() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Message message = new Message();

        try {
            List<ClassDto> getClassList = backOfficeService.getClassList();
            if (CollectionUtils.isNotEmpty(getClassList)) {
                message.setStatus(StatusEnum.OK);
                message.setData(getClassList);
                message.setMessage("강연목록 조회 성공");
            } else {
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage("강연목록 조회 실패");
            }
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage(SERVER_ERROR);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "강연등록(강연자, 강연장, 신청인원, 강연시간, 강연내용)")
    @Parameters(value = {
            @Parameter(name = "speaker", description = "강연자"),
            @Parameter(name = "location", description = "강연장"),
            @Parameter(name = "maxPeople", description = "신청인원"),
            @Parameter(name = "startTime", description = "강연시작시간"),
            @Parameter(name = "endTime", description = "강연종료시간"),
            @Parameter(name = "content", description = "강연내용")
    })
    @ApiResponse(responseCode = "200", description = "강연 등록", content = @Content(schema = @Schema(implementation = ClassDto.class)))
    @PostMapping("/class")
    public ResponseEntity<Message> regClass(@RequestBody ClassDto classDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Message message = new Message();

        try {
            int regClass = backOfficeService.regClass(classDto);
            if (regClass == 1) {
                message.setStatus(StatusEnum.OK);
                message.setData(classDto);
                message.setMessage("강연등록 성공");
            } else if (regClass == 2) {
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setData(classDto);
                message.setMessage("강연 장소 or 시간 겹침");
            } else {
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage("강연등록 실패");
            }
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage(SERVER_ERROR);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "강연신청자목록(강연별 신청한 사번 목록)")
    @Parameters(value = {
            @Parameter(name = "classId", description = "강연ID")
    })
    @ApiResponse(responseCode = "200", description = "강연신청자목록 조회", content = @Content(schema = @Schema(implementation = EmployerDto.class)))
    @GetMapping("/subscribe/{classId}")
    public ResponseEntity<Message> getSubscribeList(@PathVariable String classId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Message message = new Message();

        try {
            List<EmployerDto> getSubscribeList = backOfficeService.getSubscribeList(classId);
            if (CollectionUtils.isNotEmpty(getSubscribeList)) {
                message.setStatus(StatusEnum.OK);
                message.setData(getSubscribeList);
                message.setMessage("강연신청자목록 조회 성공");
            } else {
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage("강연신청자목록 조회 실패");
            }
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage(SERVER_ERROR);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
