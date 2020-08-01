package com.example.productsale.controller;

import com.example.productsale.dto.ResponseMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController extends BaseResponseController {
    protected ResponseEntity response(int code, String message, Object data) {

        ResponseMsg responseMsg = new ResponseMsg(code, message, data);

        return new ResponseEntity(responseMsg, HttpStatus.OK);
    }

    protected ResponseEntity response(String message, Object data) {
        return response(0, message , data);
    }

    protected ResponseEntity response(int code, String message) {
        return response(code, message, null);
    }

    protected ResponseEntity response(Object data) {
        return response(null, data);
    }

    protected ResponseEntity response() {
        return response(null);
    }
}
