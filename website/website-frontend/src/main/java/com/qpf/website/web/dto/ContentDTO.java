package com.qpf.website.web.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ContentDTO {
    private  Integer id;
    private Integer categoryId;
    private String title;
    private String subTitle;
    private String titleDesc;
    private String url;
    private String pic;
    private String content;
}
