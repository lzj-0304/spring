package com.lzj.service;

import com.lzj.dao.TestDao;

public class TestService {

    private TestDao testDao;

    private String uname;

    private Integer age;

    private Boolean flag;




    public void setAge(Integer age) {
        this.age = age;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public void setTestDao(TestDao testDao) {
        this.testDao = testDao;
    }


    public void setUname(String uname) {
        this.uname = uname;
    }

    public void test(){
        System.out.println(uname+"--"+age+"--"+flag);
        testDao.test();
        System.out.println("TestService.test...");
    }



}
