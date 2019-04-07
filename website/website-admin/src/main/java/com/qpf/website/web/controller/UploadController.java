package com.qpf.website.web.controller;

import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadController {
    private final static Logger logger = LoggerFactory.getLogger(UploadController.class);


    private UploadService uploadService;
    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @ResponseBody
    @PostMapping("upload")
    @SuppressWarnings("unchecked")
    public Map<String, Object> upload(MultipartFile dropzFile, MultipartFile editorFile) {

        BaseResult result = null;

        Map<String, Object> map = new HashMap<>();

        if (dropzFile != null) {
            result = uploadService.uploadFile(dropzFile);
            map.put("fileName", result.getData());
        }
        if (editorFile != null) {
            result = uploadService.uploadFile(editorFile);
            map.put("errno", 0);
            map.put("url", result.getData());
        }

        logger.info(String.format("result: %s", map));

        return map;
    }

}
