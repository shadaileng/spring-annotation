package com.qpf.website.commons.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpClientUtils {
    private static final String METHOD_POST = "post";
    private static final String METHOD_GET = "get";
    private static final String REQUEST_HEADER_CONNECTION = "keep-alive";
    private static final String REQUEST_HEADER_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36";

    private static String createClient(String url, String requestMathod, String cookie, BasicNameValuePair... params) {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        String result = "";
        CloseableHttpResponse response = null;

        try {

            switch (requestMathod) {
                case METHOD_GET:
                    HttpGet httpGet = new HttpGet(url);
                    httpGet.setHeader("Connection", REQUEST_HEADER_CONNECTION);
                    httpGet.setHeader("User-Agent", REQUEST_HEADER_USER_AGENT);
                    httpGet.setHeader("Cookie", cookie);
                    response = httpClient.execute(httpGet);
                    break;
                case METHOD_POST:
                    HttpPost httpPost = new HttpPost(url);

                    httpPost.setHeader("Connection", REQUEST_HEADER_CONNECTION);
                    httpPost.setHeader("User-Agent", REQUEST_HEADER_USER_AGENT);
                    httpPost.setHeader("Cookie", cookie);

                    if (params != null && params.length > 0) {
                        httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(params), "UTF-8"));
                    }
                    response = httpClient.execute(httpPost);
                    break;
            }
            if (response != null) {
                result = EntityUtils.toString(response.getEntity());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
    public static String doPost(String url, BasicNameValuePair... params) {
        return createClient(url, METHOD_POST, null, params);
    }
    public static String doPost(String url, String cookie, BasicNameValuePair... params) {
        return createClient(url, METHOD_POST, cookie, params);
    }
    public static String doGet(String url) {
        return createClient(url, METHOD_GET, null);
    }
    public static String doGet(String url, String cookie) {
        return createClient(url, METHOD_GET, cookie);
    }

//    public static void main(String[] args) {
////        get();
////        post();
////        System.out.println(createClient("http://localhost:8081/api/v1/contents/ppt", METHOD_GET, null));
////        System.out.println(createClient("http://localhost:8081/api/v1/users/login", METHOD_POST, null, new BasicNameValuePair("username", "sdl"), new BasicNameValuePair("password", "123456")));
//        System.out.println(doGet("http://localhost:8081/api/v1/contents/ppt"));
//        System.out.println(doPost("http://localhost:8081/api/v1/users/login", new BasicNameValuePair("username", "sdl"), new BasicNameValuePair("password", "123456")));
//    }
}
