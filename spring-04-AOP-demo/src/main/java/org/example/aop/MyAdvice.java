package org.example.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@Aspect
public class MyAdvice {

    //定义切入点
    @Pointcut("execution(void org.example.dao.BookDao.update())")
    private void pt(){};

    @Pointcut("execution(* org.example.dao.BookDao.findName(..))")
    private void pt2(){};

    //绑定切入点和通知的关系
    @Before("pt()")
    public void  method() {
        System.out.println(System.currentTimeMillis());
    }

    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around before advice...");
        Object ret = pjp.proceed();  //对原始操作的调用
        System.out.println("around after advice...");
        return ret;
    }

    @Before("pt2()")
    public void before2(JoinPoint jp) {
        Object[] args = jp.getArgs();
        System.out.println(Arrays.toString(args));
        System.out.println("before advice ...");
    }

    @After("pt2()")
    public void after2() {
        System.out.println("after advice ...");
    }

    @Around("pt2()")
    public Object around2(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        Object ret = pjp.proceed();
        return ret;
    }

    @AfterReturning(value = "pt2()", returning = "ret")
    public void afterReturning(Object ret) {
        System.out.println("afterReturning advice ..."+ret);
    }

    @AfterThrowing("pt2()")
    public void afterThrowing() {
        System.out.println("afterThrowing advice ...");
    }
}
