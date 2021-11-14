package cn.cqu.edu.ioc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.cqu.edu.annotations.Component;
import cn.cqu.edu.util.PackageScanner;

public class BeanFactory {
    private static final Map<String, BeanDefinition> beanPool = new HashMap<>();

    public static void scanPackage(String basePackage) throws ClassNotFoundException, IOException {
        new PackageScanner(basePackage) {
            @Override
            public void dealClass(Class<?> class2) {
                if (class2.isPrimitive() || class2.isArray() || class2.isInterface() ||
                class2.isEnum() || class2==String.class || class2.isAnnotation() ||
                !class2.isAnnotationPresent(Component.class)) return;
                try {
                    Object object = class2.getConstructor(new Class[] {}).newInstance(new Object[] {});
                    BeanDefinition beanDefinition = new BeanDefinition(class2,object);
                    beanPool.put(class2.getName(), beanDefinition);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.startScan();
    }

    public <T> T getBean(String id) {
        BeanDefinition beanDefinition = beanPool.get(id);
        if(beanDefinition==null)return null;
        return (T) beanDefinition.getObject();
    }

    public void addBean(String name,BeanDefinition beanDefinition) {
        beanPool.put(name, beanDefinition);
    }
}