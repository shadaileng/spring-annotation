package com.qpf.manage.web.init;


import com.alibaba.druid.support.http.StatViewServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import javax.servlet.http.HttpServlet;
import java.util.Set;

@HandlesTypes(value = {HttpServlet.class})
public class ServletContainerInitializerImpl implements ServletContainerInitializer {
    private static final Logger logger = LoggerFactory.getLogger(ServletContainerInitializerImpl.class);

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        logger.info("***********************************************************************************");
        if (c != null) {
            for(Class<?> clazz: c) {
                logger.info(clazz.toString());
            }
        }
        logger.info("***********************************************************************************");
//        ctx.setInitParameter("contextConfigLocation", "classpath:spring-context*.xml");
//        ctx.addListener(ContextLoaderListener.class);
        ServletRegistration.Dynamic druid = ctx.addServlet("druid", StatViewServlet.class);
        druid.addMapping("/druid/*");
    }
}
