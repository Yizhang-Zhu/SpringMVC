package com.myMVC.controller.annotate.bean;

import java.lang.annotation.*;

//通过Autowired注解实现beans的手动注入

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Autowired {
    String value() default "";
}
