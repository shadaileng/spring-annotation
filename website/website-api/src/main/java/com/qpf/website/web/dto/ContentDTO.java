package com.qpf.website.web.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString
public class ContentDTO implements Serializable {
    private  Integer id;
    private Integer categoryId;
    private String title;
    private String subTitle;
    private String titleDesc;
    private String url;
    private String pic;
    private String content;
}
