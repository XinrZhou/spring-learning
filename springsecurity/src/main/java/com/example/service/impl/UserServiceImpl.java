package com.example.service.impl;

import com.example.domin.LoginUser;
import com.example.domin.ResponseResult;
import com.example.domin.User;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import com.example.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult login(User user) {
        // 3使用ProviderManager auth方法进行验证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // 校验失败
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名密码错误");
        }

        //4生成jwt给前端
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String usrId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(usrId);

        Map<String, String> map = new HashMap<>();
        map.put("token",jwt);

        return new ResponseResult(200,"登陆成功", map);
    }
}
