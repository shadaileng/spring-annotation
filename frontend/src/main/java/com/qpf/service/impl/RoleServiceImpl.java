package com.qpf.service.impl;

import com.qpf.bean.Role;
import com.qpf.bean.dto.Page;
import com.qpf.dao.RoleMapper;
import com.qpf.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl implements ModelService<Role> {
    @Autowired
    private RoleMapper roleMapper;
    public Role query(Role role) {
        return roleMapper.selectRoleCondition(role);
    }

    @Override
    public Role queryById(Integer id) {
        return roleMapper.queryRoleById(id);
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        int delete = roleMapper.deleteRoleByIds(ids);
        return delete;
    }

    @Override
    public boolean assign(Integer id, List<Integer> integers) {
        return false;
    }

    @Override
    public List<Role> queryCondition(Role role) {
        return roleMapper.queryCondition(role);
    }

    @Override
    public List<Role> queryAll() {
        return roleMapper.queryAll();
    }

    @Override
    public List queryManyById(Integer id) {
        return null;
    }

    public Page<Role> queryPage(Map<String, Object> map) {
        int count = roleMapper.queryCount(map);
        List<Role> roles = roleMapper.queryRolePage(map);

        Page<Role> page = new Page<>();
        page.setData(roles);
        page.setIndex((Integer) map.get("index") / (Integer) map.get("size") + 1);
        page.setPageSize((Integer) map.get("size"));
        page.setPageCount((count - 1) / (Integer) map.get("size") + 1);
        page.setTotal(count);

        return page;
    }

    public int add(Role role) {
        int id = -1;
        try {
            id = roleMapper.addRole(role);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return id;
    }

    @Override
    public int edit(Role role) {
        int count = roleMapper.editRole(role);
        return count;
    }
}
