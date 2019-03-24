package com.qpf.website.commons.persistence;

import java.util.Map;

public interface BaseProvider<T> {
    /**
     * 插入数据
     * @param t
     * @return
     */
    String insert(final T t);

    /**
     * 更新数据
     * @param t
     * @return
     */
    String update(final T t);

    /**
     * 批量删除数据
     * @param map
     * @return
     */
    String deleteByIds(Map<String, Object> map);

    /**
     * 根据id查询数据
     * @return
     */
    String selectById();

    /**
     * 计数
     * @param t
     * @return
     */
    String count(final T t);

    /**
     * 分页
     * @param map
     * @return
     */
    String page(Map<String, Object> map);
}
