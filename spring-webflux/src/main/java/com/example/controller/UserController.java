//package com.example.controller;
//
//import com.example.entity.User;
//import com.example.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
///**
// * Webflux注解编程模型
// */
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    // id查询
//    @GetMapping("/{id}")
//    public Mono<User> getById(@PathVariable int id) {
//        return userService.getUserById(id);
//    }
//
//    // 查询所有
//    @GetMapping
//    public Flux<User> getAll() {
//        return userService.getAllUser();
//    }
//
//    // 添加
//    @PostMapping
//    public Mono<Void> addUser(@RequestBody User user) {
//        Mono<User> userMono = Mono.just(user);
//        return userService.saveUserInfo(userMono);
//    }
//}
