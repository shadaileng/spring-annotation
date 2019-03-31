package com.qpf.website.dao;

import com.qpf.website.absract.AbstractProvider;
import com.qpf.website.commons.utils.BeanUtils;
import com.qpf.website.entity.Content;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ContentProvider extends AbstractProvider {
    {
        fields = BeanUtils.loadFields(Content.class);
        getMethods = BeanUtils.loadMethods(Content.class);
        tableName = Content.class.getSimpleName();
    }

    @Override
    public String selectById() {
        return new SQL(){
            {
                SELECT("c.*, cc.name as `contentCategory.name`, cc.id as `contentCategory.id`");
                FROM("content as c");
                LEFT_OUTER_JOIN("content_category cc on c.category_id = cc.id");
                WHERE(" c.id = #{id} ");
            }
        }.toString();
    }

    @Override
    public String page(Map map) {
        SQL sql = new SQL();

        sql.SELECT("c.*, cc.name as `contentCategory.name`, cc.id as `contentCategory.id`");
        sql.FROM("content as c");

        sql.LEFT_OUTER_JOIN("content_category cc on c.category_id = cc.id");

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
}
