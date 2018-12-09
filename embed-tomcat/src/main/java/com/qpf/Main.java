package com.qpf;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        WebService.webService().start();
//        Main main = new Main();
//        main.show();
    }
    public void show() throws IOException {

//        File tempFile = File.createTempFile("tomcat.", ".8080");
//        tempFile.delete();
//        tempFile.mkdir();
//        tempFile.deleteOnExit();
//        System.out.println(tempFile.getAbsolutePath());
        System.out.println(System.getProperty("java.io.tmpdir"));
        System.out.println(getClass().getResource("").getFile());
        System.out.println(getClass().getResource("/public/index.html"));
        System.out.println(System.getProperty("user.dir"));
        System.out.println(ClassLoader.getSystemResource("."));
        System.out.println(getClass());
        System.out.println(this.getClass().getResource("/public/index.html"));
        System.out.println(getClass().getResource("/") == null ? "null" : getClass().getResource("/").getPath());
        System.out.println(new File(getClass().getResource("/") == null ? "." : getClass().getResource("/").getPath()));
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(path);
        path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        System.out.println(path);
        path = System.getProperty("java.class.path");
        System.out.println(path);
    }
}
