package com.myMVC.controller.IOC.bean;


import com.myMVC.controller.MVCutils.classNameOperator;
import com.myMVC.controller.annotate.bean.Autowired;
import com.myMVC.controller.annotate.bean.Prototype;
import com.myMVC.controller.annotate.connect_mybatis.mybatisMapperOperator;
import com.myMVC.controller.annotate.connect_mybatis.sqlSessionsOperator;
import com.myMVC.controller.annotate.proxy.UseProxy;
import com.myMVC.controller.IOC.Factory;
import com.myMVC.controller.frameException.ExceptionCode;
import com.myMVC.controller.frameException.MVCException;
import com.myMVC.controller.proxy.mainProxy;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

//bean的工厂，存储全部的bean
public class beanFactory implements Factory {
    private static Map<String, beans> beanMap = new HashMap<String, beans>();

    /**
     * 将bean加入map
     * @param beanClass
     */
    public static void addBean(Class<?> beanClass) {
        beans add_bean = new beans();
        add_bean.setId(beanClass.getSimpleName());
        add_bean.setObject_class(beanClass);
        add_bean.setObject(null);
        //是否使用原型模式
        if(beanClass.isAnnotationPresent(Prototype.class)) {
            add_bean.setPrototype(true);
        }else{
            add_bean.setPrototype(false);
        }
        beanMap.putIfAbsent(beanClass.getSimpleName(), add_bean);
    }

    /**
     * 默认方式注入bean，使用无参数构造函数
     * @param name
     * @param beanClass
     */
    public static void addBean(String name,Class<?> beanClass) {
        beans add_bean = new beans();
        add_bean.setId(name);
        add_bean.setObject_class(beanClass);
        add_bean.setObject(null);
        //是否使用原型模式
        if(beanClass.isAnnotationPresent(Prototype.class)) {
            add_bean.setPrototype(true);
        }else{
            add_bean.setPrototype(false);
        }
        beanMap.putIfAbsent(name, add_bean);
    }

    /**
     * 加入自定义的bean
     * @param bean_map
     */
    public static void addBean(Map<String,Object> bean_map){
        //注入beans
        for(Map.Entry<String, Object> entry : bean_map.entrySet()) {
            beans bean = new beans();
            bean.setId(entry.getKey());
            bean.setPrototype(false);
            bean.setObject_class(entry.getValue().getClass());
            bean.setObject(entry.getValue());
            beanMap.putIfAbsent(entry.getKey(),bean);
        }
    }


    /**
     * 根据名称返回实例对象
     * @param name
     * @return
     * @throws MVCException
     */
    public static Object getBean(String name) throws MVCException {
        try {
            if (beanMap.get(name) != null) {
                beans re_bean = beanMap.get(name);
                //判断模式
                if (re_bean.getPrototype()) {
                    //原型模式,生成新对象并返回,beanFactory不负责管理声明prototype 的 bean
                    return beanFactory.prototype_getBean(re_bean.getObject_class(), true);
                } else {
                    //单例模式
                    //生成对象.无参数构造函数
                    if (re_bean.getObject() == null) {
                        //不存在实例对象时
                        //判断此类有没有增强，有增强则通过增强获取类的实例
                        Object bean = re_bean.getObject_class().getDeclaredConstructor().newInstance();
                        if(re_bean.getObject_class().isAnnotationPresent(UseProxy.class)){
                            //找到增强器
                            if(re_bean.getObject_class().getAnnotation(UseProxy.class).value()!=null) {
                                Object proxy = getBean(re_bean.getObject_class().getAnnotation(UseProxy.class).value());
                                System.out.println(proxy.getClass().toString());
                                if(proxy!=null){
                                    //获取增强
                                    bean = ((mainProxy)proxy).getInstance(re_bean.getObject_class());
                                }
                            }
                        }
                        //扫描属性，autowired，注入依赖
                        Field[] fields = re_bean.getObject_class().getDeclaredFields();
                        if (fields != null && fields.length >= 0) {
                            for (Field field : fields) {
                                //有Autowired的属性
                                if (field.isAnnotationPresent(Autowired.class)) {
                                    //获取变量声明对象
                                    String type = field.getGenericType().toString();
                                    //对于自定义类型操作
                                    //强制修改私有变量
                                    field.setAccessible(true);
                                    //获取变量类型的class对象
                                    Class<?> fieldclass = Class.forName(classNameOperator.getClassName(type));
                                    System.out.println("获取" + fieldclass.getSimpleName());
                                    field.set(bean, beanFactory.getBean(fieldclass.getSimpleName()));
                                }
                                //处理mapper注解
                                mybatisMapperOperator.mybatisMapper_Operator(bean, field);

                                //处理sqlSessions注解
                                sqlSessionsOperator.sqlSesssions_operator(bean, field);

                            }
                        }
                        re_bean.setObject(bean);
                        beanMap.replace(name, re_bean);
                        return bean;
                        //根据new_beans注入bean
                    } else {
                        //存在实例对象，直接返回
                        return re_bean.getObject();
                    }

                }
            }
            return null;
        }catch (ClassNotFoundException e){
            //找不到Autowired变量类型的Class对象
            throw new MVCException(ExceptionCode.NotFindAutowiredClassException);
        }catch (IllegalAccessException e){
            //没有权限对属性注入对象
            throw new MVCException(ExceptionCode.AutowiredBeansException);
        }catch (NoSuchMethodException e){
            //找不到默认无参数构造函数
            throw new MVCException(ExceptionCode.MethodNotFindException);
        }catch (InstantiationException e){
            //无法实例化该类
            throw new MVCException(ExceptionCode.ClassInstanceException);
        }catch (InvocationTargetException e){
            //构造函数内部异常
            throw new MVCException(ExceptionCode.InvocationMethodException);
        }
    }

    /**
     * 原型模型下获取Bean注解类的对象
     * @param bean_class
     * @param isPrototype
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    private static Object prototype_getBean(Class<?> bean_class,Boolean isPrototype) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Object bean = bean_class.getDeclaredConstructor().newInstance();
        //扫描属性，autowired，注入依赖
        Field[] fields = bean_class.getDeclaredFields();
        if (fields != null && fields.length >= 0) {
            for (Field field : fields) {
                //有Autowired的属性
                if (field.isAnnotationPresent(Autowired.class)) {
                    //获取变量声明对象
                    String type = field.getGenericType().toString();
                    //对于自定义类型操作
                    //强制修改私有变量
                    field.setAccessible(true);
                    //获取变量类型的class对象
                    Class<?> fieldclass = Class.forName(classNameOperator.getClassName(type));
                    field.set(bean, beanFactory.prototype_getBean(fieldclass,isPrototype));
                }
            }
        }
        return bean;
    }

    /**
     * 输出全部bean
     */
    public static void printAll(){
        System.out.println("*****************************************");
        for(beans item:beanMap.values()){
            if(item.getObject()!=null) {
                System.out.println(item.getObject().toString() + " " + item.getObject_class().toString());
            }else{
                System.out.println("null" + " " + item.getObject_class().toString());
            }
        }
        System.out.println("*****************************************");
    }
}
