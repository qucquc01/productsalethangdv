package com.example.productsale.security;

import com.example.productsale.dto.ResponseMsg;
import com.example.productsale.msg.Msg;
import com.example.productsale.util.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public final class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseUtil.writeResponse(response, new ResponseMsg(401, Msg.getMessage("unauthorized", new Object[] {authException.getMessage()}, request)));
    }
}
