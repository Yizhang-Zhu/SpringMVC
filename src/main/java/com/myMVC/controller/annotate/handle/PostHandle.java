package com.myMVC.controller.annotate.handle;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
//拦截器方法,后拦截器
public @interface PostHandle {
    String value() default "";
}
