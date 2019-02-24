package com.qpf.service.impl;

import com.qpf.bean.Permission;
import com.qpf.bean.dto.Page;
import com.qpf.dao.PermissionMapper;
import com.qpf.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("permissionService")
public class PermissionServiceImpl implements ModelService<Permission> {
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> queryCondition(Permission permission) {
        return null;
    }

    @Override
    public List<Permission> queryAll() {
        List<Permission> list = new ArrayList<>();
        List<Permission> permissions = permissionMapper.queryAll();
        Map<Integer, Permission> permissionMap = new HashMap<>();
        permissions.forEach((el) -> {
            permissionMap.put(el.getId(), el);
        });
        permissions.forEach((el) -> {
            if (el.getpId() == 0) {
                list.add(el);
            } else {
                permissionMap.get(el.getpId()).getChildren().add(el);
            }
        });
        return list;
    }

    @Override
    public List queryManyById(Integer id) {
        return null;
    }

    @Override
    public Page<Permission> queryPage(Map<String, Object> map) {
        return null;
    }

    @Override
    public int add(Permission permission) throws Exception {
        return 0;
    }

    @Override
    public int edit(Permission permission) throws Exception {
        return 0;
    }

    @Override
    public Permission query(Permission permission) {
        return null;
    }

    @Override
    public Permission queryById(Integer id) {
        return null;
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        return 0;
    }

    @Override
    public boolean assign(Integer id, List<Integer> integers) {
        return false;
    }
}
