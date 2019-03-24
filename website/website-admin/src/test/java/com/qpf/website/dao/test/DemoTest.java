package com.qpf.website.dao.test;

import com.qpf.website.commons.utils.BeanUtils;
import com.qpf.website.entity.User;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Ignore
public class DemoTest {
    @Test
    public void testList() {
        List<String> list = Arrays.asList(new String[]{"1", "2", "3", "4"});
        System.out.println(list);
        String str = list.toString();
        str = str.replace("[", "(");
        str = str.replace("]", ")");
        System.out.println(str);
    }

    @Test
    public void testFieldAndMethod() throws Exception {

        Class clazz = User.class;
        while (clazz != null) {
            Arrays.asList(clazz.getDeclaredFields()).forEach((el)->{
                System.out.println(el.getName());
            });
            clazz = clazz.getSuperclass();
        }

        clazz = User.class;
        while (clazz != null) {
            Arrays.asList(clazz.getDeclaredMethods()).forEach((el) -> {
                System.out.println(el.getName());
            });
            clazz = clazz.getSuperclass();
        }
        User user = new User();
        user.setCreated(new Date());
        clazz = User.class;
//        while (clazz) {
//            clazz.getMethod()
//        }
        Method getCreated = User.class.getMethod("getCreated", null);
        Object invoke = getCreated.invoke(user, null);
        System.out.println(invoke);

        Object value = BeanUtils.getValue(user, "getCreated");
        System.out.println(value);
    }
}
