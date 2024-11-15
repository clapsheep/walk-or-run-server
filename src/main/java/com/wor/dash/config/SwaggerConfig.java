package com.wor.dash.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

//Swagger-UI 확인
//http://localhost/swagger-ui.html
//http://localhost:8080/WalkOrRun/swagger-ui.html


@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		Info info = new Info().title("WalkOrRun REST API 명세서").description(
				"REST API를 이용한 WalkOrRun 서비스 기능 명세서")
				.version("v1").contact(new io.swagger.v3.oas.models.info.Contact().name("JaeSeoHan")
						.email("hanahyun1@korea.ac.cr").url("https://github.com/clapsheep/WalkOrRun-server"));

		return new OpenAPI().components(new Components()).info(info);
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("wor-api").pathsToMatch("/api/**").build();
	}
}