package cn.cqu.edu.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PackageScanner {
    private String basePackage;
    private ClassLoader cl;

    public PackageScanner(String bp) {
        basePackage = bp;
        cl = getClass().getClassLoader();
    }

    public PackageScanner(String bp,ClassLoader clr) {
        basePackage = bp;
        cl = clr;
    }

    public void startScan() throws IOException, ClassNotFoundException {
        doScan(basePackage, new ArrayList<String>());
    }

    private List<String> doScan(String bp,List<String> nameList) throws IOException, ClassNotFoundException {
        String splashPath = StringUtil.dotToSplash(bp);

        URL url = cl.getResource(splashPath);
        String filePath = StringUtil.getRootPath(url);

        List<String> names;
        File file = new File(filePath);
        String[] fileNames = file.list();
        if(fileNames==null)names = null;
        else names = Arrays.asList(fileNames);

        for(String name:names) {
            if(isClassFile(name)) {
                nameList.add(toFullyQualifiedName(name, bp));
                Class<?> class2 = Class.forName(toFullyQualifiedName(name, bp));
                dealClass(class2);
            }
            else doScan(bp+"."+name,nameList);
        }

        return nameList;
    }

    public void dealClass(Class<?> class2) {}

    private boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    private String toFullyQualifiedName(String name,String basePackage) {
        return basePackage+"."+StringUtil.trimExtension(name);
    }
}