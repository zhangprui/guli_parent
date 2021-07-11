package com.glut.eduservice.controller;

import com.glut.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController     //此注解表示这个类交给spring管理，并且能够返回数据
@RequestMapping("/eduservice/user")       //此注解表示请求地址
@CrossOrigin        //此注解解决跨域问题
public class EduLoginController {
    //login
    @PostMapping("login")
    public R login(){

        return R.ok().data("token","admin");
    }
    //info
    @GetMapping("info")
    public R info(){

        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://img0.baidu.com/it/u=3870964477,3746012709&fm=26&fmt=auto&gp=0.jpg");
    }


}
