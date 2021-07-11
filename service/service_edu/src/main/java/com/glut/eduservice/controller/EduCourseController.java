package com.glut.eduservice.controller;


import com.glut.commonutils.R;
import com.glut.eduservice.entity.EduCourse;
import com.glut.eduservice.entity.vo.CourseInfoVo;
import com.glut.eduservice.entity.vo.CoursePublishVo;
import com.glut.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zpr
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //课程列表 基本实现
    //TODO 完成成条件查询带分页功能
    @GetMapping("getCourseList")
    public R getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加课程之后生成的课程id，为后面添加课程大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认的信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程最终发布  修改课程的状态（修改edu_course表中的status字段，将draft改为normal即可）
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//课程发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }

}

