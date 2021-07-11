package com.glut.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//一级分类
@Data
public class OneSubject {
    private String id;
    private String title;

    //建立一级分类和二级分类之间的关系，即一个一级分类中有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();

}
