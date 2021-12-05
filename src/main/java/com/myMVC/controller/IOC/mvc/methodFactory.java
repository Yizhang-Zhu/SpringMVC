package com.myMVC.controller.IOC.mvc;

import com.myMVC.controller.IOC.Factory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

//存储url - method的映射表
public class methodFactory implements Factory {

    private static Map<String, Method> methodMap = new HashMap<String, Method>();

    /**
     * 将方法加入map
     * @param url
     * @param method
     */
    public static void addURLMethod(String url,Method method){
        if(!methodMap.containsKey(url)){
            methodMap.put(url, method);
        }
    }

    /**
     * 根据url查找方法 ，key不存在返回null
     * @param url
     * @return
     */
    public static Method getMethod(String url){
        return methodMap.get(url);
    }

    /**
     * 输出全部
     */
    public static void printAll(){
        for(Method item:methodMap.values()){
            System.out.println(item.toString());
        }
    }
}
