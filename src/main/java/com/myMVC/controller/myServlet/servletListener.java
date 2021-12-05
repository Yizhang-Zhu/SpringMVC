package com.myMVC.controller.myServlet;

import com.myMVC.controller.IOC.bean.add_beans;
import com.myMVC.controller.frameException.MVCException;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//监听器，实现注入
public class servletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //初始化框架
        try {
            add_beans.init();
            System.out.println("初始化结束");
        } catch (MVCException e) {
            //输出框架异常
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


}
