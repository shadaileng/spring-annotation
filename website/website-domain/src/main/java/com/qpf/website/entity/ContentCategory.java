package com.qpf.website.entity;

import com.qpf.website.commons.persistence.BaseEntity;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ContentCategory extends BaseEntity {
    private Integer parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;
    private Integer isParent;
    private ContentCategory parent;
}
