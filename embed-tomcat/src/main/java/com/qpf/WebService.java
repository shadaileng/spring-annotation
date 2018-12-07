package com.qpf;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Server;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class WebService {
    private String hostName="localhost";
    private int port = 8090;
    private String root = "embed-tomcat/src/main/public";

    private int maxPostSize = 0;
    private int maxThreads = 200;
    private int acceptCount = 100;

    private Tomcat tomcat;

    public WebService() {}

    private void updateProperties() {

    }

    private void configServer(Server server) {
        server.addLifecycleListener(new AprLifecycleListener());
    }
    private void configHost(Host host) {
        host.setAppBase(System.getProperty("user.dir"));
    }
    private void configConnector(Connector connector) {
        connector.setURIEncoding("UTF-8");
        connector.setMaxPostSize(maxPostSize);
        connector.setAttribute("maxThreads", maxThreads);
        connector.setAttribute("acceptCount", acceptCount);
        connector.setAttribute("disableUploadTimeout", Boolean.valueOf(true));
        connector.setAttribute("enableLookups", Boolean.valueOf(false));
    }

    public void start() {
        updateProperties();
        try {
            tomcat = new Tomcat();
            tomcat.setPort(this.port);
            tomcat.setHostname(hostName);
            File tempFile = File.createTempFile("tomcat.", ".8080");
            tempFile.delete();
            tempFile.mkdir();
            tempFile.deleteOnExit();
            tomcat.setBaseDir(tempFile.getAbsolutePath());

            configServer(tomcat.getServer());
            configHost(tomcat.getHost());
            configConnector(tomcat.getConnector());

            StandardContext context = (StandardContext) tomcat.addWebapp("/", getClass().getResource("").getPath());
//            StandardContext context = (StandardContext) tomcat.addWebapp("/", new File(root).getAbsolutePath());
            File webInfoClasses = new File("target/classes");
//            StandardRoot root = new StandardRoot(context);
//            root.addPreResources(new DirResourceSet(root, "/WEB-INF/classes", webInfoClasses.getAbsolutePath(), "/"));
//            context.setResources(root);
//            tomcat.addWebapp("/", System.getProperty("user.dir") + File.separator + root);

            tomcat.start();
            tomcat.getServer().await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WebService webService() {
        return new WebService();
    }

}
