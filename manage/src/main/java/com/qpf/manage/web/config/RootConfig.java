package com.qpf.manage.web.config;

import com.qpf.manage.commons.utils.SpringContext;
import com.qpf.manage.dao.UserDao;
import com.qpf.manage.dao.impl.UserDaoImpl;
import com.qpf.manage.service.UserService;
import com.qpf.manage.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(value = {"com.qpf.manage"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
})
public class RootConfig {
//    @Bean
//    public SpringContext springContext() {
//        return new SpringContext();
//    }
//    @Bean
//    public UserDao userDao() {
//        return new UserDaoImpl();
//    }
//    @Bean
//    public UserService userService() {
//        return new UserServiceImpl();
//    }
}
