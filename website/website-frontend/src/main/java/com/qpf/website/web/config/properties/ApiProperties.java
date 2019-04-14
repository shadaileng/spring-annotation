package com.qpf.website.web.config.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Data
@ToString
@MetaProperties
public class ApiProperties {
    @Value("${api.host}")
    private String apiHost;

    public String getUsersLoginAPI() {
        return String.format("%s%s", apiHost, "/users/login");
    }
    public String getContentPptAPI() {
        return String.format("%s%s", apiHost, "/contents/ppt");
    }
}
