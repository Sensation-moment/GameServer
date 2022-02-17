package com.fattyCorps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger相关配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 部分摘要
     *
     * @param e
     * @return
     */
    @Bean
    public Docket groupDocket(Environment e) {
        // 添加head参数start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("token")
                .description("令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(pars)
                .apiInfo(getApiInfo())
                .select()
                // 指定swagger扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.fattyCorps.controller"))
                .build();
    }

    /**
     * 设置相关信息
     *
     * @return
     */
    private ApiInfo getApiInfo() {
        // 设置作者信息
        Contact contact = new Contact("suifeng", "www.baidu.com", "weijun.bei@163.com");
        // 设置文档信息
        ApiInfo apiInfo = new ApiInfo(
                "游戏服务器接口",
                "游戏服务器接口描述",
                "0.1",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.baidu.com",
                new ArrayList<VendorExtension>());
        return apiInfo;
    }
}