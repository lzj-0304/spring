package com.lzj.spring.beans.factory.support;

import com.lzj.spring.beans.BeanDefinition;
import com.lzj.spring.beans.factory.BeanCreationException;
import com.lzj.spring.beans.factory.BeanFactory;
import com.lzj.spring.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    public final Map<String, BeanDefinition> beanDefinitionMap=new HashMap<String,BeanDefinition>();

    @Override
    public Object getBean(String beanID) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanID);
        if (null == beanDefinition){
            return null;
        }

        if(beanDefinition.isSingleton()){
            Object bean =this.getSingleton(beanID);
            if(null == bean){
                bean= createBean(beanDefinition);
                this.registerSingleton(beanID,bean);
            }
            return bean;
        }
        return createBean(beanDefinition);
    }

    private Object createBean(BeanDefinition bd) {
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
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
