package com.qpf.website.web.api;

public class API {
    public static final String HOST = "http://localhost:8081/api/v1";
    public static final String API_CONTENTS_PPT = String.format("%s%s", HOST, "/contents/ppt");
    public static final String API_USER_LOGIN = String.format("%s%s", HOST, "/users/login");
    public static final String SESSION_USER_KEY = "user";
}
