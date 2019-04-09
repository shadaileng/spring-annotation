package com.qpf.website.web.config;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@ComponentScan(value = {"com.qpf.website"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
})
@PropertySource("classpath:application.properties")
public class RootConfig {
//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//
//        multipartResolver.setMaxUploadSize(10485760L);
//
//        return multipartResolver;
//    }
}
