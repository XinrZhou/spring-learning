package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.domin.ResponseResult;
import com.example.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //给前端ResponseResult 的json
        ResponseResult responseResult = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "登陆认证失败了，请重新登陆！");
        String json = JSON.toJSONString(responseResult);

        WebUtils.renderString(response,json);
    }
}