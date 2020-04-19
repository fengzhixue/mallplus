package com.cxytiandi.encrypt.springboot.annotation;

import java.lang.annotation.*;

/**
 * 加密注解
 * <p>
 * <p>加了此注解的接口将进行数据加密操作<p>
 *
 * @author zscat
 * @about 2019-04-30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt {

    String value() default "";

}
