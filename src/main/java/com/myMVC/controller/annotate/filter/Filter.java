package com.myMVC.controller.annotate.filter;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
/**
 * url为过滤器需要过滤的url
 */
public @interface Filter {
    String value() default "";
}
