package com.example.service.impl;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserServiceImpl implements UserService {

    // 模拟数据库，创建map集合存储数据
    private final Map<Integer, User> users = new HashMap<>();

    public UserServiceImpl() {
        users.put(1, new User("Tom","b", 20));
        users.put(2, new User("Jerry","b", 18));
    }

    @Override
    public Mono<User> getUserById(int id) {
        return Mono.justOrEmpty(this.users.get(id));
    }

    @Override
    public Flux<User> getAllUser() {
        return Flux.fromIterable(this.users.values());
    }

    @Override
    public Mono<Void> saveUserInfo(Mono<User> userMono) {
        return userMono.doOnNext(user -> {
            // 向集合中添加值
            int id = users.size()+1;
            users.put(id, user);
        }).thenEmpty(Mono.empty());
    }
}
