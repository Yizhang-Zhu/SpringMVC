package com.myMVC.controller.MVCutils;

public class classNameOperator {
    /**
     * 将 class或interface A.B.C.D 转化为A.B.C.D
     * @param type
     * @return
     */
    public static String getClassName(String type){
        StringBuffer stringBuffer = new StringBuffer(type);
        while(stringBuffer.charAt(0)!=' '){
            stringBuffer.deleteCharAt(0);
        }
        //去空格
        stringBuffer.deleteCharAt(0);
        return stringBuffer.toString();
    }
}
