package com.qpf.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(getClass() + "...preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(getClass() + "...postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(getClass() + "...afterCompletion");
        //must-revalidate:作用与no-cache相同，但更严谨
        //no-store:缓存将不存在response,包括header和body。
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        // 本地无缓存，自动刷新页面
        response.setHeader("Pragma", "no-cache");
        // Expires实体报头域给出响应过期的日期和时间，小于等于0表示当前页面立即过期，
        // 为了让浏览器不要缓存页面，也可以利用Expires实体报关域，设置为0
        response.setDateHeader("Expires", 0);
    }
}
