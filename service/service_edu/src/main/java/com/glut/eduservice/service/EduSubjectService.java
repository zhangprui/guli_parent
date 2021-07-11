package com.glut.eduservice.service;

import com.glut.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.glut.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zpr
 * @since 2021-07-01
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    //课程分类列表（树形结构）
    List<OneSubject> getAllOneTwoSubject();
}
