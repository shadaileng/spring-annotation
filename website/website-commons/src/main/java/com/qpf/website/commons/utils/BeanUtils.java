package com.qpf.website.commons.utils;

import com.qpf.website.commons.persistence.BaseEntity;

import java.lang.reflect.Method;
import java.util.*;

public class BeanUtils {
    public static List<String> loadFields(Class clazz) {
        List<String> fields = new ArrayList<>();
        while (clazz != null) {
            Arrays.asList(clazz.getDeclaredFields()).forEach((el)->{
                boolean isSup = el.getType().getSuperclass().equals(BaseEntity.class);
                if (!el.getName().equals("serialVersionUID") && !el.getName().equals("id") && !isSup) {
                    fields.add(el.getName());
                }
//            System.out.println(el.getName());
            });
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
    public static Map<String, String> loadMethods (final Class clazz) {
        Map<String, String> methods = new HashMap<>();
        List<String> fields = loadFields(clazz);

        for (String field : fields) {
            String _method = String.format("get%s%s", field.substring(0, 1).toUpperCase(), field.substring(1));
            methods.put(field, _method);
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

    /**
     * 驼峰命名转下划线命名
     * @param str
     * @return
     */
    public static String camelToUnderline(String str) {
        if (str == null || str.trim().isEmpty()){
            return "";
        }

        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
