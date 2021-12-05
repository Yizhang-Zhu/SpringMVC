package com.myMVC.controller.myServlet;

import com.myMVC.controller.IOC.filter.afterFilterFactory;
import com.myMVC.controller.IOC.filter.beforeFilterFactory;
import com.myMVC.controller.IOC.bean.beanFactory;
import com.myMVC.controller.frameException.ExceptionCode;
import com.myMVC.controller.frameException.MVCException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class coreFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //request过滤器

        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //按照过滤器url找出method
        Method method = beforeFilterFactory.getMethod(request.getServletPath(),true);
        if(method!=null){
            Object obj1 = null;
            try {
                obj1 = beanFactory.getBean(method.getDeclaringClass().getSimpleName());
            } catch (MVCException e) {
                e.printStackTrace();
            }
            //执行方法
            try {
                method.invoke(obj1,req,resp);
            } catch (IllegalAccessException e) {
                //方法没有权限调用
                MVCException mvcException = new MVCException(ExceptionCode.FilterMethodException);
                mvcException.printStackTrace();
            } catch (InvocationTargetException e){
                //方法内部错误
                MVCException mvcException = new MVCException(ExceptionCode.RunFilterMethodException);
                mvcException.printStackTrace();
            }
        }

        chain.doFilter(req, resp);

        //response过滤器
        //按照过滤器url找出method
        Method method_after = afterFilterFactory.getMethod(request.getServletPath(),true);
        if(method_after!=null){
            Object obj2 = null;
            try {
                obj2 = beanFactory.getBean(method_after.getDeclaringClass().getSimpleName());
            } catch (MVCException e) {
                e.printStackTrace();
            }
            //执行方法
            try {
                method_after.invoke(obj2,req,resp);
            } catch (IllegalAccessException e) {
                //方法没有权限调用
                MVCException mvcException = new MVCException(ExceptionCode.FilterMethodException);
                mvcException.printStackTrace();
            } catch (InvocationTargetException e){
                //方法内部错误
                MVCException mvcException = new MVCException(ExceptionCode.RunFilterMethodException);
                mvcException.printStackTrace();
            }
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
