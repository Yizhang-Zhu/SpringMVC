package com.myMVC.controller.annotate.bean;

import java.lang.annotation.*;

//通过AddBeans注解实现beans的手动注入

/**
 * AddBeans注解用于Map<String id , Object obj>返回值静态方法，返回new出来的Bean的实例来注入
 */


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AddBeans {
    String value() default "";
}
