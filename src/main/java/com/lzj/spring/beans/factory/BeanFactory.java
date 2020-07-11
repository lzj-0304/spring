package com.lzj.spring.beans.factory;

public interface BeanFactory {
    public Object getBean(String beanID);

    public BeanDefinition getBeanDefinition(String beanID);
}
