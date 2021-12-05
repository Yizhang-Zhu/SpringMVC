package com.myMVC.controller.MVCutils;

public class get_url_root {
    /**
     * 获取上一级的方法路径
     * @param url
     * @return
     */
    public static String get_url_root_faction(String url){
        StringBuffer stringBuffer = new StringBuffer(url);
        stringBuffer.reverse();
        while(stringBuffer.length()>0){
            if(stringBuffer.charAt(0)!='/' && stringBuffer.charAt(0)!='\\'){
                stringBuffer.deleteCharAt(0);
            }else{
                break;
            }
        }
        if(stringBuffer.length()>0){
            stringBuffer.deleteCharAt(0);
        }
        stringBuffer.reverse();
        return stringBuffer.toString();
    }
}
