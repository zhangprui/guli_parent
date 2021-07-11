package com.glut.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glut.eduservice.entity.EduSubject;
import com.glut.eduservice.entity.excel.SubjectData;
import com.glut.eduservice.entity.subject.OneSubject;
import com.glut.eduservice.entity.subject.TwoSubject;
import com.glut.eduservice.listener.SubjectExcelListener;
import com.glut.eduservice.mapper.EduSubjectMapper;
import com.glut.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zpr
 * @since 2021-07-01
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {

        try{
            //得到文件的输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //课程分类列表（树形结构）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1 查询所有的一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2 查询所有的二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于存储最终封装的数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //3 封装一级分类
        //遍历查询出来的一级分类的list集合，将一级分类的数据封装到要求的集合中去
        for(int i = 0; i<oneSubjectList.size();i++){
            //获取eduSubject中的每个对象
            EduSubject eduSubject = oneSubjectList.get(i);

            //将每个eduSubject对象放到oneSubject中去
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject,oneSubject);//等同于上两行代码

            //将oneSubject放到最终的集合中去
            finalSubjectList.add(oneSubject);

            //创建list集合封装每一个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();

            //在一级分类的for循环里面遍历二级分类 ,并做封装
            for (int j = 0; j < twoSubjectList.size(); j++) {
                EduSubject tSubject = twoSubjectList.get(j);
                if(tSubject.getParentId().equals(oneSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把一级分类下的所有二级分类放到一级分类中去
            oneSubject.setChildren(twoFinalSubjectList);
        }

        //4 封装二级分类

        return finalSubjectList;
    }
}
