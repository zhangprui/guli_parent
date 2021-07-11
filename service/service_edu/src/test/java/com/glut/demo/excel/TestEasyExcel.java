package com.glut.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        //实现excel写的操作
        //1.设置写入的文件夹的地址和excel文件名称
//        String fileName = "D:\\在校\\实战\\write.xlsx";

        //2.调用easyexcel中的方法实现写的操作
        //write方法两个参数：第一个参数 文件路径名称，第二个参数 实体类class
//        EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(getData());


        //实现excel读的操作
        String fileName = "D:\\在校\\实战\\write.xlsx";
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    //创建方法返回list集合
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for(int i = 0; i<10;i++){
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);
        }
        return list;
    }
}
