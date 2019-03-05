package com.qpf.manage.web.controller;

import com.qpf.manage.commons.utils.SpringContext;
import com.qpf.manage.entity.User;
import com.qpf.manage.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/login")
public class UserController_servlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserController_servlet.class);
    private UserService userService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = userService.login(email, password);
        logger.debug("login by email: {}, password: {}", email, password);

        if (user == null) {
            req.getRequestDispatcher("login.html").forward(req, resp);
        } else {
            resp.sendRedirect("main.html");
        }
    }

    @Override
    public void init() throws ServletException {
//        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
//        userService = (UserService) context.getBean("userService");
        userService = SpringContext.getBean("userService");
    }
}
