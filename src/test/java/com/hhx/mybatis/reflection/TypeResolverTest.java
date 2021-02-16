package com.hhx.mybatis.reflection;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

@Slf4j
public class TypeResolverTest {

    @Test
    public void test() throws NoSuchFieldException {
        // Map<? extends T, ? super Type01> ss4;
        Type ss1 = TypeParameterResolver.resolveFieldType(Type03.class.getDeclaredField("ss4"), Type03.class);
        // org.apache.ibatis.reflection.TypeParameterResolver$ParameterizedTypeImpl
        log.info("ss4 {}", ss1.getTypeName());
        Type ss1ActualTypeArgument1 = ((ParameterizedType) ss1).getActualTypeArguments()[1];
        //org.apache.ibatis.reflection.TypeParameterResolver$WildcardTypeImpl
        log.info("ss4 {}", ss1ActualTypeArgument1.getTypeName());
        WildcardType ss1ActualTypeArgument11 = (WildcardType) ss1ActualTypeArgument1;
        log.info("ss4 {}", ss1ActualTypeArgument11.getUpperBounds());
        log.info("ss4 {}", ss1ActualTypeArgument11.getLowerBounds());
    }


}
