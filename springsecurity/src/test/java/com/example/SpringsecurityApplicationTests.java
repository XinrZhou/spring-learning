package com.example;

import com.example.dao.UserDao;
import com.example.domin.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class SpringsecurityApplicationTests {

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        List<User> users = userDao.selectList(null);
        System.out.println(users.get(0));
    }

    @Test
    public void testBCript() {
        String test1 = passwordEncoder.encode("test");
        String test2 = passwordEncoder.encode("test");
        System.out.println(test1);
        System.out.println(test2);

        boolean result1 = passwordEncoder.matches("test", test1);
        System.out.println(result1);
    }
}
