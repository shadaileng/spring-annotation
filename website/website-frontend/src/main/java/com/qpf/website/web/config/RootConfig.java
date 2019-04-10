package com.qpf.website.web.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

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
    @Bean
    public DefaultKaptcha captchaProducer() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();

        InputStream inputStream = ClassLoader.getSystemResourceAsStream("classpath:/application.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        defaultKaptcha.setConfig(new Config(properties));
        return defaultKaptcha;
    }
}
