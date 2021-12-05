package com.myMVC.controller.webTest;


import com.myMVC.controller.HttpMapRequest.Model;
import com.myMVC.controller.annotate.mvc.Controller;
import com.myMVC.controller.annotate.mvc.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * MVC测试，返回index.jsp，实现上传文件功能
 */

@Controller
@RequestMapping("/uploadFile")
public class fileUpload {

    @RequestMapping("/index")
    public void getIndex(Model model){
        System.out.println(1);
        try {
            model.getResponse().sendRedirect("localhost:8080\\index.jsp");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/up")
    public void upload(Model model){
        System.out.println("______________________________________________________________-");
        try {
            HttpServletRequest request = model.getRequest();
            Part part= request.getPart("fileUP");
            String fileType=request.getContentType();
            String fileHeader=part.getHeader("content-disposition");
            long size=part.getSize();
            System.out.println(part);
            System.out.println(fileType);
            System.out.println(fileHeader);
            System.out.println(size);
            String filename=fileHeader.substring(fileHeader.indexOf("filename=")+10, fileHeader.lastIndexOf("\""));
            // 写入文件进入服务端
            part.write("E:\\aaa\\"+filename);
            // 交互信息反馈
            model.getResponse().getWriter().print(filename + " upload success");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
