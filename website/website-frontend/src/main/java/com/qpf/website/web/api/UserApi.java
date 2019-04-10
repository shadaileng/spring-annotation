package com.qpf.website.web.api;

import com.qpf.website.commons.utils.HttpClientUtils;
import com.qpf.website.commons.utils.JsonUtils;
import com.qpf.website.web.dto.UserDTO;
import org.apache.http.message.BasicNameValuePair;

public class UserApi {
    public static UserDTO login(String username, String password) {
        UserDTO user = null;

        try {
            String usersStr = HttpClientUtils.doPost(API.API_USER_LOGIN, new BasicNameValuePair("username", username), new BasicNameValuePair("password", password));

            String code = JsonUtils.json2StringByNode(usersStr, "code");
            if ("200".equals(code)) {
                user = JsonUtils.json2pojoByNode(usersStr, "data", UserDTO.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
