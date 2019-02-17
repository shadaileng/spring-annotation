package com.qpf.test;

import com.qpf.bean.Person;
import com.qpf.bean.Role;
import com.qpf.service.ModelService;
import com.qpf.service.PersonService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestRoleService extends BasicTest {
    @Autowired
    private ModelService<Role> modelService;
    @Test
    public void TestListPerson() {
        System.out.println(modelService);
        Role role = new Role();
        role.setName("PM - 项目经理");
        List<Role> list = modelService.queryCondition(role);
        System.out.println(list);
    }
}
