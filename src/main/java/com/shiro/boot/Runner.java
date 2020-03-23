package com.shiro.boot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner 接口的 Component 会在所有 Spring Beans 都初始化之后，SpringApplication.run() 之前执行，
 * 非常适合在应用程序启动之初进行一些数据初始化的工作。
 * 可创建多个，并使用 @Order 注解实现执行顺序
 * @author jzl
 * @date 2020/3/20 11:38
 */
@Component
public class Runner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("The Runner start to initialize ... ");
    }
}
