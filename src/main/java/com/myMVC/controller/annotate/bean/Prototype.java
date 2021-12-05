package com.myMVC.controller.annotate.bean;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Prototype {
    String value()default "";
}
