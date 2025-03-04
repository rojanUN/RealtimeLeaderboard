package com.sh.roadmap.builder;

import com.sh.roadmap.model.Response;
import com.sh.roadmap.util.ExceptionUtil;
import com.sh.roadmap.util.MessageBundle;
import lombok.Data;

@Data
public class ResponseBuilder {

    private ResponseBuilder() {
    }

    public static Response buildSuccessResponse(String message, Object data) {
        return Response.builder()
                .success(Boolean.TRUE)
                .code("0000")
                .data(data)
                .message(message)
                .build();
    }

    public static Response buildSuccessResponse(String code) {
        return Response.builder()
                .success(Boolean.TRUE)
                .code("0000")
                .message(MessageBundle.getMessageByCode(code))
                .build();
    }

    public static Response buildSuccessResponse(Object data) {
        return Response.builder()
                .success(Boolean.TRUE)
                .code("0000")
                .data(data)
                .build();
    }

    public static Response buildUnknownFailResponse(Exception exception) {
        Response response = new Response();
        response.setSuccess(Boolean.FALSE);
        response.setCode("000000");
        response.setMessage(exception.getMessage());
        response.setData(ExceptionUtil.getStackTraceString(exception));
        return response;

    }

}
