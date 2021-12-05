package com.myMVC.controller.annotate.handle;

import com.myMVC.controller.IOC.bean.beanFactory;
import com.myMVC.controller.IOC.handleFactory.postHandleFactory;
import com.myMVC.controller.IOC.handleFactory.preHandleFactory;

import java.lang.reflect.Method;

//Handle的注入
public class handleOperator {
    /**
     * 输入一个class实例，根据注解进行操作，拦截器注入
     * @param item
     */
    public static void operator_handle(Class<?> item) {
        boolean isHandle = item.isAnnotationPresent(Handle.class);
        if (!isHandle) {
            return;
        }

        //加入beanFactory
        beanFactory.addBean(item.getSimpleName(),item);

        // 类方法为空不继续执行
        Method[] methods = item.getMethods();
        if (methods == null || methods.length <= 0) {
            return;
        }
        String operator_url = item.getAnnotation(Handle.class).value();
        for(Method method:methods){
            boolean annotationBeforeFilter = method.isAnnotationPresent(PreHandle.class);
            if (annotationBeforeFilter) {
                //将url与method加入Factory
                preHandleFactory.addURLMethod(operator_url,method);
            }
            boolean annotationAfterFilter = method.isAnnotationPresent(PostHandle.class);
            if (annotationAfterFilter) {
                //将url与method加入methodFactory
                postHandleFactory.addURLMethod(operator_url,method);
            }
        }
    }
}
