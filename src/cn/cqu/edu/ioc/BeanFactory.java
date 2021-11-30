package cn.cqu.edu.ioc;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import cn.cqu.edu.annotations.Autowired;
import cn.cqu.edu.annotations.Bean;
import cn.cqu.edu.annotations.Component;
import cn.cqu.edu.annotations.Configuration;
import cn.cqu.edu.util.PackageScanner;

public class BeanFactory {
    private static final Map<String, BeanDefinition> beanPool = new HashMap<>();

    public static void scanPackage(String basePackage) throws ClassNotFoundException, IOException {
        new PackageScanner(basePackage) {
            @Override
            public void dealClass(Class<?> class2) {
                if (class2.isPrimitive() || class2.isArray() || class2.isInterface() ||
                class2.isEnum() || class2==String.class || class2.isAnnotation() ||
                !class2.isAnnotationPresent(Component.class) || class2.isAnnotationPresent(Configuration.class)) return;
                try {
                    if(class2.isAnnotationPresent(Configuration.class)) {
                        dealBean(class2);
                        return;
                    }
                    Object object = class2.getConstructor(new Class[] {}).newInstance(new Object[] {});
                    BeanDefinition beanDefinition = new BeanDefinition(class2,object);
                    beanPool.put(class2.getName(), beanDefinition);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.startScan();
    }
//——————————————————————————————————————————————————Don't have param-method inject
    private static void dealBean(Class<?> class2) {
        try {
            Object configObject = class2.getConstructor(new Class[] {}).newInstance(new Object[] {});
			Method[] methods = class2.getMethods();
            for(Method method:methods) {
                if(!method.isAnnotationPresent(Bean.class)) {
                    continue;
                }
                int paraCount = method.getParameterCount();
                Class<?> returnType = method.getReturnType();
                if (paraCount==0) {
                    Object result = method.invoke(configObject, new Object[]{});
                    BeanDefinition beanDefinition = new BeanDefinition(returnType,result);
                    beanPool.put(returnType.getName(), beanDefinition);
                }
                else {
                    
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String id) {
        BeanDefinition beanDefinition = beanPool.get(id);
        if(beanDefinition==null)return null;
        if(!beanDefinition.isInject()) {
            synchronized (beanPool) {
                if (!beanDefinition.isInject()) {
                    beanDefinition.setInject(true);
                    doInject(beanDefinition);
                }
            }
        }
        return (T) beanDefinition.getObject();
    }

    private void doInject(BeanDefinition bea) {
        Class<?> class2 = bea.getClass1();
        Object object = bea.getObject();
        injectByField(class2, object);
        injectByMethod(class2, object);
    }

    private void injectByField(Class<?> class2,Object object) {
        Field[] fields = class2.getDeclaredFields();
        for (Field field:fields) {
            if(!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }
            String fClass = field.getType().getName();
            Object instance = getBean(fClass);
            field.setAccessible(true);
            try {
                field.set(object,instance);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void injectByMethod(Class<?> class2,Object object) {
        Method[] methods = class2.getDeclaredMethods();
        for(Method method:methods) {
            if(!method.isAnnotationPresent(Autowired.class) || !method.getName().startsWith("set") ||
            method.getParameterCount()!=1 || method.getModifiers()!=Modifier.PUBLIC) {
                continue;
            }
            String paraClass = method.getParameterTypes()[0].getName();
            Object instance = getBean(paraClass);
            try {
                method.invoke(object, new Object[] {instance});
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addBean(String name,BeanDefinition beanDefinition) {
        beanPool.put(name, beanDefinition);
    }
}