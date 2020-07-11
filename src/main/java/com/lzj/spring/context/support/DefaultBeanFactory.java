package com.lzj.spring.context.support;

import com.lzj.spring.beans.BeanDefinition;
import com.lzj.spring.beans.factory.BeanCreationException;
import com.lzj.spring.beans.factory.BeanFactory;
import com.lzj.spring.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

public class DefaultBeanFactory implements BeanFactory,BeanDefinitionRegistry {

    public final Map<String, BeanDefinition> beanDefinitionMap=new HashMap<String,BeanDefinition>();

    @Override
    public Object getBean(String beanID) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanID);
        if (null == beanDefinition){
            return null;
        }
        String beanClassName= beanDefinition.getBeanClassName();
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        try {
            Class<?> clazz =classLoader.loadClass(beanClassName);
            return  clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
        }
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    @Override
    public void registerBeanDefinition(String beanID, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanID,beanDefinition);
    }
}
