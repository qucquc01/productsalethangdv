package com.example.productsale.dto;

import com.example.productsale.exception.BaseException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseMsg implements Serializable {

    private Integer code = 0;
    private String message = null;
    private Object data = null;

    public ResponseMsg(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseMsg(Integer code, String message) {
        this(code, message, null);
    }

    public ResponseMsg(Exception e){
        this();
        message = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            message += "(" + cause.getMessage() + ")";
        }

        if (e instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            data = baseException.getData();
            code = baseException.getCode();
        } else {
            code = 500;
            message = "Internal Server Error: " + message;
            e.printStackTrace();
        }
    }
}
