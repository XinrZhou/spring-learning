package com.example.config;


import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;


public class ServletContainersConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
// 定义一个servlet容器启动的配置类，在里面加载spring的配置
//public class ServletContainersConfig extends AbstractDispatcherServletInitializer {
//
//    // 加载springMVC容器配置
//    @Override
//    protected WebApplicationContext createServletApplicationContext() {
//        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
//        ctx.register(SpringMvcConfig.class);
//        return ctx;
//    }
//
//    // 设置哪些请求归springMVC处理
//    @Override
//    protected String[] getServletMappings() {
//        return new String[]{"/"};
//    }
//
//    // 加载springMVC容器配置
//    @Override
//    protected WebApplicationContext createRootApplicationContext() {
//        return null;
//    }
//}
