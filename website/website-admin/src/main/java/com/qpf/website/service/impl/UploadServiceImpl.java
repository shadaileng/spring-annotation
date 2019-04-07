package com.qpf.website.service.impl;

import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.service.UploadService;
import com.qpf.website.web.config.properties.ResourceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {
    private final static Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    @Autowired
    private ResourceProperties resourceProperties;
    @Override
    public BaseResult uploadFile(MultipartFile file) {

        BaseResult result;
//        Map<String, Object> map = new HashMap<>();

        String filename = file.getOriginalFilename();

        String realPath = resourceProperties.getResourcePathUpload();

        String fileSuffixed = filename.substring(filename.lastIndexOf("."));

        String datePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        File target = new File(realPath, datePath);

        if (!target.exists()) {
            target.mkdirs();
        }

        target = new File(target, String.format("%s%s", UUID.randomUUID().toString().replace("-", ""), fileSuffixed));

        try {
            file.transferTo(target);
        } catch (Exception e) {
            String msg = String.format("Error: %s", e.getMessage());
            logger.error(msg);
            result = BaseResult.failed(msg);
//            map.put("fileName", "");
            result.setData("");
            return result;
        }

//        map.put("fileName", String.format("upload/%s", target.getName()));

        result = BaseResult.success();
        result.setData(String.format("%s%s/%s", resourceProperties.getResourceUrl(), datePath, target.getName()));

        return result;
    }
}
