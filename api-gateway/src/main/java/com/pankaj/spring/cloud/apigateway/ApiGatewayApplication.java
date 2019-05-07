package com.pankaj.spring.cloud.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.pankaj.spring.cloud.apigateway.filter.PostFilter;
import com.pankaj.spring.cloud.apigateway.filter.PreFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableSwagger2
@EnableFeignClients
@ComponentScan("com.pankaj.spring.cloud")
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	@Value("spring.datasource.username")
	private String test;
	
	 @Bean
	    public Docket documentManagementApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .apiInfo(apiInfo())
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

	    @Bean
	    public PreFilter preFilter() {
	        return new PreFilter();
	    }
	    
	    @Bean
	    public PostFilter postFilter(){
	    	return new PostFilter();
	    }
	    
	    public CorsFilter corsFilter() {
	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        final CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.addAllowedOrigin("*");
	        config.addAllowedHeader("*");
	        config.addAllowedMethod("OPTIONS");
	        config.addAllowedMethod("HEAD");
	        config.addAllowedMethod("GET");
	        config.addAllowedMethod("PUT");
	        config.addAllowedMethod("POST");
	        config.addAllowedMethod("DELETE");
	        config.addAllowedMethod("PATCH");
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }

	    @Bean
	    public CookieSerializer cookieSerializer() {
	        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
	        serializer.setCookieName("JSESSIONID");
	        serializer.setCookiePath("/");
	        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
	        serializer.setCookieMaxAge(315360000);
	        return serializer;
	    }
}
