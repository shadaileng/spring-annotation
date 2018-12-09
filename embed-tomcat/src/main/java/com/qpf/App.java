package com.qpf;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws LifecycleException
    {
        Tomcat tomcat = new Tomcat();
        tomcat.setHostname("localhost");
        tomcat.setBaseDir(".");
        
        String DEFAULT_PROTOCOL = "org.apache.coyote.http11.Http11NioProtocol";
        Connector connector = new Connector(DEFAULT_PROTOCOL);
        connector.setURIEncoding("UTF-8");
        connector.setPort(8090);
        tomcat.getService().addConnector(connector);

        // Listener
        StandardServer server = (StandardServer)tomcat.getServer();
        server.addLifecycleListener(new AprLifecycleListener());
        
        // http://127.0.0.1:8090/servlet/hello
        Context context = tomcat.addContext("servlet", null);
        tomcat.addServlet(context, "helloServlet", new HttpServlet() {
        	@Override
        	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        			throws ServletException, IOException {
        		resp.getWriter().println("Hello");
        	}
		});
        context.addServletMappingDecoded("/hello", "helloServlet");
        
        // webapp
        tomcat.getHost().setAppBase(System.getProperty("user.dir") + File.separator + ".");
        tomcat.addWebapp("", "web");
        
        tomcat.start();
        tomcat.getServer().await();
    }
}
