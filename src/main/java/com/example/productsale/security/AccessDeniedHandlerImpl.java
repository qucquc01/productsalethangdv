package com.example.productsale.security;

import com.example.productsale.dto.ResponseMsg;
import com.example.productsale.msg.Msg;
import com.example.productsale.util.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException, ServletException {
        ResponseUtil.writeResponse(response, new ResponseMsg(403, Msg.getMessage("access.denied", null, request)));
    }
}
