package com.lzj.test;

import com.lzj.spring.context.ApplicationContext;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BeanFactoryTest.class,
        ApplicationContextTest.class
})
public class AllTest {
}
