package com.qpf.website.absract;

import com.qpf.website.commons.persistence.BaseProvider;
import com.qpf.website.commons.utils.BeanUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.*;

public class AbstractProvider implements BaseProvider {
    protected static List<String> fields = new ArrayList<>();
    protected static Map<String, String> getMethods = new HashMap<>();
    protected static String tableName;
    protected static List<String> exclude = Arrays.asList(new String[]{"length", "start"});

    @Override
    public String insert(Object o) {
        SQL sql = new SQL();
        sql.INSERT_INTO(tableName);
        fields.forEach((el)->{
            sql.VALUES(BeanUtils.camelToUnderline(el), String.format("#{%s}", el));
        });

        return sql.toString();
    }

    @Override
    public String update(Object o) {
        SQL sql = new SQL();

        sql.UPDATE(tableName);
        sql.WHERE("id=#{id}");
        fields.forEach((el)->{
            if(BeanUtils.getValue(o, getMethods.get(el)) != null){
                sql.SET(String.format("%s = #{%s}", BeanUtils.camelToUnderline(el), el));
            }
        });
        return sql.toString();
    }

    @Override
    public String selectById() {
        return new SQL(){
            {
                SELECT("*");
                FROM(tableName);
                WHERE(" id = #{id} ");
            }
        }.toString();
    }

    @Override
    public String count(Object o) {
        return new SQL(){
            {
                SELECT("COUNT(*)");

                FROM(tableName);

                fields.forEach((el)->{
                    if(BeanUtils.getValue(o, getMethods.get(el)) != null){
                        WHERE(String.format("%s like '%%' || #{%s}  || '%%'", BeanUtils.camelToUnderline(el), el));
                    }
                });

            }
        }.toString();
    }

    @Override
    public String page(Map map) {
        SQL sql = new SQL();
//        System.out.println(map);

        sql.SELECT("*");
        sql.FROM(tableName);

        fields.forEach((el)->{
            Object value = map.get(el);
            if(value != null && !exclude.contains(el)){
                sql.WHERE(String.format("%s like '%%' || #{%s}  || '%%'", BeanUtils.camelToUnderline(el), el));
            }
        });

        String length = "5";
        String start = "0";

        String page = String.format(" LIMIT %s OFFSET %s ", map.get("length") == null ? length : "#{length}", map.get("start") == null ? start : "#{start}");
        String str = String.format("%s %s", sql.toString(), page);

        return str;
    }

    @Override
    public String deleteByIds(Map map) {
        SQL sql = new SQL();

        @SuppressWarnings("unchecked")
        List<String> ids = (List<String>) map.get("ids");

        sql.DELETE_FROM(tableName);
        String str = ids.toString();
        str = str.replace("[", "(");
        str = str.replace("]", ")");
        sql.WHERE(String.format(" id in %s", str));

        return sql.toString();
    }
}
