package org.example;

import org.example.dao.BookDao;
import org.example.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App2 {
    public  static void main(String[] args) {
        //获取IoC容器
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        ctx.registerShutdownHook();

        BookService bookService = (BookService) ctx.getBean("bookService");
        bookService.save();
    }
}
