package com.cosmetics.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cosmetics.common.bean.PageRequest;
import com.cosmetics.common.bean.Response;
import com.cosmetics.common.entity.BaseEntity;
import com.cosmetics.common.service.BaseService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Component
public abstract class BaseController<T extends BaseEntity> {
    @Autowired
    public BaseService<T> service;

    @ApiOperation("根据id获取数据")
    @GetMapping("/get")
    public Response<T> get(int id) {
        T entity = service.get(id);
        if (entity == null) {
            return Response.fail("数据不存在");
        }

        return Response.success(entity);
    }

    @ApiOperation("根据id删除数据")
    @PostMapping("/delete")
    public Response<Object> delete(int id) {
        LinkedHashMap<String, Integer> data = new LinkedHashMap<>();
        service.delete(id);
        data.put("id", id);
        return Response.success(data);
    }

    @ApiOperation("添加数据，前端应检查数据完整性")
    @PostMapping("/add")
    public Response<Object> add(@Validated T entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            return Response.fail("参数错误", errors);
        }
        if (service.add(entity)) {
            LinkedHashMap<String, Integer> data = new LinkedHashMap<>();
            data.put("id", entity.getId());
            return Response.success(data);
        }
        return Response.fail("操作失败");
    }

    @ApiOperation("根据id和传入字段更新数据，参数为所需更新的字段")
    @PostMapping("/update")
    public Response<Integer> update(T entity) {
        entity.setUpdateAt(new Date());
        service.update(entity);
        return Response.success(entity.getId());
    }

    @ApiOperation("分页获取数据")
    @GetMapping("/page")
    public Response<Object> getPage(@Validated PageRequest pageRequest) {
        IPage<T> data = service.page(pageRequest);
        return Response.success(data);
    }
}
