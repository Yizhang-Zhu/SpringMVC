package com.myMVC.controller.HttpMapRequest;



import com.myMVC.controller.IOC.bean.beanFactory;
import com.myMVC.controller.frameException.ExceptionCode;
import com.myMVC.controller.frameException.MVCException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;

//将request和response封装为model
public class RequestValueOperator {
    /**
     * 将request和response封装为model
     * @param request
     * @param response
     * @return
     * @throws MVCException
     */
    public static Model getRequestValue(HttpServletRequest request, HttpServletResponse response) throws MVCException {
        try {
            // 将request和response封装为Model
            Model model = (Model) beanFactory.getBean((Model.class).getSimpleName());
            // 对其私有属性进行封装，无set方法
            System.out.println("开始封装model");
            System.out.println(model.getClass());
            Field[] fields = model.getClass().getDeclaredFields();
            for (Field field : fields) {
                // 注入request和response
                if (field.getGenericType().getTypeName().equals("javax.servlet.http.HttpServletRequest")) {
                    field.setAccessible(true);
                    field.set(model, request);
                }
                if (field.getGenericType().getTypeName().equals("javax.servlet.http.HttpServletResponse")) {
                    field.setAccessible(true);
                    field.set(model, response);
                }
            }
            return model;
        }catch (IllegalAccessException e){
            // 成员变量无法访问
            throw new MVCException(ExceptionCode.AddModelFieldsException);
        }
    }
}
