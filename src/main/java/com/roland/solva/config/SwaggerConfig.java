package com.roland.solva.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

/**
 * @author Roland Pilpani 05.12.2022
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30).
                apiInfo(apiInfo()).select().
                apis(RequestHandlerSelectors.basePackage("com.roland.solva.controllers"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Solva Test Assignment")
                .description("Microservice for bank transactions and month expenses control")
                .contact(new Contact("Roland Pilpani", "", "pilpani.roland@gmail.com"))
                .version("1.0.0").build();
    }

}