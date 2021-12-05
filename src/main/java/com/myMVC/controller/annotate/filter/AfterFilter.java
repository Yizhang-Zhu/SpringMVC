package com.myMVC.controller.annotate.filter;


import java.lang.annotation.*;

/**
 * response请求过滤器，在方法执行后调用
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AfterFilter {
    String value() default "";
}
