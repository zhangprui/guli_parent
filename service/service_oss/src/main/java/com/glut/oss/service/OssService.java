package com.glut.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface OssService {
    //上传头像到oss的方法
    String uploadFileAvatar(MultipartFile file);
}
