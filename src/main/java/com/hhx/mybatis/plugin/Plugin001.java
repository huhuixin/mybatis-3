package com.hhx.mybatis.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

@Slf4j
@Intercepts({@Signature(type= Executor.class, method = "update", args = {MappedStatement.class,Object.class})})
public class Plugin001 implements Interceptor {

    private String prop;

    public Object intercept(Invocation invocation) throws Throwable {
        log.info("intercept method: {}", invocation.getMethod());
        log.info("intercept args: {}", invocation.getArgs());
        log.info("intercept prop: {}", prop);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return null;
    }

    public void setProperties(Properties properties) {
        prop = properties.getProperty("prop", "defaultProp");
        log.info("setProperties prop [{}]", prop);
    }
}
