package com.zscat.mallplus.config;

import com.zscat.mallplus.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常处理器
 *
 * @author ieflex
 */
@RestControllerAdvice
@Slf4j
public class InterfaceExceptionHandler {

    /**
     * 接口 业务异常
     */
    @ResponseBody
    @ExceptionHandler(ClassCastException.class)
    public Object businessInterfaceException(ClassCastException e) {
        log.error(e.getMessage(), e);
        e.printStackTrace();
        return new CommonResult().fail(100);
    }

    /**
     * 拦截所有运行时的全局异常   
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Object runtimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        // 返回 JOSN
        return new CommonResult().fail(500);
    }

    /**
     * 系统异常捕获处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object exception(Exception e) {
        log.error(e.getMessage(), e);
        return new CommonResult().fail(500);
    }
}
