package com.lzj.spring.beans.factory.support;

import com.lzj.spring.beans.factory.BeanFactory;
import com.lzj.spring.config.RuntimeBeanReference;
import com.lzj.spring.config.TypedStringValue;
import org.dom4j.bean.BeanMetaData;

public class BeanDefinitionValueResolver {
    private final BeanFactory beanFactory;
    public BeanDefinitionValueResolver(
            BeanFactory beanFactory) {

        this.beanFactory = beanFactory;
    }

    public Object resolveValueIfNecessary(Object value) {

        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String refName = ref.getBeanName();
            Object bean = this.beanFactory.getBean(refName);
            return bean;

        }else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else{
            throw new RuntimeException("the value " + value +" has not implemented");
        }
    }
}
