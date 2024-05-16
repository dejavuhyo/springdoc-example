# Spring Boot API Springdoc Swagger UI Example

## 1. 개발언어
* OpenJDK 17

## 2. 프레임워크
* Spring Boot 3.2.5
* MyBatis 3.0.3
* lombok 1.18.32
* commons-lang3 3.14.0
* commons-collections4 4.4
* log4jdbc 1.16
* log4j 2.23.1
* springdoc 2.5.0

## 3. RDBMS
* PostgreSQL 15.6

## 4. DB 설계
[직원 테이블] - [직원/강연 테이블] - [강연 테이블]

* 강연 테이블
```sql
create table class_tb
(
    speaker    varchar(20) not null,
    location   varchar(100),
    max_people varchar(10),
    content    varchar(1000),
    class_id   varchar(10) not null
        constraint class_tb_pk
            primary key,
    start_time timestamp,
    end_time   timestamp
);

comment on table class_tb is '강의';
comment on column class_tb.speaker is '강연자';
comment on column class_tb.location is '강연장';
comment on column class_tb.max_people is '신청인원';
comment on column class_tb.content is '강연내용';
comment on column class_tb.class_id is '강연ID';
comment on column class_tb.start_time is '강연시작시간';
comment on column class_tb.end_time is '강연종료시간';
```

* 직원 테이블
```sql
create table employer_tb
(
    employer_id varchar(5) not null
        constraint employer_tb_pk
            primary key,
    name        varchar(10)
);

comment on table employer_tb is '사원';
comment on column employer_tb.employer_id is '사원ID';
comment on column employer_tb.name is '사원이름';
```

* 직원/강연 테이블
```sql
create table class_employer_tb
(
    status         varchar(1)  not null,
    class_id       varchar(10) not null
        constraint class_employer_tb_class_tb_class_id_fk
            references class_tb,
    employer_id    varchar(5)  not null
        constraint class_employer_tb_employer_tb_employer_id_fk
            references employer_tb,
    subscribe_date timestamp default now(),
    cancel_date    timestamp,
    constraint class_employer_tb_pk
        primary key (class_id, employer_id, status)
);

comment on table class_employer_tb is '강연신청자';
comment on column class_employer_tb.status is '상태 0:취소, 1:신청';
comment on column class_employer_tb.subscribe_date is '강연신청일자';
```

## 5. 이슈사항

### 1) BackOffice - 강연등록 
* 기존에 등록된 강연과 같은 시간 및 장소 여부를 확인하여 강연 등록이 가능하도록 하였음
* 강연등록시 지나간 날짜 등록은 화면에서 처리해야됨

### 2) Front - 강연신청
* 강연신청 최대 인원을 확인하여 강연 신청이 가능하도록 하였음
* 강연신청시 중복 신청을 확인하여 강연 신청이 가능하도록 하였음

## 6. 기타 설명

### 1) 단위 테스트
* test 패키지 확인

### 2) Backend API Docs 경로
* [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### 3) API 테스트

* BackOffice
  - 강연목록: GET - http://localhost:8080/backoffice/classes
  - 강연등록: POST - http://localhost:8080/backoffice/class
    ```json
    {
        "speaker": "강발표",
        "location": "강연장2",
        "maxPeople": "70",
        "startTime": "2024-05-16 09:00:00",
        "endTime": "2024-05-16 10:00:00",
        "content": "강연내용5"
    }
    ```
  - 강연신청자목록: GET - http://localhost:8080/backoffice/subscribe/1

* Front
  - 강연목록: GET - http://localhost:8080/front/possibleClass
  - 강연신청: POST - http://localhost:8080/front/subscribe
    ```json
    {
        "classId": "1",
        "employerId": "10009"
    }
    ```
  - 신청내역조회: GET - http://localhost:8080/front/subscribe/10001
  - 신청한강연취소: PUT - http://localhost:8080/front/subscribe
    ```json
    {
        "classId": "1",
        "employerId": "10008"
    }
    ```
  - 실시간인기강연: GET - http://localhost:8080/front/popularClass

### 4) 샘플 데이터

* 강연 테이블
```sql
INSERT INTO class_tb (speaker, location, max_people, content, class_id, start_time, end_time) VALUES ('김발표', '강연장2', '70', '강연내용2', '2', '2024-05-10 10:00:00.000000', '2024-05-10 10:30:00.000000');
INSERT INTO class_tb (speaker, location, max_people, content, class_id, start_time, end_time) VALUES ('박발표', '강연장3', '30', '강연내용3', '3', '2024-05-20 11:00:00.000000', '2024-05-20 13:00:00.000000');
INSERT INTO class_tb (speaker, location, max_people, content, class_id, start_time, end_time) VALUES ('강발표', '강연장1', '50', '강연내용6', '6', '2024-05-09 11:00:00.000000', '2024-05-09 11:50:00.000000');
INSERT INTO class_tb (speaker, location, max_people, content, class_id, start_time, end_time) VALUES ('안발표', '강연장2', '50', '강연내용7', '7', '2024-05-08 09:00:00.000000', '2024-05-08 10:00:00.000000');
INSERT INTO class_tb (speaker, location, max_people, content, class_id, start_time, end_time) VALUES ('최발표', '강연장1', '70', '강연내용4', '4', '2024-05-15 09:30:00.000000', '2024-05-15 10:00:00.000000');
INSERT INTO class_tb (speaker, location, max_people, content, class_id, start_time, end_time) VALUES ('황발표', '강연장2', '50', '강연내용5', '5', '2024-05-13 09:30:00.000000', '2024-05-13 10:30:00.000000');
INSERT INTO class_tb (speaker, location, max_people, content, class_id, start_time, end_time) VALUES ('이발표', '강연장1', '10', '강연내용1', '1', '2024-05-12 09:00:00.000000', '2024-05-12 10:00:00.000000');
```

* 직원 테이블
```sql
INSERT INTO employer_tb (employer_id, name) VALUES ('10008', '이병헌');
INSERT INTO employer_tb (employer_id, name) VALUES ('10001', '홍길동');
INSERT INTO employer_tb (employer_id, name) VALUES ('10005', '박영수');
INSERT INTO employer_tb (employer_id, name) VALUES ('10009', '마동석');
INSERT INTO employer_tb (employer_id, name) VALUES ('10002', '김철수');
INSERT INTO employer_tb (employer_id, name) VALUES ('10007', '장동건');
INSERT INTO employer_tb (employer_id, name) VALUES ('10006', '유재석');
INSERT INTO employer_tb (employer_id, name) VALUES ('10004', '이길동');
INSERT INTO employer_tb (employer_id, name) VALUES ('10003', '김영수');
```

* 직원/강연 테이블
```sql
INSERT INTO class_employer_tb (status, class_id, employer_id, subscribe_date, cancel_date) VALUES ('1', '1', '10001', '2024-05-09 14:39:19.599845', null);
INSERT INTO class_employer_tb (status, class_id, employer_id, subscribe_date, cancel_date) VALUES ('1', '1', '10002', '2024-05-09 14:53:02.471614', null);
INSERT INTO class_employer_tb (status, class_id, employer_id, subscribe_date, cancel_date) VALUES ('1', '1', '10003', '2024-05-09 14:53:15.720371', null);
INSERT INTO class_employer_tb (status, class_id, employer_id, subscribe_date, cancel_date) VALUES ('0', '2', '10001', '2024-05-09 14:53:29.842429', null);
INSERT INTO class_employer_tb (status, class_id, employer_id, subscribe_date, cancel_date) VALUES ('0', '3', '10006', '2024-05-09 14:53:44.444790', null);
INSERT INTO class_employer_tb (status, class_id, employer_id, subscribe_date, cancel_date) VALUES ('0', '1', '10004', '2024-05-09 15:06:01.316006', null);
INSERT INTO class_employer_tb (status, class_id, employer_id, subscribe_date, cancel_date) VALUES ('1', '1', '10007', '2024-05-10 14:38:45.440732', null);
INSERT INTO class_employer_tb (status, class_id, employer_id, subscribe_date, cancel_date) VALUES ('0', '1', '10008', '2024-05-10 14:22:49.792652', '2024-05-10 15:15:00.185900');
INSERT INTO class_employer_tb (status, class_id, employer_id, subscribe_date, cancel_date) VALUES ('1', '1', '10006', '2024-05-10 16:12:21.148040', null);
INSERT INTO class_employer_tb (status, class_id, employer_id, subscribe_date, cancel_date) VALUES ('1', '1', '10009', '2024-05-10 16:40:37.755257', null);
```
