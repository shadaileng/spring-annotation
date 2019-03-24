package com.qpf.website.commons.utils;

import java.lang.reflect.Method;
import java.util.*;

public class BeanUtils {
    public static List<String> loadFields(Class clazz) {
        List<String> fields = new ArrayList<>();
        while (clazz != null) {
            Arrays.asList(clazz.getDeclaredFields()).forEach((el)->{
                if (!el.getName().equals("serialVersionUID") && !el.getName().equals("id"))
                    fields.add(el.getName());
//            System.out.println(el.getName());
            });
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
    public static Map<String, String> loadMethods(Class clazz) {
        Map<String, String> methods = new HashMap<>();
        List<String> fields = loadFields(clazz);

        while (clazz != null) {
            Arrays.asList(clazz.getMethods()).forEach((el)->{
                String name = el.getName();
                String symbol = "get";
                int len = symbol.length();
                name.substring(name.lastIndexOf(symbol) + len);
                String field = String.format("%s%s", name.substring(len, len + 1).toLowerCase(), name.substring(name.lastIndexOf(symbol) + len + 1));
                if (fields.contains(field)) {
                    methods.put(field, name);
                }
            });
            clazz = clazz.getSuperclass();
        }
        return  methods;
    }

    public static Object getValue(Object o, String _method) {
        try {
            Method method = o.getClass().getMethod(_method, null);
            Object value = method.invoke(o, null);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
