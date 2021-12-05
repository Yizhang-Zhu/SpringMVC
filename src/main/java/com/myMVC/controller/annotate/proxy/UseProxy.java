package com.myMVC.controller.annotate.proxy;


import java.lang.annotation.*;


//注解value = 增强器的类名(simple_name)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UseProxy {
    String value() default "";
}
