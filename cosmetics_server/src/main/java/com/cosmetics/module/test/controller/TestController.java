package com.cosmetics.module.test.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cosmetics.common.bean.PageRequest;
import com.cosmetics.common.bean.Response;
import com.cosmetics.module.test.entity.Test;
import com.cosmetics.module.user.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.cosmetics.common.controller.BaseController;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2022-02-23
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController<Test> {
    @Autowired
    UserServiceImpl userService;

    @Override
    @ApiOperation("分页获取数据")
    @GetMapping("/page")
    public Response<Object> getPage(@Validated PageRequest pageRequest) {
        IPage<Test> data = service.page(pageRequest);
        List<Test> tests = data.getRecords();
        for(Test t: tests){
            t.setResearcher(userService.getById(t.getResearcherId()).getUsername());
            t.setTestor(userService.getById(t.getTestorId()).getUsername());
        }
        return Response.success(data);
    }
}
