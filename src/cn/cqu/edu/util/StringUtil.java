package cn.cqu.edu.util;

import java.net.URL;

public class StringUtil {
    public static String getRootPath(URL url) {
        return url.getFile();
    }

    public static String dotToSplash(String name) {
        return name.replaceAll("\\.", "/");
    }

    public static String trimExtension(String name) {
        int pos = name.indexOf(".");
        if(pos!=-1) return name.substring(0, pos);
        return name;
    }
}