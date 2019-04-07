package com.qpf.website.entity;

import com.qpf.website.commons.persistence.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class Content extends BaseEntity {
    @NotNull(message = "父级类目id")
    private Integer categoryId;
    @Length(min = 1, max = 20, message = "标题长度介于 1 - 20 个字符之间")
    private String title;
    @Length(min = 1, max = 20, message = "子标题长度介于 1 - 20 个字符之间")
    private String subTitle;
    @Length(min = 1, max = 50, message = "标题描述长度介于 1 - 50 个字符之间")
    private String titleDesc;
    private String url;
    private String pic;
    @NotBlank(message = "内容不能为空")
    private String content;
    @NotNull(message = "父级类目不能为空")
    private ContentCategory contentCategory;
}
