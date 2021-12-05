package com.myMVC.controller.IOC.filter;

import com.myMVC.controller.MVCutils.get_url_root;
import com.myMVC.controller.IOC.Factory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class afterFilterFactory implements Factory {

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
     * 根据url查找方法 ，key不存在返回null,op为是否增强，op=true时，当前url没有拦截器时会寻找
     * 上一级路径的拦截器
     * @param url
     * @param op
     * @return
     */
    public static Method getMethod(String url,boolean op){
        Method method = methodMap.get(url);
        if(method==null){
            //查找上一层路径的过滤器
            //获取上一层的目录
            if(op) {
                String root_url = get_url_root.get_url_root_faction(url);
                while (!root_url.equals("")) {
                    method = methodMap.get(root_url);
                    if (method != null) {
                        return method;
                    }
                    root_url = get_url_root.get_url_root_faction(root_url);
                }
            }
            return null;
        }else {
            return method;
        }
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
