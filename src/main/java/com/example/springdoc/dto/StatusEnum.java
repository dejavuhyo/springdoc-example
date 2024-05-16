package com.example.springdoc.dto;

public enum StatusEnum {
    OK(200, "OK"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    FOUND(302, "Found"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    CONFLICT(409, "Conflict"),
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    int code;
    String message;

    StatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
