package com.hhx.mybatis.reflection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReClass001 {
    private static String CONSTANT = "OK";
    private String name;

    public ReClass001() {
    }

    public ReClass001(String name) {
        this.name = name;
    }

    public String getName() {
        log.info("ReClass001 getName {}", name);
        return name;
    }

    public void setName(String name) {
        log.info("ReClass001 setName {}", name);
        this.name = name;
    }
}
