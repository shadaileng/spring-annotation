package com.qpf.manage.dao;

import com.qpf.manage.entity.User;

public interface UserDao {
    User getUserByEmailAndPassword(String email, String password);
}
