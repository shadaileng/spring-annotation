package com.qpf.website.commons.persistence;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class BaseTreeEntity extends BaseEntity {
    private Integer parentId;
    private Integer status;
    private Integer sortOrder;
    private Integer isParent;
    private BaseTreeEntity parent;
}
