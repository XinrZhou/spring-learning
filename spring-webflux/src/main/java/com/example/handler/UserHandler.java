package com.example.handler;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }
    // 空值处理
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    // 根据id查询
    public Mono<ServerResponse> getById(ServerRequest request) {
        // 获取id值
        int userId = Integer.valueOf(request.pathVariable("id"));
        // 调用service得到数据
        Mono<User> userMono = this.userService.getUserById(userId);
        // 把userMono进行转换并返回
        return userMono
                .flatMap(user -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(fromObject(user)))
                .switchIfEmpty(notFound);
    }

    // 查询所有
    public  Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<User> users = userService.getAllUser();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(users, User.class);
    }

    // 添加
    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        return ServerResponse.ok().build(this.userService.saveUserInfo(userMono));
    }
}
