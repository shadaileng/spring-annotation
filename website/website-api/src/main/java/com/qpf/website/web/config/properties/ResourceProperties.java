package com.qpf.website.web.config.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Data
@ToString
@MetaProperties
public class ResourceProperties {
    @Value("${resource.path}")
    private String resourcePath;
    @Value("${resource.path.upload}")
    private String resourcePathUpload;
    @Value("${resource.url}")
    private String resourceUrl;
    @Value("${api.path.v1}")
    private String apiPathV1;
}
