package com.myMVC.controller.annotate.bean;

import java.lang.annotation.*;

//通过Prototype注解实现beans的手动注入

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Prototype {
    String value()default "";
}
