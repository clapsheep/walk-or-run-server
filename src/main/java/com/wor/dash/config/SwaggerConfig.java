package com.wor.dash.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

//Swagger-UI 확인
//http://localhost/swagger-ui.html
//http://localhost:8080/swagger-ui.html


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("WALK OR RUN REST API")
                .description("삼성헬스 데이터를 활용한 운동 기록 분석, 러닝 & 걷기 통계 확인, 다양한 챌린지 참여 기능을 제공하는 WALK OR RUN 서비스의 REST API 명세서입니다.")
                .version("v1")
                .contact(new io.swagger.v3.oas.models.info.Contact()
                        .name("워꼴런 & UUID")
                        .email("")
                        .url("https://wor-dashboard.netlify.app"));

        // SecurityScheme 정의 (Bearer 토큰 방식)
        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT");

        // SecurityRequirement 정의
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");

        // OpenAPI 설정에 추가
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("BearerAuth", bearerAuth))
                .addSecurityItem(securityRequirement)
                .info(info);
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi
                .builder()
                .group("wor-api")
                .pathsToMatch("/api/**")
                .build();
    }


}