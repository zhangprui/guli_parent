package com.glut.eduservice.controller;


import com.glut.commonutils.R;
import com.glut.eduservice.entity.EduVideo;
import com.glut.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zpr
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //查找小节
    @GetMapping("getVideo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId){
        EduVideo eduVideo = videoService.getById(videoId);
        return R.ok().data("eduVideo",eduVideo);
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

    //删除小节
    //TODO 此方法需要完善，删除小节时，该小节对应的视频也需要删除
    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        videoService.removeById(videoId);
        return R.ok();
    }

}

