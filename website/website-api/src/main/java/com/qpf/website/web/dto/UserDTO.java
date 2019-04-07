package com.qpf.website.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

public class UserDTO implements Serializable {
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String phone;
}
