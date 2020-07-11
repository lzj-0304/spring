package com.lzj.service;

import com.lzj.spring.beans.factory.BeanDefinition;
import com.lzj.spring.beans.factory.BeanFactory;
import com.lzj.spring.context.support.DefaultBeanFactory;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestService {

    @Test
    public void test01(){
        BeanFactory factory=new DefaultBeanFactory("spring.xml");
        //System.out.println(factory.getBeanDefinition("testService").getBeanClassName());
        BeanDefinition beanDefinition = factory.getBeanDefinition("testService");
        assertEquals("com.lzj.service.TestService",beanDefinition.getBeanClassName());
        assertNotNull(factory.getBean("testService"));
    }
}
