package com.myMVC.controller.annotate.mvc;

import java.lang.annotation.*;

//路由映射
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface RequestMapping {
    String value() default "";
}
