package com.qpf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.catalina.Host;
import org.apache.catalina.Server;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;

public class WebService {

    @AllArgsConstructor // 全参构造方法
    @NoArgsConstructor  // 无参构造方法
    @Data               // getter setter
    @Accessors(chain=true) //链式访问
    public static class Builder {

        private String hostName="localhost";
        private String baseDir = new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
        private int port = 8090;
        private String protocol = "org.apache.coyote.http11.Http11NioProtocol";
        private String uriEncoding = "UTF-8";
//        private String root = "embed-tomcat/src/main/resources";
        private String path_ = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

        private String root = path_.substring(0, path_.lastIndexOf(File.separator) + 1) + "public";

        private int maxPostSize = 0;
        private int maxThreads = 200;
        private int acceptCount = 100;
        public WebService build() {
            if (StringUtils.isEmpty(root)) {
                throw new IllegalArgumentException(StringUtils.replace("root is Empty", "root", root));
            }
            File rootDir = new File(root);
            if (!rootDir.isDirectory()) {
                throw new IllegalArgumentException(StringUtils.replace("root is not directory", "root", root));
            }
            if (!rootDir.exists()) {
                throw new IllegalArgumentException(StringUtils.replace("root is not exists", "root", root));
            }

            return new WebService(this);
        }
    }

//    private Tomcat tomcat;
    private Builder builder;

    public WebService(Builder builder) {
        this.builder = builder;
        initServer();
    }

    private void initServer() {
        updateProperties();
        try {
            // 1. 创建内嵌Tomcat
            Tomcat tomcat = new Tomcat();
            // 2. 设置端口
            tomcat.setPort(builder.port);
            // 设置主机
            tomcat.setHostname(builder.hostName);

            // 设置workspace
            tomcat.setBaseDir(getWorkspace());

            configServer(tomcat.getServer());
            configHost(tomcat.getHost());
            configConnector(tomcat.getConnector());


            String path = new File(builder.root).getAbsolutePath();
            StandardContext context = (StandardContext) tomcat.addWebapp("/", path);
            StandardRoot resources = new StandardRoot(context);
            resources.addPreResources(new DirResourceSet(resources, "/public/classes", path, "/"));
            context.setResources(resources);

            tomcat.start();
            tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateProperties() {

    }

    private void configServer(Server server) {
        server.addLifecycleListener(new AprLifecycleListener());
    }
    private void configHost(Host host) {
        host.setAppBase(System.getProperty("user.dir"));
        host.setAutoDeploy(false);
    }
    private void configConnector(Connector connector) {
        connector.setURIEncoding("UTF-8");
        connector.setMaxPostSize(builder.maxPostSize);
        connector.setAttribute("maxThreads", builder.maxThreads);
        connector.setAttribute("acceptCount", builder.acceptCount);
        connector.setAttribute("disableUploadTimeout", Boolean.valueOf(true));
        connector.setAttribute("enableLookups", Boolean.valueOf(false));
    }

    public void start() {

    }

    /**
     * 设置workspace
     * @return
     * @throws IOException
     */
    private String getWorkspace() throws IOException {
        File tempFile = File.createTempFile("tomcat.", "." + builder.port);
        tempFile.delete();
        tempFile.mkdir();
        tempFile.deleteOnExit();
        return tempFile.getAbsolutePath();
    }

    public static WebService webService() {
        return new WebService.Builder().build();
    }

}
