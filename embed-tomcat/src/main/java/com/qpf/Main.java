package com.qpf;

import java.io.File;

public class Main {
    public static void main(String[] args) {
//        WebService.webService().start();
        Main main = new Main();
        main.show();
    }
    public void show() {

        System.out.println(System.getProperty("user.dir"));
        System.out.println(getClass());
        System.out.println(getClass().getResource("/"));
        System.out.println(getClass().getResource("/").getPath());
        System.out.println(new File(getClass().getResource("/").getPath()));
    }
}
