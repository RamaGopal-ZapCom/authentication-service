package com.zapcom.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Created by Rama Gopal Project Name - auth-service */
@Configuration
public class AuthServiceSwaggerConfiguration {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Authentication Service API")
                .version("1.0")
                .description("API documentation for Client Service"));
  }

  // Optional: if you want to document specific controller packages
  @Bean
  public GroupedOpenApi clientApi() {
    return GroupedOpenApi.builder()
        .group("Authentication-service")
        .packagesToScan("com.zapcom.controller")
        .build();
  }
}
