package com.qpf.init;

import com.qpf.servlet.HelloServlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import javax.servlet.http.HttpServlet;
import java.util.Set;

@HandlesTypes(value = {HttpServlet.class})
public class ServletContainerInitializerImpl implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        if (set != null) {
            for(Class<?> clazz: set) {
                System.out.println(clazz);
            }
        }
        ServletRegistration.Dynamic dynamic = servletContext.addServlet("helloServlet", HelloServlet.class);
        dynamic.addMapping("/hello");
    }
}
