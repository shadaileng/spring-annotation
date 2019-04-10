package com.qpf.website.web.interceptor;

import com.qpf.website.web.api.API;
import com.qpf.website.web.dto.UserDTO;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserDTO user = (UserDTO) request.getSession().getAttribute(API.SESSION_USER_KEY);
        String uri = request.getRequestURI();
        if (user != null && uri.startsWith(request.getContextPath() + "/login")) {
            response.sendRedirect(String.format("%s%s", request.getContextPath(), "/index"));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
