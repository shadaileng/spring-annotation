package com.qpf.website.web.controller.v1;

import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.entity.Content;
import com.qpf.website.service.ContentService;
import com.qpf.website.web.dto.ContentDTO;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("${api.path.v1}/contents")
public class ContentController {

    @Autowired
    private ContentService service;

    @GetMapping("ppt")
    public BaseResult ppt() {
        BaseResult result;
        try {
            List<Content> contents = service.selectByCategoryId(89);
            List<ContentDTO> target = new ArrayList<>();

            for (Content content: contents) {
                ContentDTO dto = new ContentDTO();
                BeanUtils.copyProperties(dto, content);
                target.add(dto);
            }

            result = BaseResult.success("success", target);
        }catch (Exception e) {
            result = BaseResult.failed(String.format("Error: %s", e.getMessage()));
        }
        return result;
    }
}
