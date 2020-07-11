package com.lzj.spring.context.support;

import com.lzj.spring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {
    public BeanDefinition getBeanDefinition(String beanID);

    public void registerBeanDefinition(String beanID,BeanDefinition beanDefinition);
}
