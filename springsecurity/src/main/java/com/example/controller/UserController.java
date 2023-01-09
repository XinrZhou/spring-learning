package com.example.controller;


import com.example.domin.ResponseResult;
import com.example.domin.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        return userService.login(user);
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('hello')")
    public String hello() {
        return "hello";
    }

}
