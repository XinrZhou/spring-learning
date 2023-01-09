package com.example.filter;

import com.example.domin.LoginUser;
import com.example.utils.JwtUtil;
import com.example.utils.RedisCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            // 放行，让后面的过滤器执行
           filterChain.doFilter(request, response);
           return;
        }

        // 解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 获取userId，redis获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("当前用户未登录！");
        }

        // 封装Authentication
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginUser, null, null);

        // 存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        //放行，让后面的过滤器执行
        filterChain.doFilter(request, response);
    }
}
