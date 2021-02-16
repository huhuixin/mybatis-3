package com.hhx.mybatis.parsing;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.PropertyParser;
import org.apache.ibatis.parsing.TokenHandler;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class PropertyParse {

    @Test
    public void test() throws IOException {
        Properties variables = new Properties();
        variables.load(Resources.getResourceAsStream("properties/mybatis.properties"));
        // username=hhx
        // password=123456
        log.info(PropertyParser.parse("username=${username}", variables));
        log.info(PropertyParser.parse("password=${password}", variables));
    }

    @Test
    public void testMyParser() throws IOException {
        Properties variables = new Properties();
        variables.load(Resources.getResourceAsStream("properties/mybatis.properties"));
        // username=hhx
        // password=123456
        TokenHandler handler = new MyHandle(variables);
        GenericTokenParser parser = new GenericTokenParser("#{{", "}", handler);
        log.info(parser.parse("username=#{{username}"));
        log.info(parser.parse("password=#{{password}"));
        log.info(parser.parse("password111=#{{password111}"));
    }

    @Test
    public void testMyParser2() throws IOException {
        log.info(parse("username=#{{username}","#{{", "}", "hhx"));
        log.info(parse("password=${password}","${", "}", "123456"));
    }

    // 这玩意只是取一下值, 可以添加一下扩展功能, 比如默认值啥的
    private class MyHandle implements TokenHandler {
        private final Properties variables;
        public MyHandle(Properties variables) {
            this.variables = variables;
        }
        public String handleToken(String key) {
            if (variables != null && variables.containsKey(key)) {
                return variables.getProperty(key);
            }
            //throw new RuntimeException("丢失配置!: " + key);
            log.warn("丢失配置, key: {}", key);
            return "你配置文件里都没有这个东西啊[" + key + "]";
        }
    }


    public static String parse(String text, String openToken, String closeToken, String value) {
        if (text != null && !text.isEmpty()) {
            int start = text.indexOf(openToken);
            if (start == -1) {
                return text;
            } else {
                char[] src = text.toCharArray();
                int offset = 0;
                StringBuilder builder = new StringBuilder();
                StringBuilder expression = null;

                do {
                    if (start > 0 && src[start - 1] == '\\') {
                        builder.append(src, offset, start - offset - 1).append(openToken);
                        offset = start + openToken.length();
                    } else {
                        if (expression == null) {
                            expression = new StringBuilder();
                        } else {
                            expression.setLength(0);
                        }

                        builder.append(src, offset, start - offset);
                        offset = start + openToken.length();

                        int end;
                        for(end = text.indexOf(closeToken, offset); end > -1; end = text.indexOf(closeToken, offset)) {
                            if (end <= offset || src[end - 1] != '\\') {
                                expression.append(src, offset, end - offset);
                                break;
                            }

                            expression.append(src, offset, end - offset - 1).append(closeToken);
                            offset = end + closeToken.length();
                        }

                        if (end == -1) {
                            builder.append(src, start, src.length - start);
                            offset = src.length;
                        } else {
                            builder.append(value);
                            offset = end + closeToken.length();
                        }
                    }

                    start = text.indexOf(openToken, offset);
                } while(start > -1);

                if (offset < src.length) {
                    builder.append(src, offset, src.length - offset);
                }

                return builder.toString();
            }
        } else {
            return "";
        }
    }
}
