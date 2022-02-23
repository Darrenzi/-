package com.cosmetics.common.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cosmetics.common.bean.PageRequest;

/**
 * 带有缓存的增删查改方法
 */
public interface CacheCrudService<T> {

    T get(int id);

    boolean add(T entity);

    boolean delete(int id);

    boolean update(T entity);

    IPage<T> page(PageRequest pageRequest);
}
