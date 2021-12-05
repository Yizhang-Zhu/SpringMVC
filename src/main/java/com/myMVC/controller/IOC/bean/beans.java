package com.myMVC.controller.IOC.bean;


public class beans {
    // id，唯一标识符，主键
    String id;
    Object object = null;
    Class<?> object_class;
    //是否为原型模式，默认false（单例模式）
    Boolean prototype;

    public Class<?> getObject_class() {
        return object_class;
    }

    public Object getObject() {
        return object;
    }

    public String getId() {
        return id;
    }

    public Boolean getPrototype() {
        return prototype;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setObject_class(Class<?> object_class) {
        this.object_class = object_class;
    }

    public void setPrototype(Boolean prototype) {
        this.prototype = prototype;
    }
}
