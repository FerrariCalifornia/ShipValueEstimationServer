package com.cc.controller;

import com.cc.dao.AuthorityMapper;
import com.cc.pojo.Authority;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by cc on 2017/4/24.
 */
public class CrawlerTest {
    public static void main(String[] args){

        System.out.printf("a:aaaaasdasdasdasdasd");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
        AuthorityMapper authorityMapper = (AuthorityMapper) context.getBean("authorityMapper");



    }
}
