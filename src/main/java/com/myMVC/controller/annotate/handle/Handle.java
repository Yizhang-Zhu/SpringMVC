package com.myMVC.controller.annotate.handle;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
//拦截器类
public @interface Handle {
    String value()default "";
}
