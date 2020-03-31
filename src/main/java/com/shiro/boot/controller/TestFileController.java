package com.shiro.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 文件 上传下载 测试类
 *
 * @author jzl
 * @date 2020/3/30 16:13
 */
@Controller
@RequestMapping("/file")
public class TestFileController {

    @RequestMapping("/fileUpload")
    public String fileUpload(){
        return "/fileUpload.html";
    }



}
