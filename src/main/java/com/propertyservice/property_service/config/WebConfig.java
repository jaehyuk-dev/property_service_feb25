package com.propertyservice.property_service.config;

import org.springframework.context.annotation.Configuration;
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
}
