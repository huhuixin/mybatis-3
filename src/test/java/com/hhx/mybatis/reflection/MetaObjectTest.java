package com.hhx.mybatis.reflection;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.Test;

import java.util.*;

@Slf4j
public class MetaObjectTest {
    @Test
    public void test() {
        User user = new User("hhx");
        user.girl = new User("小仙女");
        user.girls = new User[]{new User("小富婆"), new User("小姐姐")};
        user.map = new HashMap<>();
        user.map.put("key1", "value1");
        user.names = Arrays.asList("123");

        org.apache.ibatis.reflection.MetaObject metaObject = SystemMetaObject.forObject(user);
        // metaObject 会将不同的类型解析用不同的 objectWrapper 封装

        // BeanWrapper
        log.info("getObjectWrapper {}", metaObject.getObjectWrapper().getClass());

        log.info("self.name hasGetter {}", metaObject.hasGetter("self.name"));
        log.info("self.name getValue {}", metaObject.getValue("self.name"));
        // MapWrapper
        log.info("metaObjectForProperty map {}", metaObject.metaObjectForProperty("map").getObjectWrapper().getClass());
        log.info("map.key1 getValue {}", metaObject.getValue("map.key1"));
        log.info("self.name getValue {}", metaObject.getValue("girl.self.name"));
        log.info("girl.map.key1 getValue {}", metaObject.getValue("girl.map.key1"));

        // BeanWrapper
        log.info("metaObjectForProperty girls {}", metaObject.metaObjectForProperty("girls").getObjectWrapper().getClass());
        // CollectionWrapper
        log.info("metaObjectForProperty names {}", metaObject.metaObjectForProperty("names").getObjectWrapper().getClass());

        log.info("girls[0].name getValue {}", metaObject.getValue("girls[0].name"));
        log.info("self.girls[1].self.name getValue {}", metaObject.getValue("self.girls[1].self.name"));

        metaObject.setValue("girls[0].name", "小可爱");
        log.info("girls[0].name getValue {}", metaObject.getValue("girls[0].name"));

        metaObject.setValue("girls[0].names", Arrays.asList("是小可爱啊"));
        log.info("girls[0].names getValue {}", metaObject.getValue("girls[0].names[0]"));

        // 必须得有的替换才行
        metaObject.setValue("girls[1].names", Arrays.asList(""));
        // 如果list未初始化, 会报错, 即使初始化, 如果操作的位置未被初始化, 也会数组越界
        metaObject.setValue("girls[1].names[0]", "是小姐姐啊");
        log.info("girls[1].names getValue {}", metaObject.getValue("girls[1].names[0]"));
    }
}

class User {
    String name;
    User self;
    User girl;
    User[] girls;
    List<String> names = new ArrayList<>(1);
    Map<String, String> map;
    public User(String name) {
        this.name = name;
        this.self = this;
    }
}
