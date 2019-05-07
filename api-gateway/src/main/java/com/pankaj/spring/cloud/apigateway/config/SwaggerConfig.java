package com.pankaj.spring.cloud.apigateway.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Component
@Primary
@EnableAutoConfiguration
public class SwaggerConfig  implements SwaggerResourcesProvider {

	
	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		resources.add(swaggerResource("book-service", "/book-service/v2/api-docs", "2.0"));
		resources.add(swaggerResource("rating-service", "/rating-service/v2/api-docs", "2.0"));
		resources.add(swaggerResource("user-service", "/user-service/v2/api-docs", "2.0"));
		return resources;
	}
	  
	private SwaggerResource swaggerResource(String name, String location, String version) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion(version);
		return swaggerResource;
	}
	 
	
	/*
	 * @Bean public Docket api() { return new Docket(DocumentationType.SWAGGER_2)
	 * .select() .apis(RequestHandlerSelectors.basePackage(
	 * "com.pankaj.spring.cloud.bookservice")) .paths(PathSelectors.ant("/books/*"))
	 * .build(); }
	 */

}
