package com.qpf.website.web.config.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Component
@Configuration
@Retention(RUNTIME)
@Target({ElementType.TYPE})
public @interface MetaProperties {
}
