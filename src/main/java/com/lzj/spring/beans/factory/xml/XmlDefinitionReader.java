package com.lzj.spring.beans.factory.xml;

import com.lzj.spring.beans.BeanDefinition;
import com.lzj.spring.beans.ConstructorArgument;
import com.lzj.spring.beans.PropertyValue;
import com.lzj.spring.beans.factory.BeanDefinitionStoreException;
import com.lzj.spring.beans.factory.support.BeanDefinitionRegistry;
import com.lzj.spring.beans.factory.support.GenericBeanDefinition;
import com.lzj.spring.config.RuntimeBeanReference;
import com.lzj.spring.config.TypedStringValue;
import com.lzj.spring.core.io.Resource;
import com.lzj.spring.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XmlDefinitionReader {
    
    private Log logger=LogFactory.getLog(XmlDefinitionReader.class);
    public static final String ID_ATTRIBUTE="id";
    public static final String CLASS_ATTRIBUTE="class";

    public static final String SCOPE_ATTRIBUTE = "scope";


    public static final String PROPERTY_ELEMENT = "property";

    public static final String REF_ATTRIBUTE = "ref";

    public static final String VALUE_ATTRIBUTE = "value";

    public static final String NAME_ATTRIBUTE = "name";

    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
    public static final String TYPE_ATTRIBUTE = "type";

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public XmlDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void loadBeanDefinition(Resource resource) {
        InputStream is =null;
        try {
            is=resource.getInputStream();
            SAXReader reader =new SAXReader();
            Document document = reader.read(is);
            Element element= document.getRootElement();
            Iterator<Element> elements= element.elementIterator();
            while (elements.hasNext()){
                element = elements.next();
                String beanID= element.attributeValue(ID_ATTRIBUTE);
                String beanClassName=element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition beanDefinition = (BeanDefinition) new GenericBeanDefinition(beanID,beanClassName);
                if(element.attributeValue(SCOPE_ATTRIBUTE) !=null){
                    beanDefinition.setScope(element.attributeValue(SCOPE_ATTRIBUTE));
                }


                // 解析constructor-arg标签
                parseConstructorArgElements(element,beanDefinition);
                // 解析property 标签
                parsePropertyElement(element,beanDefinition);
                beanDefinitionRegistry.registerBeanDefinition(beanID,beanDefinition);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(),e);
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseConstructorArgElements(Element element, BeanDefinition beanDefinition) {
        Iterator iter = element.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while(iter.hasNext()){
            Element ele = (Element)iter.next();
            parseConstructorArgElement(ele, beanDefinition);
        }
    }

    private void parseConstructorArgElement(Element ele, BeanDefinition beanDefinition) {
        String typeAttr = ele.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = ele.attributeValue(NAME_ATTRIBUTE);
        Object value = parsePropertyValue(ele, beanDefinition, null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
        if (StringUtils.hasLength(typeAttr)) {
            valueHolder.setType(typeAttr);
        }
        if (StringUtils.hasLength(nameAttr)) {
            valueHolder.setName(nameAttr);
        }

        beanDefinition.getConstructorArgument().addArgumentValue(valueHolder);
    }


    private void parsePropertyElement(Element element, BeanDefinition beanDefinition) {
        Iterator iter= element.elementIterator(PROPERTY_ELEMENT);
        while(iter.hasNext()){
            Element propElem = (Element)iter.next();
            String propertyName = propElem.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propertyName)) {
                logger.error("Tag 'property' must have a 'name' attribute");
                return;
            }
            Object val = parsePropertyValue(propElem, beanDefinition, propertyName);
            PropertyValue pv = new PropertyValue(propertyName, val);
            beanDefinition.getPropertyValues().add(pv);
        }
    }

    public Object parsePropertyValue(Element ele, BeanDefinition bd, String propertyName) {
        String elementName = (propertyName != null) ?
                "<property> element for property '" + propertyName + "'" :
                "<constructor-arg> element";
        boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE)!=null);
        boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) !=null);

        if (hasRefAttribute) {
            String refName = ele.attributeValue(REF_ATTRIBUTE);
            if (!StringUtils.hasText(refName)) {
                logger.error(elementName + " contains empty 'ref' attribute");
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        }else if (hasValueAttribute) {
            TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
            return valueHolder;
        }
        else {
            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }
}
