package com.lzj.spring.beans.factory.config;


public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);

    public Object getSingleton(String beanName);

}
