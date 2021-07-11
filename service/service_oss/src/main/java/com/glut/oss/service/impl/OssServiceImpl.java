package com.glut.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.glut.oss.service.OssService;
import com.glut.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    //上传文件到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String fileName = file.getOriginalFilename();

            //1.在文件名称中添加随机的唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid+fileName;

            //2.把文件按日期进行分类
            //   2019/11/11/01.jpg
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");

            fileName = datePath + "/" + fileName;

            // 调用oss方法实现上传
            //第一个参数  bucket名称
            //第二个参数  上传到oss文件的路径和文件名称
            //第三个参数  上传文件输入流
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传文件之后的路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //https://edu-guli-2103.oss-cn-beijing.aliyuncs.com/Saved%20Pictures/%E6%9E%9C%E5%9B%AD.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
