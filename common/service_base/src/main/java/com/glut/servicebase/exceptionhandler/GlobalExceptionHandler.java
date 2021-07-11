package com.glut.servicebase.exceptionhandler;


import com.glut.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //全局异常处理
    @ExceptionHandler(Exception.class) //此注解规定出现哪类异常时，执行此方法
    @ResponseBody //此注解为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理......");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class) //此注解规定出现哪类异常时，执行此方法
    @ResponseBody //此注解为了返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理......");
    }
    //自定义异常处理
    @ExceptionHandler(GuliException.class) //此注解规定出现哪类异常时，执行此方法
    @ResponseBody //此注解为了返回数据
    public R error(GuliException e){
        log.error(e.getMessage());//将error级别的日志写入日志文件中去
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
