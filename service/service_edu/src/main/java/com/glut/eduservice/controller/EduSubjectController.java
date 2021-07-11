package com.glut.eduservice.controller;


import com.glut.commonutils.R;
import com.glut.eduservice.entity.subject.OneSubject;
import com.glut.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zpr
 * @since 2021-07-01
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来的文件，读取出文件的内容
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //得到上传过来的excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    //课程分类列表（树形结构）
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //list集合是一级分类，因为一级分类中不仅有它本身，还有二级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

