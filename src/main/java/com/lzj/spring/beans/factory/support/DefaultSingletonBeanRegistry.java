package com.lzj.spring.beans.factory.support;

import com.lzj.spring.beans.factory.config.SingletonBeanRegistry;
import com.lzj.spring.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String,Object> beanMap = new ConcurrentHashMap<String,Object>();
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        Assert.notNull(beanName, "'beanName' must not be null");
        Object bean = this.beanMap.get(beanName);
        if(null !=bean){
            // bean 对象已注册 不可重复注册
            throw new IllegalStateException("Could not register object [" + singletonObject +
                    "] under bean name '" + beanName + "': there is already object [" + bean + "] bound");
        }
        this.beanMap.put(beanName,singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.beanMap.get(beanName);
    }
}
