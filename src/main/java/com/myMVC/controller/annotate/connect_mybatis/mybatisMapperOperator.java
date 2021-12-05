package com.myMVC.controller.annotate.connect_mybatis;

import com.myMVC.controller.MVCutils.classNameOperator;
import com.myMVC.controller.connect_mybatis.mybatisSession;
import com.myMVC.controller.frameException.ExceptionCode;
import com.myMVC.controller.frameException.MVCException;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Field;

public class mybatisMapperOperator {
    /**
     * 处理mybatisMapper注解，注入mapper
     * @param obj
     * @param item
     * @throws MVCException
     */
    public static void mybatisMapper_Operator(Object obj, Field item) throws MVCException {
        try {
            if (item.isAnnotationPresent(MybatisMapper.class)) {
                SqlSession sqlSession = mybatisSession.getSession();
                item.setAccessible(true);
                item.set(obj, sqlSession.getMapper(Class.forName(classNameOperator.getClassName(item.getGenericType().toString()))));
            }
        }catch (ClassNotFoundException e){
            // 没有找到MybatisMapper注解标注的属性的类
            throw new MVCException(ExceptionCode.NotFindMybatisMapperClassException);
        }catch (IllegalAccessException  e){
            // MybatisMapper注解标注的属性不能被注入对象
            throw new MVCException(ExceptionCode.AddMybatisMapperException);
        }
    }
}