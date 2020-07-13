package com.lzj.spring.beans.factory.support;

import com.lzj.spring.beans.BeanDefinition;
import com.lzj.spring.beans.PropertyValue;
import com.lzj.spring.beans.SimpleTypeConverter;
import com.lzj.spring.beans.factory.BeanCreationException;
import com.lzj.spring.beans.factory.BeanFactory;
import com.lzj.spring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
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
        // 实例化bean
        Object bean = instanceBean(bd);
        // 属性赋值
        populateBean(bean,bd);
        return bean;

    }

    private void populateBean(Object bean, BeanDefinition bd) {
       List<PropertyValue> pvs= bd.getPropertyValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        try{
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyValue pv : pvs){
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);

                for (PropertyDescriptor pd : pds) {
                    if(pd.getName().equals(propertyName)){
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }
            }
        }catch(Exception ex){
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", ex);
        }
    }

    private Object instanceBean(BeanDefinition bd) {
        if(bd.hasConstructorArgumentValues()){
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        }else{
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            String beanClassName = bd.getBeanClassName();
            try {
                Class<?> clz = cl.loadClass(beanClassName);
                return clz.newInstance();
            } catch (Exception e) {
                throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
            }
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
