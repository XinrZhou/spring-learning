package com.example.controller;


import com.example.domain.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
//@RequestMapping("/user")
public class UserController {

    @RequestMapping("/save")
    @ResponseBody
    public String save() {
        System.out.println("user save ...");
        return "{'module': 'user save'}";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete() {
        System.out.println("user delete ...");
        return "{'module': 'user delete'}";
    }

    @RequestMapping("/commonParam")
    @ResponseBody
    public String commonParam(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "{'module': 'common param'}";
    }

    @RequestMapping("/commonParamDifferentName")
    @ResponseBody
    public String commonParamDifferentName(@RequestParam("name") String userName, int age) {
        System.out.println(userName);
        System.out.println(age);
        return "{'module': 'common param'}";
    }

    // pojo参数
    @RequestMapping("/pojoParam")
    @ResponseBody
    public String pojoParam(User user) {
        System.out.println("pojo参数传递 user ==>"+ user);
        return "{'module': 'pojo param'}";
    }


    // 嵌套pojo参数
    @RequestMapping("/pojoContainerPojoParam")
    @ResponseBody
    public String pojoContainerPojoParam(User user) {
        System.out.println("pojo嵌套pojo参数传递 user ==>"+ user);
        return "{'module': 'pojo contain pojo param'}";
    }

    // 数组参数
    @RequestMapping("/arrayParam")
    @ResponseBody
    public String arrayParam(String[] likes) {
        System.out.println("数组参数传递 likes ==>"+ Arrays.toString(likes));
        return "{'module': 'array param'}";
    }

    // 集合参数
    @RequestMapping("/listParam")
    @ResponseBody
    public String listParam(@RequestParam List<String> likes) {
        System.out.println("集合参数传递 likse ==>"+ likes);
        return "{'module': 'list param'}";
    }

    // 日期参数
    @RequestMapping("/dateParam")
    @ResponseBody
    public String dateParam(Date date,
                            @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1,
                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date2) {
        System.out.println("参数传递 date ==>"+ date);
        System.out.println("参数传递 date1(yyyy-MM-dd) ==>"+ date1);
        System.out.println("参数传递 date2(yyy-MM-dd HH:mm:ss) ==>"+ date2);
        return "{'module': 'date param'}";
    }

    // 响应页面/跳转页面
    @RequestMapping("/toJumpPage")
    public String toJumpPage() {
        System.out.println("跳转页面");
        return "hello.jsp";
    }

    //响应文本数据
    @RequestMapping("/toText")
    @ResponseBody
    public String toText() {
        System.out.println("返回纯文本数据");
        return "response text";
    }

    //响应POJO对象
    @RequestMapping("/toJsonPOJO")
    @ResponseBody
    public User toJsonPOJO() {
        System.out.println("返回json对象");
        User user = new User();
        user.setName("hello");
        user.setAge(18);
        return user;
    }

    //响应POJO集合对象
    @RequestMapping("/toJsonList")
    @ResponseBody
    public List<User> toJsonList() {
        System.out.println("换回json集合数据");
        User user1 = new User();
        user1.setName("Tom");
        user1.setAge(2);

        User user2 = new User();
        user2.setName("Jerry");
        user2.setAge(3);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        return userList;
    }
}
