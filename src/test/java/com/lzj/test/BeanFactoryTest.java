package com.lzj.test;

import com.lzj.service.TestService;
import com.lzj.spring.beans.factory.xml.XmlDefinitionReader;
import com.lzj.spring.context.support.DefaultBeanFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BeanFactoryTest {

   /* @Test
    public void test01(){
        BeanFactory factory=new DefaultBeanFactory("spring.xml");
        //System.out.println(factory.getBeanDefinition("testService").getBeanClassName());
        BeanDefinition beanDefinition = factory.getBeanDefinition("testService");
        assertEquals("com.lzj.service.TestService",beanDefinition.getBeanClassName());
        assertNotNull(factory.getBean("testService"));
    }*/

    @Test
    public void test02(){
        DefaultBeanFactory factory=new DefaultBeanFactory();
        XmlDefinitionReader reader=new XmlDefinitionReader(factory);
        reader.loadBeanDefinition("spring.xml");
        assertNotNull(factory.getBean("testService"));
        TestService testService = (TestService) factory.getBean("testService");
        testService.test();
    }

}
