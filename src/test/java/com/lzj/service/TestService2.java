package com.lzj.service;

import com.lzj.dao.TestDao;

public class TestService2 {

    private TestDao testDao;

    private String uname;

    private Integer age;

    public void setAge(Integer age) {
        this.age = age;
    }

    public TestService2(TestDao testDao, String uname) {
        this.testDao = testDao;
        this.uname = uname;
    }



    public void test(){
        System.out.println(uname+","+age);
        testDao.test();
        System.out.println("TestService.test...");
    }



}
