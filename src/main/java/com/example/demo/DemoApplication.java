package com.example.demo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebSecurity
@EnableSwagger2
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
        	      .apiInfo(apiInfo())
        	      .securityContexts(Arrays.asList(securityContext()))
        	      .securitySchemes(Arrays.asList(apiKey()))
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.example"))              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "My REST API",
	      "Some custom description of API.",
	      "1.0",
	      "Terms of service",
	      new Contact("Nil Nadirler", "www.nilnadirler.com", "nilnadirler@gmail.com"),
	      "License of API",
	      "API license URL",
	      Collections.emptyList());
	}
    private ApiKey apiKey() { 
        return new ApiKey("JWT", "Authorization", "header"); 
    }
    private SecurityContext securityContext() { 
        return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
    } 

    private List<SecurityReference> defaultAuth() { 
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
        authorizationScopes[0] = authorizationScope; 
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
    }

}



