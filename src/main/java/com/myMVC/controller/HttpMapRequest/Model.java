package com.myMVC.controller.HttpMapRequest;

import com.myMVC.controller.annotate.bean.Bean;
import com.myMVC.controller.annotate.bean.Prototype;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 封装请求，返回给Controller
@Bean
@Prototype
public class Model{
    private HttpServletRequest request;
    private HttpServletResponse response;

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
    public String getParameter(String param_Name){
        return request.getParameter(param_Name);
    }
    public String[] getParameterValues(String param_Name){
        return request.getParameterValues(param_Name);
    }
}
