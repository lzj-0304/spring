package com.lzj.test;

import com.lzj.service.TestService;
import com.lzj.spring.beans.factory.xml.XmlDefinitionReader;
import com.lzj.spring.context.support.DefaultBeanFactory;
import com.lzj.spring.core.io.ClassPathResource;
import com.lzj.spring.core.io.FileSystemResource;
import com.lzj.spring.core.io.Resource;
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
        Resource classPathResource = new ClassPathResource("spring.xml");
        reader.loadBeanDefinition(classPathResource);
        assertNotNull(factory.getBean("testService"));
        TestService testService = (TestService) factory.getBean("testService");
        testService.test();
    }

    @Test
    public void test03(){
        DefaultBeanFactory factory=new DefaultBeanFactory();
        XmlDefinitionReader reader=new XmlDefinitionReader(factory);
        Resource fileSystemResource = new FileSystemResource("C:\\java\\idea_lzj\\spring\\src\\test\\resources\\spring.xml");
        reader.loadBeanDefinition(fileSystemResource);
        assertNotNull(factory.getBean("testService"));
        TestService testService = (TestService) factory.getBean("testService");
        testService.test();
    }

}
