package com.myMVC.controller.annotate.filter;

import com.myMVC.controller.IOC.bean.beanFactory;
import com.myMVC.controller.IOC.filter.beforeFilterFactory;
import com.myMVC.controller.IOC.filter.afterFilterFactory;

import java.lang.reflect.Method;

/**
 * 处理BeforeFilter和AfterFilter注解
 */
public class FilterOperator {
    /**
     * 输入一个class实例，根据注解进行操作，过滤器注入
     * @param item
     */
    public static void operator_filter(Class<?> item) {
        boolean isFilter = item.isAnnotationPresent(Filter.class);
        if (!isFilter) {
            return;
        }

        //加入beanFactory
        beanFactory.addBean(item.getSimpleName(),item);

        // 类方法为空不继续执行
        Method[] methods = item.getMethods();
        if (methods == null || methods.length <= 0) {
            return;
        }
        String operator_url = item.getAnnotation(Filter.class).value();
        for(Method method:methods){
            boolean annotationBeforeFilter = method.isAnnotationPresent(BeforeFilter.class);
            if (annotationBeforeFilter) {
                //将url与method加入Factory
                beforeFilterFactory.addURLMethod(operator_url,method);
            }
            boolean annotationAfterFilter = method.isAnnotationPresent(AfterFilter.class);
            if (annotationAfterFilter) {
                //将url与method加入methodFactory
                afterFilterFactory.addURLMethod(operator_url,method);
            }
        }
    }
}
