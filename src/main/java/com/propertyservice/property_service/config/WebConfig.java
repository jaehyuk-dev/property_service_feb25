package com.propertyservice.property_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /building/ 이후 나오는 경로를 실제 로컬 디스크의 upload 폴더로 매핑
        registry.addResourceHandler("/building/**")
                .addResourceLocations("file:///C:/NIS/Study/property_service_feb25/src/main/resources/static/building/");

        registry.addResourceHandler("/property/**")
                .addResourceLocations("file:///C:/NIS/Study/property_service_feb25/src/main/resources/static/property/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 엔드포인트에 대해 CORS 허용
                .allowedOrigins("*") // 모든 도메인 허용 (보안상 필요한 경우 특정 도메인만 허용)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드 지정
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(false) // 필요에 따라 쿠키 인증 허용 설정
                .maxAge(3600); // preflight 요청 캐싱 시간 (초)
    }
}
