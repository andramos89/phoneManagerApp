package com.example.phonecontactapp;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("PhoneBook Contact API").version("1.0")).components(new Components());
	}
	@Bean
	GroupedOpenApi groupedOpenApi() {
		return GroupedOpenApi.builder().group("public-apis").pathsToMatch("/**").build();
	}

}
