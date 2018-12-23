package com.qpf.bean;


import org.springframework.beans.factory.annotation.Value;

public class ConfigProperties {
    @Value("${configProperties.fileRoot}")
    private String FileRoot;

    public String getFileRoot() {
        return FileRoot;
    }

    public void setFileRoot(String fileRoot) {
        FileRoot = fileRoot;
    }
}
