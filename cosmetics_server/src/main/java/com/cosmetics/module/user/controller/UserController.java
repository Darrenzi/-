package com.cosmetics.module.user.controller;


import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cosmetics.common.bean.Response;
import com.cosmetics.common.controller.BaseController;
import com.cosmetics.module.user.entity.User;
import com.cosmetics.module.user.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * @since 2022-02-15
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {

    @Autowired
    UserServiceImpl service;

    @ApiOperation("使用邮箱或用户名登录")
    @PostMapping("/login")
    public Response<String> login(@RequestParam("un") String un, @RequestParam("pd") String pd, HttpServletRequest request) {
        boolean isEmail = ReUtil.isMatch("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", un);
        QueryWrapper<User> query = new QueryWrapper();
        query.select("password", "id", "role", "username");
        if (!isEmail) {
            query.eq("username", un);
        }else{
            query.eq("email", un);
        }

        User user = service.list(query).get(0);
        if(user!= null && user.getPassword().equals(pd)) {
            HttpSession session = request.getSession();
            session.setAttribute("id", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            return Response.success(null);
        }
        return Response.fail("密码错误");
    }

    @GetMapping("/role")
    public Response<Object> getUserByRole(int role){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role",role);
        
        return Response.success(service.list(queryWrapper));
    }

    @PostMapping("/check")
    public Response<User> isLogin(HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        String username = (String) session.getAttribute("username");
        Integer role = (Integer) session.getAttribute("role");

        if(id==null||username==null||role==null)return Response.fail("未登录");
        QueryWrapper<User> query = new QueryWrapper();
        query.eq("username", username);
        int count = service.count(query);
        if(count==0)return Response.fail("未登录");

        User user = service.get(id);
        return Response.success(user);
    }

    @PostMapping("/search")
    public Response<Object> search(String condition){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("id", condition);
        queryWrapper.or().like("username", condition);

        return Response.success(service.list(queryWrapper));
    }
}
