package com.lzj.spring.context.support;

import com.lzj.spring.beans.factory.BeanDefinition;
import com.lzj.spring.beans.factory.BeanFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DefaultBeanFactory implements BeanFactory {
    public static final String ID_ATTRIBUTE="id";
    public static final String CLASS_ATTRIBUTE="class";
    public final Map<String,BeanDefinition> beanDefinitionMap=new HashMap<String,BeanDefinition>();
    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }
    private void loadBeanDefinition(String configFile) {
        InputStream is=null;
        is = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
        try {
            SAXReader reader =new SAXReader();
            Document document = reader.read(is);
            Element element= document.getRootElement();
            Iterator<Element> elements= element.elementIterator();
            while (elements.hasNext()){
                element = elements.next();
                String id= element.attributeValue(ID_ATTRIBUTE);
                String beanClassName=element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition beanDefinition = new GenericBeanDefinition(id,beanClassName);
                beanDefinitionMap.put(id,beanDefinition);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String beanID) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanID);
        if (null == beanDefinition){
            return null;
        }
        String beanClassName= beanDefinition.getBeanClassName();
        ClassLoader classLoader =Thread.currentThread().getContextClassLoader();
        try {
            Class<?> clazz =classLoader.loadClass(beanClassName);
            return  clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return beanDefinitionMap.get(beanID);
    }
}
