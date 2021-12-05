package com.myMVC.controller.annotate.connect_mybatis;

import java.lang.annotation.*;

//sql session会话
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SqlSessions {
    String value()default "";
}
