package org.example;

import org.example.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class App {
    public  static void main(String[] args) {
        //获取IoC容器
        //1. 类路径加载配置文件
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
       //2. 文件路径加载配置文件
//        ApplicationContext ctx = new FileSystemXmlApplicationContext("D:\\workspace-2022\\spring-workspace\\spring-learning\\spring-01-core-concept\\src\\main\\resources\\applicationContext.xml");
        BookService bookService = (BookService) ctx.getBean("bookService");
//        BookService bookService = ctx.getBean("bookService", BookService.class);
//        BookService bookService = ctx.getBean( BookService.class);
        bookService.save();
    }
}
