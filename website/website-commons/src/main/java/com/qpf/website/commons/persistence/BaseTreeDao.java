package com.qpf.website.commons.persistence;

import java.util.List;
import java.util.Map;

public interface BaseTreeDao<T extends BaseTreeEntity> extends BaseDao<T> {

    List<Integer> selectParentIdByIds(List<Integer> ids);

    int reduce(List<Integer> ids, Integer id);

    int increase(List<Integer> ids, Integer id);

    List<T> selectByPid(Integer pid);

    T selectById(int id);
}
