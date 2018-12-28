package com.qpf.bean;


import org.springframework.beans.factory.annotation.Value;

public class ConfigProperties {
    @Value("${configProperties.fileRoot}")
    private String FileRoot;
    @Value("${configProperties.fileHost}")
    private String FileHost;
	public String getFileRoot() {
		return FileRoot;
	}
	public void setFileRoot(String fileRoot) {
		FileRoot = fileRoot;
	}
	public String getFileHost() {
		return FileHost;
	}
	public void setFileHost(String fileHost) {
		FileHost = fileHost;
	}
    
}
