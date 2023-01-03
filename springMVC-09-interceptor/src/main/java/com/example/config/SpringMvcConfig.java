package com.example.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({"com.example.controller","com.example.config"})
@EnableWebMvc
public class SpringMvcConfig {
}
