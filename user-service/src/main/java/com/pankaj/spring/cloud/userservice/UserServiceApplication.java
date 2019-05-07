package com.pankaj.spring.cloud.userservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan("com.pankaj.spring.cloud")
@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	
	 @Bean
	    public Docket documentManagementApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .apiInfo(apiInfo()).pathMapping("user-service")
	                .select()
	                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
	                .paths(PathSelectors.regex("/.*"))
	                .build();
	    }

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("User Service Swagger Documentation")
	                .description("API Documentation for User Service APIs")
	                .version("2.0").build();
	    }
	
}
