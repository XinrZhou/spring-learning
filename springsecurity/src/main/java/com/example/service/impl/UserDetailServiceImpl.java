package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.dao.UserDao;
import com.example.domin.LoginUser;
import com.example.domin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1根据用户名查询数据库
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName, username);
        User user = userDao.selectOne(lambdaQueryWrapper);

        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名错误");
        }

        // 2TODO 权限信息：实际的权限信息需要从数据库获取，此处只是进行测试
        List<String> list=new ArrayList<>(Arrays.asList("sayhello","delgoods"));

        //3返回UserDetails
        return new LoginUser(user, list);
    }
}
