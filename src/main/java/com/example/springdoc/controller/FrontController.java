package com.example.springdoc.controller;

import com.example.springdoc.dto.ClassDto;
import com.example.springdoc.dto.Message;
import com.example.springdoc.dto.RankingDto;
import com.example.springdoc.dto.StatusEnum;
import com.example.springdoc.dto.SubscribeDto;
import com.example.springdoc.service.FrontService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "FrontController", description = "Front - 강연목록, 강연신청, 신청내역조회, 신청한강연취소, 실시간인기강연")
@RestController
@RequestMapping("/front")
public class FrontController {

    public static final String SERVER_ERROR = "서버 오류";

    @Autowired
    private FrontService frontService;

    @Operation(summary = "강연목록(신청 가능한 시점부터 강연 시작 시간 1일 후까지 노출)")
    @ApiResponse(responseCode = "200", description = "강연목록 조회", content = @Content(schema = @Schema(implementation = ClassDto.class)))
    @GetMapping("/possibleClass")
    public ResponseEntity<Message> getPossibleClassList() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Message message = new Message();

        try {
            List<ClassDto> getPossibleClassList = frontService.getPossibleClassList();
            if (CollectionUtils.isNotEmpty(getPossibleClassList)) {
                message.setStatus(StatusEnum.OK);
                message.setData(getPossibleClassList);
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

    @Operation(summary = "강연신청(사번입력, 같은 강연 중복 신청 제한)")
    @Parameters(value = {
            @Parameter(name = "employerId", description = "사번"),
            @Parameter(name = "classId", description = "강연ID")
    })
    @ApiResponse(responseCode = "200", description = "강연신청 등록", content = @Content(schema = @Schema(implementation = SubscribeDto.class)))
    @PostMapping("/subscribe")
    public ResponseEntity<Message> regSubscribe(@RequestBody SubscribeDto subscribeDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Message message = new Message();

        try {
            subscribeDto.setStatus("1");
            int regSubscribe = frontService.regSubscribe(subscribeDto);
            if (regSubscribe == 1) {
                message.setStatus(StatusEnum.OK);
                message.setData(subscribeDto);
                message.setMessage("강연신청 등록 성공");
            } else if (regSubscribe == 2) {
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setData(subscribeDto);
                message.setMessage("신청가능 인원이 없음");
            } else {
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage("중복 신청 제한");
            }
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage(SERVER_ERROR);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "신청내역조회(사번입력)")
    @Parameters(value = {
            @Parameter(name = "employerId", description = "사번")
    })
    @ApiResponse(responseCode = "200", description = "신청내역 조회", content = @Content(schema = @Schema(implementation = ClassDto.class)))
    @GetMapping("/subscribe/{employerId}")
    public ResponseEntity<Message> getSubscribeList(@PathVariable String employerId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Message message = new Message();

        try {
            List<ClassDto> getSubscribeList = frontService.getSubscribeList(employerId);
            if (CollectionUtils.isNotEmpty(getSubscribeList)) {
                message.setStatus(StatusEnum.OK);
                message.setData(getSubscribeList);
                message.setMessage("신청내역조회 성공");
            } else {
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage("신청내역조회 실패");
            }
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage(SERVER_ERROR);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "신청한강연취소")
    @Parameters(value = {
            @Parameter(name = "employerId", description = "사번"),
            @Parameter(name = "classId", description = "강연ID")
    })
    @ApiResponse(responseCode = "200", description = "신청한강연 취소", content = @Content(schema = @Schema(implementation = SubscribeDto.class)))
    @PutMapping("/subscribe")
    public ResponseEntity<Message> updSubscribe(@RequestBody SubscribeDto subscribeDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Message message = new Message();

        try {
            subscribeDto.setStatus("0");
            int updSubscribe = frontService.updSubscribe(subscribeDto);
            if (updSubscribe == 1) {
                message.setStatus(StatusEnum.OK);
                message.setData(subscribeDto);
                message.setMessage("강연취소 성공");
            } else {
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage("강연취소 실패");
            }
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage(SERVER_ERROR);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "실시간 인기강연")
    @ApiResponse(responseCode = "200", description = "실시간 인기강연 조회", content = @Content(schema = @Schema(implementation = RankingDto.class)))
    @GetMapping("/popularClass")
    public ResponseEntity<Message> getPopularClassList() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Message message = new Message();

        try {
            List<RankingDto> getPopularClassList = frontService.getPopularClassList();
            if (CollectionUtils.isNotEmpty(getPopularClassList)) {
                message.setStatus(StatusEnum.OK);
                message.setData(getPopularClassList);
                message.setMessage("인기강연 조회 성공");
            } else {
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage("인기강연 조회 실패");
            }
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            message.setMessage(SERVER_ERROR);
            return new ResponseEntity<>(message, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
