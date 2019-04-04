package com.qpf.website.entity;

import com.qpf.website.commons.persistence.BaseEntity;
import com.qpf.website.commons.persistence.BaseTreeEntity;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ContentCategory extends BaseTreeEntity {
    private String name;
}
