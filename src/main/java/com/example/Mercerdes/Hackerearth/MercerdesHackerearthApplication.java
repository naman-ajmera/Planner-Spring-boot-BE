package com.example.Mercerdes.Hackerearth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class MercerdesHackerearthApplication {


	public static void main(String[] args) {
		SpringApplication.run(MercerdesHackerearthApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration(){
		return new Docket(DocumentationType.SWAGGER_2).select()
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.example"))
				.build().apiInfo(apiDetails());
	}

	private ApiInfo apiDetails(){
		return new ApiInfo(
				"Calendar Planer Application",
				"Application to create Plans for Dailyy, Weekly, Monthly and None frequency",
				"1.0",
				"Free to Use",
				new springfox.documentation.service.Contact("Naman Ajmera","Hackerearth","naman.ajmra@gmail..com"),
				"Api License",
				"https:google.com",
				Collections.emptyList());
	}

}
