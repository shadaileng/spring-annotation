package com.qpf.website.absract;

import com.qpf.website.commons.persistence.BaseEntity;
import com.qpf.website.commons.persistence.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class AbstractController<T extends BaseEntity, S extends BaseService> {

    @Autowired
    protected S service;

    public abstract String list(Map<String, Object> map) ;
    public abstract Object get(Integer id) ;
    public abstract Object add(T entity) ;
    public abstract Object update(T entity) ;
    public abstract Object delete(Integer[] ids) ;
    public abstract Object load() ;
    public abstract T loadObj(HttpServletRequest request);

    @ResponseBody
    @GetMapping("page")
    public Object loadPage(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer start, @RequestParam(required = false, defaultValue = "2") Integer length) {

        return service.page(start, length, loadObj(request));
    }
}
