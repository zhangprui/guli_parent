package com.glut.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glut.eduservice.entity.EduCourse;
import com.glut.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zpr
 * @since 2021-07-02
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

}
