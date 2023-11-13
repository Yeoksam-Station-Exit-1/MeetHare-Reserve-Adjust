package com.yeoksam_station_exit_1.meetharereserveadjust.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {
  @Bean
  public OpenAPI openAPI() {

    Info info = new Info()
        .version("v0.0.1")
        .title("MeetHare 예약 & 정산 API")
        .description("MeetHare 서비스의 API 명세 페이지입니다.");

    return new OpenAPI()
        .info(info);
  }
}