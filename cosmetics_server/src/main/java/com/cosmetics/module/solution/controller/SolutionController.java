package com.cosmetics.module.solution.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cosmetics.common.bean.PageRequest;
import com.cosmetics.common.bean.Response;
import com.cosmetics.module.solution.entity.Solution;
import com.cosmetics.module.solution.entity.UploadForm;
import com.cosmetics.module.solution.service.impl.SolutionServiceImpl;
import com.cosmetics.module.test.entity.Test;
import com.cosmetics.module.test.service.impl.TestServiceImpl;
import com.cosmetics.module.user.entity.User;
import com.cosmetics.module.user.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cosmetics.common.controller.BaseController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author Miranda
 * @since 2022-02-23
 */
@RestController
@RequestMapping("/solution")
public class SolutionController extends BaseController<Solution> {
    @Autowired
    TestServiceImpl testService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    SolutionServiceImpl solutionService;

    @Override
    @ApiOperation("根据id获取数据")
    @GetMapping("/get")
    public Response<Solution> get(int id) {
        Solution entity = service.get(id);
        Test test = testService.get(entity.getTestId());
        User tester = userService.get(test.getTestorId());
        User researcher = userService.get(test.getResearcherId());
        entity.setTestor(tester.getUsername());
        entity.setResearcherId(researcher.getId());
        entity.setResearcher(researcher.getUsername());
        entity.setNumber(test.getNumber());
        if (entity == null) {
            return Response.fail("数据不存在");
        }

        return Response.success(entity);
    }

    @ApiOperation("分页获取数据")
    @GetMapping("/page")
    public Response<Object> getPage(@Validated PageRequest pageRequest) {
        IPage<Solution> data = service.page(pageRequest);
        List<Solution> solutionList = data.getRecords();
        for(Solution entity:solutionList){
            Test test = testService.get(entity.getTestId());
            User tester = userService.get(test.getTestorId());
            User researcher = userService.get(test.getResearcherId());
            entity.setTestId(tester.getId());
            entity.setTestor(tester.getUsername());
            entity.setResearcherId(researcher.getId());
            entity.setResearcher(researcher.getUsername());
            entity.setNumber(test.getNumber());
        }
        return Response.success(data);
    }

    @ApiOperation("分页获取数据")
    @GetMapping("/report/page")
    public Response<Object> getReportPage(@Validated PageRequest pageRequest) {
        IPage<Solution> data = solutionService.getReports(pageRequest);
        List<Solution> solutionList = data.getRecords();
        for(Solution entity:solutionList){
            Test test = testService.get(entity.getTestId());
            User tester = userService.get(test.getTestorId());
            User researcher = userService.get(test.getResearcherId());
            entity.setTestId(tester.getId());
            entity.setTestor(tester.getUsername());
            entity.setResearcherId(researcher.getId());
            entity.setResearcher(researcher.getUsername());
            entity.setNumber(test.getNumber());
        }
        return Response.success(data);
    }

    @PostMapping("/upload")
    public Response<Object> upload(UploadForm form){
        Map<String, String> data = new HashMap<>();
        Solution solution = new Solution();
        solution.setId(form.getId());
        if(form.getAccessory() != null){
            String path = solutionService.saveFile(form.getAccessory(), form.getAccessoryType());
            if(path==null)return Response.fail("上传附件失败");
            data.put("photo",path);
            solution.setAccessory(path);
        }

        if(form.getPhoto()!=null){
            String path = solutionService.saveFile(form.getPhoto(), form.getPhotoType());
            if(path==null)return Response.fail("上传现场照片失败");
            data.put("photo",path);
            solution.setPhoto(path);
        }

        service.update(solution);
        return Response.success(data);
    }
}