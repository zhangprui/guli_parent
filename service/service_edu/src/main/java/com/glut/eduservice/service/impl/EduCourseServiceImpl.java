package com.glut.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glut.eduservice.entity.EduCourse;
import com.glut.eduservice.entity.EduCourseDescription;
import com.glut.eduservice.entity.vo.CourseInfoVo;
import com.glut.eduservice.entity.vo.CoursePublishVo;
import com.glut.eduservice.mapper.EduCourseMapper;
import com.glut.eduservice.service.EduCourseDescriptionService;
import com.glut.eduservice.service.EduCourseService;
import com.glut.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zpr
 * @since 2021-07-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    //添加课程基本信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1 向课程信息表里面添加课程信息
        //把courseInfoVo对象转换成eduCourse对象，再传入课程信息表中
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert<=0){
            throw new GuliException(20001,"添加课程信息失败");
        }

        //添加课程后，获取添加的课程的id
        String cid = eduCourse.getId();

        //2 向课程简介表里添加简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        //手动设置课程id是课程描述的id，建立两个表之间的联系
        eduCourseDescription.setId(cid);
        courseDescriptionService.save(eduCourseDescription);

        return cid;
    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        //2 查询课程的描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
//        BeanUtils.copyProperties(courseDescription,courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if(i == 0){
            throw new GuliException(20001,"修改课程信息失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        courseDescriptionService.updateById(eduCourseDescription);


    }

    //根据课程id查询课程确认的信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用写好的mapper
        return baseMapper.getPublishCourseInfo(id);
    }
}
