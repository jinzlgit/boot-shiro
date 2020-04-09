package com.shiro.boot.controller;

import com.shiro.boot.common.result.ArgumentValidEntity;
import com.shiro.boot.common.result.R;
import com.shiro.boot.common.result.TestJSONResult;
import com.shiro.boot.common.result.validgroup.Creat;
import com.shiro.boot.common.result.validgroup.Update;
import com.shiro.boot.entity.SysUserEntity;
import com.shiro.boot.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 测试类
 *
 * @author jzl
 * @date 2020/3/27 11:00
 */
@Api(value = "Swagger2 在线接口文档")
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/list")
    public R list(){
        List<SysUserEntity> list = sysUserService.list();
//        return R.ok();
        return R.ok().data("userList", list).message("用户列表");
    }

    @GetMapping("/json")
    public R json(){
        List<TestJSONResult> list = new ArrayList<>();
        TestJSONResult jsonResult1 = new TestJSONResult();
        jsonResult1.setName("张三");
        jsonResult1.setPassword("123");
        jsonResult1.setBirthday(new Date());
        jsonResult1.setDesc("0000");
        list.add(jsonResult1);
        TestJSONResult jsonResult2 = new TestJSONResult();
        jsonResult2.setPassword("456");
        jsonResult2.setBirthday(new Date());
        jsonResult2.setDesc("1111");
        list.add(jsonResult2);
        return R.ok().data("jsonList", list).message("测试JSON返回").code(222);
    }

    @PostMapping("/add")
    public R add(@RequestBody TestJSONResult result){
        log.info(result.toString());
        log.info("密码: " + result.getPassword());
        return R.ok().data("object", result).message("请求对象内容");
    }

    @PutMapping("/update")
    public R update(){
        log.info("这是 PUT");
        return R.ok();
    }

    @DeleteMapping("/delete")
    public R delete(){
        log.info("这是 DELETE");
        return R.error();
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/addUser")
    public R addUser(@RequestBody @Validated(Creat.class) @ApiParam("用户信息") ArgumentValidEntity user) {
        return R.ok().data("user", user);
    }

    @PutMapping("/updateUser")
    public R updateUser(@RequestBody @Validated(Update.class)ArgumentValidEntity user) {
        return R.ok().data("user", user);
    }

}
