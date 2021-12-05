package com.myMVC.controller.IOC.bean;

import com.myMVC.controller.MVCutils.packageClassScanner;
import com.myMVC.controller.annotate.bean.BeanScanner;
import com.myMVC.controller.annotate.filter.FilterOperator;
import com.myMVC.controller.annotate.handle.handleOperator;
import com.myMVC.controller.annotate.mvc.ControllerRequestMappingOperator;
import com.myMVC.controller.frameException.MVCException;

import java.util.Set;

//初始化beans
public class add_beans {
    /**
     * 框架初始化
     * @throws MVCException
     */
    public static void init() throws MVCException {
        //扫描包下的所有类
        System.out.println("开始注入beans");
        String packageName = "com";
        Set<Class<?>> classSet = packageClassScanner.getClasses(packageName);
        for(Class<?> item:classSet){
            try {

                // 处理Bean注解
                BeanScanner.operator_bean(item);
                // 处理Controller和RequestMapping注解
                ControllerRequestMappingOperator.operator_Controller(item);
                // 处理Filter注解的类
                FilterOperator.operator_filter(item);
                // 处理Handle注解的类
                handleOperator.operator_handle(item);
                System.out.println("注解生成完毕");

            } catch (MVCException e) {
                // Bean注解的错误，继续转发
                System.out.println("注解发生错误");

                throw e;
            }
        }
        System.out.println("结束初始化");
    }
}
