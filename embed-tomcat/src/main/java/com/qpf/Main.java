package com.qpf;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        WebService.webService().start();
        Main main = new Main();
        main.show();
    }
    public void show() throws IOException {

//        File tempFile = File.createTempFile("tomcat.", ".8080");
//        tempFile.delete();
//        tempFile.mkdir();
//        tempFile.deleteOnExit();
//        System.out.println(tempFile.getAbsolutePath());
        System.out.println(getClass().getResource("").getFile());
        System.out.println(getClass().getResource("/index.html"));
        System.out.println(System.getProperty("user.dir"));
        System.out.println(ClassLoader.getSystemResource("."));
        System.out.println(getClass());
        System.out.println(this.getClass().getResource("/"));
        System.out.println(getClass().getResource("/").getPath());
        System.out.println(new File(getClass().getResource("/").getPath()));
    }
}
