package com.sh.roadmap.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class LeaderboardException extends Exception {
    private Object data;
    private String code;
    private String message;
    private HttpStatus httpStatus;

    public LeaderboardException(Object data, String code, String message) {
        super(message);
        this.data = data;
        this.code = code;
        this.httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    public LeaderboardException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public LeaderboardException(Throwable throwable) {
        super(throwable);
        this.message = throwable.getMessage();
    }
}
