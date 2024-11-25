package com.wor.dash.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Value("${project.origin-url}")
	private String allowOrginUrl;

	private final Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins(allowOrginUrl)
				.allowedMethods(
						HttpMethod.GET.name(),
						HttpMethod.POST.name(),
						HttpMethod.PUT.name(),
						HttpMethod.DELETE.name(),
						HttpMethod.HEAD.name(),
						HttpMethod.OPTIONS.name(),
						HttpMethod.PATCH.name())
				.maxAge(1800);

		// Swagger UI 관련 CORS 설정 추가
		registry.addMapping("/v3/api-docs/**")
				.allowedOrigins(allowOrginUrl)
				.allowedMethods(HttpMethod.GET.name());
		registry.addMapping("/swagger-ui/**")
				.allowedOrigins(allowOrginUrl)
				.allowedMethods(HttpMethod.GET.name());
	}
}