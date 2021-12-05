package com.myMVC.controller.proxy;


import com.myMVC.controller.annotate.bean.Bean;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Bean
//增强类需要声明为bean,增强类必须继承mainProxy
public abstract class mainProxy implements MethodInterceptor {
    //创建增强对象
    public Object getInstance(Class<?> clazz){
        //创建增强类
        Enhancer enhancer = new Enhancer();
        //设置生成的代理类的父类为传入的对象类
        enhancer.setSuperclass(clazz);
        //设置方法的拦截回调为当前对象(方法拦截的时候会调用intercept方法)
        enhancer.setCallback(this);
        //返回创建的动态代理对象(Enhancer有静态方法create可以直接调用)
        return enhancer.create();
    }

    //子类修改此方法
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }
}
