package com.qpf.website.web.config;

import com.qpf.website.web.config.properties.ResourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;

@EnableWebMvc
@Configuration
@ComponentScan(value = {"com.qpf.website"}, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
})
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private ResourceProperties resourceProperties;
    private String RESOURCES_LOCATION = "static/";
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCES_LOCATION + "**").addResourceLocations("classpath:/public/");
//        registry.addResourceHandler(RESOURCES_LOCATION + "**").addResourceLocations(RESOURCES_LOCATION);
        registry.addResourceHandler("upload/**").addResourceLocations(String.format("file:%s", resourceProperties.getResourcePathUpload()));
    }


}
