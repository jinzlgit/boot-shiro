package com.shiro.boot.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 配置类
 *
 * @author jzl
 * @date 2020/4/8 14:48
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                // 指定构建 API 文档的详细信息的方法
                .apiInfo(apiInfo())
                .select()
                // 指定要生成 api 接口的包路径，这里把 controller 作为包路径，生成 controller 中的所有接口
                .apis(RequestHandlerSelectors.basePackage("com.shiro.boot.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建 API 文档的详细信息
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                // 设置页面标题
                .title("Spring Boot 集成 Swagger2 接口总览")
                // 设置接口描述
                .description("大家一起学Springboot")
                // 设置联系方式
                .contact("金振林")
                // 设置版本
                .version("1.0")
                // 构建
                .build();
    }

}
