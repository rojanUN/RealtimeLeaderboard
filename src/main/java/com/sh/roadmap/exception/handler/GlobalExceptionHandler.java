package com.sh.roadmap.exception.handler;

import com.sh.roadmap.builder.ResponseBuilder;
import com.sh.roadmap.exception.LeaderboardException;
import com.sh.roadmap.model.Response;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LeaderboardException.class)
    public ResponseEntity<Response> handleLeaderboardException(LeaderboardException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(Response.builder()
                        .message(e.getMessage())
                        .code(e.getCode())
                        .data(e.getData())
                        .success(Boolean.FALSE)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseBuilder.buildUnknownFailResponse(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, List<String>> errors = bindingResult.getAllErrors().stream().collect(Collectors.groupingBy(
                error -> (error instanceof FieldError fieldError) ? fieldError.getField() : error.getObjectName(),
                Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())));
        Response response = new Response();
        response.setSuccess(false);
        response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.setData(errors);
        return ResponseEntity.badRequest()
                .body(response);
    }

}