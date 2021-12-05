package com.myMVC.controller.frameException;

// 异常信息编号

public enum ExceptionCode {
    RunAddBeansException("MVC001","AddBeans注解方法体内部存在未捕获的异常"),
    AddBeansException("MVC002","AddBeans注解方法执行异常，无法访问指定的类，字段，方法或构造函数的定义"),
    NotFindMybatisMapperClassException("MVC003","没有找到MybatisMapper注解标注的属性的类"),
    AddMybatisMapperException("MVC004","MybatisMapper注解标注的属性不能被注入对象"),
    AddSqlSessionException("MVC005","无法注入SqlSession"),
    NotFindAutowiredClassException("MVC006","找不到Autowired变量类型的Class对象"),
    AutowiredBeansException("MVC007","没有权限对属性注入对象"),
    MethodNotFindException("MVC008","找不到默认无参数构造函数"),
    ClassInstanceException("MVC009","无法实例化该类"),
    InvocationMethodException("MVC010","构造函数内部异常"),
    MybaitisConfigNotFind("MVC011","找不到mybatis配置文件"),
    AddModelFieldsException("MVC012","Model成员变量无法访问"),
    ResponseIOException("MVC013","无法打开response输出流"),
    CLassNotFindExceptions("MVC014","无法找到包中的类"),
    RunFilterMethodException("MVC015","过滤器方法内部异常"),
    FilterMethodException("MVC016","过滤器方法无法调用"),
    RunMethodException("MVC017","方法内部异常"),
    MethodException("MVC018","方法无法调用");

    private String code;
    private String message;

    /**
     * 构造函数
     * @param code
     * @param message
     */
    private ExceptionCode(String code, String message) {
        this.code=code;
        this.message=message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "【" + this.code + "】" + this.message;
    }
}
