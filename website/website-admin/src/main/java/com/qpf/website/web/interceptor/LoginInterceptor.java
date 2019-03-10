package com.qpf.website.web.interceptor;

import com.qpf.website.commons.utils.Constant;
import com.qpf.website.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆拦截器
 * <p>未登录用户只能访问/login
 * <p>已登陆用户,访问/login,跳转到/main
 */
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute(Constant.SESSION_USER);
        String uri = request.getRequestURI();
        logger.info("user in session: {}, uri: {}", user, uri);
        if (user == null && !uri.startsWith(request.getContextPath() + "/login")) {
            logger.info("redirect to login");
            response.sendRedirect(String.format("%s%s", request.getContextPath(), "/login"));
            return false;
        }
        if (user != null && uri.startsWith(request.getContextPath() + "/login")) {
            logger.info("redirect to main");
            response.sendRedirect(String.format("%s%s", request.getContextPath(), "/main"));
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
