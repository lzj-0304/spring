package com.lzj.test;

import com.lzj.service.TestService;
import com.lzj.spring.context.ApplicationContext;
import com.lzj.spring.context.support.ClasspathXmlApplicationContext;
import org.junit.Test;


public class PropertyTest {


    @Test
    public void test03(){
        ApplicationContext ac=new ClasspathXmlApplicationContext("spring_02.xml");
        TestService testService = (TestService) ac.getBean("testService");
        testService.test();
    }

}
