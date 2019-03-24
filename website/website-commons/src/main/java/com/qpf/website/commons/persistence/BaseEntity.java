package com.qpf.website.commons.persistence;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class BaseEntity implements Serializable {
    private Integer id;
    private Date created;
    private Date updated;
}
