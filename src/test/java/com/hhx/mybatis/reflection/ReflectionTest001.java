package com.hhx.mybatis.reflection;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@Slf4j
public class ReflectionTest001 {

    @Test
    public void test() throws InvocationTargetException, IllegalAccessException {
        Reflector reflector = new Reflector(ReClass001.class);
        log.info("reflector: {}", reflector.findPropertyName("name"));
        log.info("reflector: {}", reflector.getDefaultConstructor());
        log.info("reflector: {}", Arrays.asList(reflector.getGetablePropertyNames()));
        log.info("reflector: {}", Arrays.asList(reflector.getSetablePropertyNames()));
        log.info("reflector: {}", reflector.getType());
        log.info("reflector: {}", reflector.getGetterType("name"));
        Invoker nameGetter = reflector.getGetInvoker("name");
        ReClass001 reClass001 = new ReClass001("001");
        log.info("reflector: {}", nameGetter.invoke(reClass001, null));
        reflector.getSetInvoker("name").invoke(reClass001, new Object[]{"002"});
        log.info("reflector: {}", reClass001.getName());
        log.info("reflector: {}", reflector.findPropertyName("name"));

    }

    public void testFactory() {
        ReflectorFactory factory = new DefaultReflectorFactory();
        factory.setClassCacheEnabled(true);
        Reflector forClass = factory.findForClass(ReClass001.class);
    }
}

