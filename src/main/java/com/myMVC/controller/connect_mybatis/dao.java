package com.myMVC.controller.connect_mybatis;

import com.myMVC.controller.annotate.bean.Autowired;
import com.myMVC.controller.annotate.bean.Bean;
import com.myMVC.controller.annotate.connect_mybatis.MybatisMapper;
import com.myMVC.controller.annotate.connect_mybatis.SqlSessions;
import com.myMVC.controller.connect_mybatis.mybatisSession;
import org.apache.ibatis.session.SqlSession;

@Bean
public class dao {
    @SqlSessions
    SqlSession session;
    @MybatisMapper
    mapper user2Mapper;
    // 使用接口方式
    public entryy findUserById(int id){

        entryy rint = user2Mapper.findUserById(id);
        //使用mapper方法后必须使用commit
        session.commit();
        return rint;
    }
    public void insert(entryy entry){
        user2Mapper.insertInTo(entry);
        session.commit();
    }
}
