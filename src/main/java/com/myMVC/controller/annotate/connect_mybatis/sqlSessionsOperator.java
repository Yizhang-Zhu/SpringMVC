package com.myMVC.controller.annotate.connect_mybatis;


import com.myMVC.controller.connect_mybatis.mybatisSession;
import com.myMVC.controller.frameException.ExceptionCode;
import com.myMVC.controller.frameException.MVCException;


import java.lang.reflect.Field;

//处理sql session相关操作的类
public class sqlSessionsOperator {
    /**
     * 处理SqlSessions注解
     * @param object
     * @param field
     * @throws MVCException
     */
    public static void sqlSesssions_operator(Object object,Field field) throws MVCException {
        try {
            if (field.isAnnotationPresent(SqlSessions.class)) {
                field.setAccessible(true);
                field.set(object, mybatisSession.getSession());
            }
        }catch (IllegalAccessException e){
            //无法注入SqlSession
            throw new MVCException(ExceptionCode.AddSqlSessionException);
        }
    }
}
