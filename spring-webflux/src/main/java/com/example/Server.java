package com.example;

import com.example.handler.UserHandler;
import com.example.service.UserService;
import com.example.service.impl.UserServiceImpl;

import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

public class Server {

    // 调用
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.createReactorServer();
        System.out.println("enter to exit...");
        System.in.read();
    }

    //创建路由
    public RouterFunction<ServerResponse> routerFunction() {
        // 创建handler对象
        UserService userService = new UserServiceImpl();
        UserHandler handler = new UserHandler(userService);

        //设置路由
        return RouterFunctions.route(
                GET("/user/{id}").and(accept(APPLICATION_JSON)),handler::getById)
                        .andRoute(GET("/users").and(accept(APPLICATION_JSON)),handler::getAll);
    }

    // 创建服务器完成适配
    public void createReactorServer() {
        RouterFunction<ServerResponse> route = routerFunction();
        HttpHandler httpHandler = toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);

        // 创建服务器
        HttpServer httpServer = HttpServer.create();
        httpServer.handle(adapter).bindNow();
    }
}
