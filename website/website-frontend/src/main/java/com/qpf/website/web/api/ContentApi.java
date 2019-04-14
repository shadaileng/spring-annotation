package com.qpf.website.web.api;

import com.qpf.website.commons.utils.HttpClientUtils;
import com.qpf.website.commons.utils.JsonUtils;
import com.qpf.website.web.config.properties.ApiProperties;
import com.qpf.website.web.dto.ContentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentApi {
    @Autowired
    private ApiProperties properties;
    public List<ContentDTO> list() {
        List<ContentDTO> contents = new ArrayList<>();

        try {
            String contentsStr = HttpClientUtils.doGet(properties.getContentPptAPI());
            String code = JsonUtils.json2StringByNode(contentsStr, "code");
            if ("200".equals(code)) {
                contents = JsonUtils.json2listByNode(contentsStr, "data", ContentDTO.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contents;
    }
}
