package com.lzj.spring.beans.factory;

import com.lzj.spring.beans.BeanDefinition;

public interface BeanFactory {
    public Object getBean(String beanID);

}
