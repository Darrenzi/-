package com.cosmetics.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cosmetics.common.bean.PageRequest;
import com.cosmetics.common.entity.BaseEntity;

public class BaseService<T extends BaseEntity> extends ServiceImpl<BaseMapper<T>, T> implements IService<T>, CacheCrudService<T> {
    @Override
    public T get(int id) {
        return super.getById(id);
    }

    @Override
    public boolean add(T entity) {
        return super.save(entity);
    }

    @Override
    public boolean delete(int id) {
        return super.removeById(id);
    }

    @Override
    public boolean update(T entity) {
        return super.updateById(entity);
    }

    @Override
    public IPage<T> page(PageRequest pageRequest) {
        Page<T> page = new Page(pageRequest.getNum(), pageRequest.getSize());
        return super.page(page);
    }
}
