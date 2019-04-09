package com.qpf.website.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserDTO implements Serializable {
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String phone;
}
