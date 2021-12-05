package com.myMVC.controller.annotate.bean;


import com.myMVC.controller.IOC.bean.beanFactory;
import com.myMVC.controller.frameException.ExceptionCode;
import com.myMVC.controller.frameException.MVCException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

//注入Bean
public class BeanScanner {
    /**
     * 将Bean注解的类生成为beans并加入factory
     * @param item
     * @throws MVCException
     */
    public static void operator_bean(Class<?> item) throws MVCException {
        try {
            //存在Bean注解
            if (item.isAnnotationPresent(Bean.class)) {
                Method[] methods = item.getMethods();
                for (Method method : methods) {
                    //存在AddBeans注解
                    if (method.isAnnotationPresent(AddBeans.class)) {
                        //执行方法
                        Map<String, Object> result = (Map<String, Object>) method.invoke(null);
                        beanFactory.addBean(result);
                        break;
                    }
                }
                beanFactory.addBean(item);
            }
        }catch (IllegalAccessException e){
            //设置或获取字段或调用方法，但当前执行的方法无法访问指定的类，字段，方法或构造函数的定义
            throw new MVCException(ExceptionCode.AddBeansException);
        }catch (InvocationTargetException e){
            //当被调用的方法的内部抛出了异常而没有被捕获，AddBeans方法体内部方法throws异常
            throw new MVCException(ExceptionCode.RunAddBeansException);
        }
    }
}
