package com.lzj.spring.context.support;

import com.lzj.spring.beans.factory.support.DefaultBeanFactory;
import com.lzj.spring.beans.factory.xml.XmlDefinitionReader;
import com.lzj.spring.context.ApplicationContext;
import com.lzj.spring.core.io.Resource;

public abstract class AbstractApplicationContext implements ApplicationContext {


    private DefaultBeanFactory factory=null;



    public AbstractApplicationContext(String configFile) {
        factory=new DefaultBeanFactory();
        XmlDefinitionReader reader=new XmlDefinitionReader(factory);
        reader.loadBeanDefinition(this.getResourceByPath(configFile));
    }

    @Override
    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }


    public abstract Resource getResourceByPath(String configFile);



}
