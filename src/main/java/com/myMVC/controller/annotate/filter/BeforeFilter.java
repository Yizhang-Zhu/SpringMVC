package com.myMVC.controller.annotate.filter;


import java.lang.annotation.*;

/**
 * request请求过滤器，在方法执行前调用
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BeforeFilter {
    String value() default "";
}
