package com.myMVC.controller.HttpMapResponse;

import com.google.gson.Gson;
import com.myMVC.controller.annotate.bean.Bean;
import com.myMVC.controller.frameException.ExceptionCode;
import com.myMVC.controller.frameException.MVCException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

//使用json发送数据

public class JsonResponse {
    /**
     * 外部调用，实现序列化json数据并转发
     *
     * @param json
     * @param response
     * @param packageName
     * @throws MVCException
     */
    public static void addJsonResponse(Object json, HttpServletResponse response, String packageName) throws MVCException {
        add_json_to_response(response, map2json(json, packageName));
    }

    /**
     * 将Object对象序列化，形成json
     *
     * @param json
     * @return
     */
    private static String map2json(Object json, String packageName) {
        String jsonStr = "";
        if (packageName.equals("Gson")) {
            Gson jsonObject = new Gson();
            jsonStr = jsonObject.toJson(json);
        }
        return jsonStr;
    }

    /**
     * 将json字符串通过response转发
     *
     * @param response
     * @param jsonStr
     * @throws MVCException
     */
    private static void add_json_to_response(HttpServletResponse response, String jsonStr) throws MVCException {
        try {
            Writer out = response.getWriter();
            out.write(jsonStr);
            out.flush();
            out.close();
        } catch (IOException e) {
            // 无法打开response输出流
            throw new MVCException(ExceptionCode.ResponseIOException);
        }
    }
}
