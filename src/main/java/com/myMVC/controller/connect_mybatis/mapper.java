package com.myMVC.controller.connect_mybatis;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

// 这边接口名要和xml映射的文件名一致
public interface mapper {

    entryy findUserById(int id);

    void insertInTo(entryy entry);
}
