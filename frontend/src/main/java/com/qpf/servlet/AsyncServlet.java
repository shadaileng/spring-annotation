package com.qpf.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(value = "/async", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
	private static final long serialVersionUID = 201901020931L;
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext asyncContext = req.startAsync();
        System.out.println("[1]" + Thread.currentThread() + "...." + new Date().getTime());
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("(1)" + Thread.currentThread() + "...." + new Date().getTime());
                    work();//
                    asyncContext.complete();
                    System.out.println("(2)" + Thread.currentThread() + "...." + new Date().getTime());
                    asyncContext.getResponse().getWriter().write("3s...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        resp.getWriter().write("hello....");
        System.out.println("[2]" + Thread.currentThread() + "...." + new Date().getTime());
    }
    private void work() throws Exception {
        Thread.sleep(3000);
    }
}
