package com.glut.eduservice.service;

import com.glut.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.glut.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zpr
 * @since 2021-07-02
 */
public interface EduChapterService extends IService<EduChapter> {
    //返回课程大纲列表，根据课程id进行查询
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //删除章节的方法
    boolean deleteChapter(String chapterId);
}
