package com.lzj.test;

import com.lzj.service.TestService;
import com.lzj.spring.context.ApplicationContext;
import com.lzj.spring.context.support.ClasspathXmlApplicationContext;
import com.lzj.spring.context.support.FileSystemApplicationContext;
import org.junit.Test;

public class ApplicationContextTest {

    @Test
    public void test01(){
        ApplicationContext ac=new ClasspathXmlApplicationContext("spring.xml");
        TestService testService= (TestService) ac.getBean("testService");
        testService.test();
    }

    @Test
    public void test02(){
        ApplicationContext ac=new FileSystemApplicationContext("C:\\java\\idea_lzj\\spring\\src\\test\\resources\\spring.xml");
        TestService testService= (TestService) ac.getBean("testService");
        testService.test();
    }
}
