package com.lzj.spring.context.support;


import com.lzj.spring.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {
    private  String id;
    private String beanClassName;



    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getBeanClassName() {
        return beanClassName;
    }
}
