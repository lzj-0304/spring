package com.lzj.test;

import com.lzj.service.TestService;
import com.lzj.service.TestService2;
import com.lzj.spring.context.ApplicationContext;
import com.lzj.spring.context.support.ClasspathXmlApplicationContext;
import org.junit.Test;


public class ConstrcutorArgTest {


    @Test
    public void test03(){
        ApplicationContext ac=new ClasspathXmlApplicationContext("spring_03.xml");
        TestService2 testService2 = (TestService2) ac.getBean("testService2");
        testService2.test();
    }

}
