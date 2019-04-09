package com.qpf.website.web.api;

import com.qpf.website.commons.utils.HttpClientUtils;
import com.qpf.website.commons.utils.JsonUtils;
import com.qpf.website.web.dto.ContentDTO;

import java.util.ArrayList;
import java.util.List;

public class ContentApi {
    public static List<ContentDTO> list() {
        List<ContentDTO> contents = new ArrayList<>();

        try {
            String contentsStr = HttpClientUtils.doGet(API.API_CONTENTS_PPT);
            System.out.println(contentsStr);
            contents = JsonUtils.json2listByNode(contentsStr, "data", ContentDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contents;
    }
}
