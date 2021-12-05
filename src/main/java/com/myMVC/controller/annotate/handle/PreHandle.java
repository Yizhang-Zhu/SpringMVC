package com.myMVC.controller.annotate.handle;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
//前拦截器
public @interface PreHandle {
    String value() default "";
}
