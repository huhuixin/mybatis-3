package com.hhx.mybatis.reflection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class TypeTest {

    /**
     * @see Type
     */
    @Test
    public void test() throws NoSuchFieldException {
        // Type  是所有类型的父接口, 有一个实现类 Class
        Type type = Type01.class;
        log.info("typeName Type01: {}", type.getTypeName());
        // 和四个子接口
        // ParameterizedType 代表参数化类型
        testParameterizedType("list");
        testParameterizedType("list2");
        testParameterizedType("map");
        testParameterizedType("map2");
        testParameterizedType("entry");

        // TypeVariable 类型变量
        testTypeVariable();

        // GenericArrayType  数组类型带泛型
        log.info("Type03.body is GenericArrayType {}", Type03.class.getDeclaredField("body").getGenericType() instanceof GenericArrayType);
        log.info("Type03.bodies is GenericArrayType {}", Type03.class.getDeclaredField("bodies").getGenericType() instanceof GenericArrayType);
        log.info("Type03.bodies getGenericComponentType {}", ((GenericArrayType) Type03.class.getDeclaredField("bodies").getGenericType()).getGenericComponentType());

        // WildcardType 通配符泛型
        // List<S> ss1;
        // List<? extends S> ss2;
        // List<? extends Type01> ss3;
        log.info("Type03.ss1 is WildcardType {}", ((ParameterizedType) Type03.class.getDeclaredField("ss1").getGenericType()).getActualTypeArguments()[0] instanceof WildcardType);
        log.info("Type03.ss2 is WildcardType {}", ((ParameterizedType) Type03.class.getDeclaredField("ss2").getGenericType()).getActualTypeArguments()[0] instanceof WildcardType);
        log.info("Type03.ss3 is WildcardType {}", ((ParameterizedType) Type03.class.getDeclaredField("ss3").getGenericType()).getActualTypeArguments()[0] instanceof WildcardType);
        WildcardType ss3WildcardType =  (WildcardType)((ParameterizedType) Type03.class.getDeclaredField("ss3").getGenericType()).getActualTypeArguments()[0];
        log.info("Type03.ss3 getTypeName {}",  ss3WildcardType.getTypeName());
        log.info("Type03.ss3 getLowerBounds {}",  ss3WildcardType.getLowerBounds());
        log.info("Type03.ss3 getUpperBounds {}",  ss3WildcardType.getUpperBounds());

        // Map<? extends T, ? super Type01> ss4;
        Type[] ss4WildcardTypes =  ((ParameterizedType) Type03.class.getDeclaredField("ss4").getGenericType()).getActualTypeArguments();
        WildcardType wildcardType1 = (WildcardType) ss4WildcardTypes[0];
        log.info("Type03.ss4 WildcardType1 getTypeName {}",  wildcardType1.getTypeName());
        log.info("Type03.ss4 WildcardType1 getLowerBounds {}",  wildcardType1.getLowerBounds());
        log.info("Type03.ss4 WildcardType1 getUpperBounds {}",  wildcardType1.getUpperBounds());
        WildcardType wildcardType2 = (WildcardType) ss4WildcardTypes[1];
        log.info("Type03.ss4 WildcardType2 getTypeName {}",  wildcardType2.getTypeName());
        log.info("Type03.ss4 WildcardType2 getLowerBounds {}",  wildcardType2.getLowerBounds());
        log.info("Type03.ss4 WildcardType2 getUpperBounds {}",  wildcardType2.getUpperBounds());
    }

    private void testParameterizedType(String filedName) throws NoSuchFieldException {
        Type genericType = Type02.class.getDeclaredField(filedName).getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            log.info("Type02.{} TypeName {}", filedName, parameterizedType.getTypeName());
            log.info("Type02.{} ActualTypeArguments {}", filedName, Stream.of(parameterizedType.getActualTypeArguments())
                    .map(Type::getTypeName).collect(Collectors.joining(",")));
            log.info("Type02.{} OwnerType {}", filedName, Optional.ofNullable(parameterizedType.getOwnerType())
                    .map(Type::getTypeName).orElse("null"));
            log.info("Type02.{} RawType {}", filedName, parameterizedType.getRawType().getTypeName());
        } else {
            log.info("Type02.{} not ParameterizedType", filedName);
        }
    }

    private void testTypeVariable () {
        TypeVariable<Class<Type03>>[] typeParameters = Type03.class.getTypeParameters();
        TypeVariable<Class<Type03>> typeVariable1 = typeParameters[0];
        TypeVariable<Class<Type03>> typeVariable2 = typeParameters[1];
        log.info("Type03 TypeVariable getBounds {}", typeVariable1.getBounds());
        log.info("Type03 TypeVariable getBounds {}", typeVariable2.getBounds());
        log.info("Type03 TypeVariable getGenericDeclaration {}", typeVariable1.getGenericDeclaration());
        log.info("Type03 TypeVariable getGenericDeclaration {}", typeVariable2.getGenericDeclaration());
        log.info("Type03 TypeVariable getName {}", typeVariable1.getName());
        log.info("Type03 TypeVariable getName {}", typeVariable2.getName());
    }
}

class Type01 {

}

class Type02 {
    List list;
    List<String> list2;
    Map<String, Integer> map;
    Map<String, Map<Object, String>> map2;
    Map.Entry<String, Integer> entry;
}

class Type03<T extends Type01, S> {
    T body;
    S status;
    T[] bodies;
    List<S> ss1;
    List<? extends S> ss2;
    List<? extends Type01> ss3;
    Map<? extends T, ? super Type01> ss4;
}