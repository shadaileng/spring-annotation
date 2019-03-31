package com.qpf.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qpf.website.commons.persistence.BaseEntity;
import com.qpf.website.commons.utils.RegexpUtils;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
@Data
@ToString
public class User extends BaseEntity {
    @Length(min = 3, max = 20, message = "用户名长度介于3到20个字符之间")
    private String username;
    @JsonIgnore
    private String password;
    @Pattern(regexp = RegexpUtils.EMAIL, message = "电子邮箱格式不正确")
    private String email;
    @Pattern(regexp = RegexpUtils.PHONE, message = "手机号码格式不正确")
    private String phone;
}
