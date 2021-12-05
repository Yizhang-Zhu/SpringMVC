package com.myMVC.controller.frameException;

public class MVCException extends Exception{
    //根据ExceptionCode的异常码生成Exception

    /**
     * 构造函数
     * @param obj
     */
    public MVCException(Object obj){
        super(obj.toString());
    }

    /**
     * 无参数构造函数
     */
    public MVCException(){
        super();
    }
}
