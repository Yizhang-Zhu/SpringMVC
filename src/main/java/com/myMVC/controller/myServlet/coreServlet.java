package com.myMVC.controller.myServlet;

import com.myMVC.controller.HttpMapRequest.Model;
import com.myMVC.controller.HttpMapRequest.RequestValueOperator;
import com.myMVC.controller.HttpMapResponse.JsonResponse;
import com.myMVC.controller.IOC.mvc.methodFactory;
import com.myMVC.controller.IOC.bean.beanFactory;
import com.myMVC.controller.IOC.handleFactory.preHandleFactory;
import com.myMVC.controller.IOC.handleFactory.postHandleFactory;
import com.myMVC.controller.frameException.ExceptionCode;
import com.myMVC.controller.frameException.MVCException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


//核心Servlet
@MultipartConfig
public class coreServlet extends HttpServlet {
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        String requestPath = request.getServletPath();
        System.out.println("请求地址："+requestPath);
        //获取所有类
        Method method = methodFactory.getMethod(requestPath);
        if(method!=null){
            Class<?> item = method.getDeclaringClass();
            String select_name = item.getSimpleName();
            //获取类实例的对象
            Object object = null;
            try {
                object = beanFactory.getBean(select_name);
            } catch (MVCException e) {
                e.printStackTrace();
            }
            try {
                //前拦截器调用
                Method preHandleMethod = preHandleFactory.getMethod(requestPath,true);
                if (preHandleMethod!=null) {
                    String preHandleClassName = preHandleMethod.getDeclaringClass().getSimpleName();
                    Object preHandleClass = beanFactory.getBean(preHandleClassName);
                    preHandleMethod.invoke(preHandleClass, request, response);
                }
                //处理request，通过request获取参数并调用
                Model model = RequestValueOperator.getRequestValue(request,response);

                //调用方法

                Object return_json =null;
                return_json = method.invoke(object,model);


                //后拦截器调用
                Method postHandleMethod = postHandleFactory.getMethod(requestPath,true);
                if(postHandleMethod!=null) {
                    String postHandleClassName = postHandleMethod.getDeclaringClass().getSimpleName();
                    Object postHandleClass = beanFactory.getBean(postHandleClassName);
                    postHandleMethod.invoke(postHandleClass, request, response);
                }

                //将数据加载为json
                if(return_json!=null){
                    JsonResponse.addJsonResponse(return_json,response,"Gson");
                }

            }  catch (IllegalAccessException e) {
                //方法没有权限调用
                MVCException mvcException = new MVCException(ExceptionCode.MethodException);
                mvcException.printStackTrace();
            } catch (InvocationTargetException e){
                //方法内部错误
                MVCException mvcException = new MVCException(ExceptionCode.RunMethodException);
                mvcException.printStackTrace();
            }catch (MVCException e) {
                e.printStackTrace();
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            this.doPost(request,response);
    }
}
