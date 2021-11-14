package cn.cqu.edu.ioc;

public class BeanDefinition {
    private Class<?> class1;
    private Object object;
    private volatile boolean inject;

    public BeanDefinition() {}

    public BeanDefinition(Class<?> class1,Object object) {
        this.class1 = class1;
        this.object = object;
        inject = false;
    }

    public Class<?> getClass1() {
        return class1;
    }

    public void setClass1(Class<?> class1) {
        this.class1 = class1;
    }

    public Object getObject() {
        return object;
    }

    public boolean isInject() {
        return inject;
    }

    public void setInject(boolean inject) {
        this.inject = inject;
    }
}