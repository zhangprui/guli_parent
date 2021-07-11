package com.glut.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.glut.eduservice.entity.EduChapter;
import com.glut.eduservice.entity.EduVideo;
import com.glut.eduservice.entity.chapter.ChapterVo;
import com.glut.eduservice.entity.chapter.VideoVo;
import com.glut.eduservice.mapper.EduChapterMapper;
import com.glut.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glut.eduservice.service.EduVideoService;
import com.glut.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zpr
 * @since 2021-07-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    //返回课程大纲列表，根据课程id进行查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1 根据课程id查询课程里面的所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2 根据课程id查询课程里面的所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //创建list集合用于最终的封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //3 遍历查询出来的章节list集合，进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter chapter = eduChapterList.get(i);

            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            finalList.add(chapterVo);

            //创建一个list集合用于封装小节的集合
            List<VideoVo> videoVoList = new ArrayList<>();

            for (int j = 0; j < eduVideoList.size(); j++) {
                EduVideo video = eduVideoList.get(j);
                if(video.getChapterId().equals(chapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    videoVoList.add(videoVo);
                }

            }
            chapterVo.setChildren(videoVoList);

        }

        //4 遍历查询小节list集合，进行封装

        return finalList;
    }

    //删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //1 根据章节chapterId查询小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //2 判断小节是否为空，为空则可以删除，否则无法删除
        if(count > 0){
            throw new GuliException(20001,"该章节下有小节，不能删除该章节");
        }
        int result = baseMapper.deleteById(chapterId);

        return result>0;

    }
}
