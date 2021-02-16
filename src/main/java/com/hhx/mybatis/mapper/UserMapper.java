package com.hhx.mybatis.mapper;

import com.hhx.mybatis.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@CacheNamespaceRef(UserMapper.class)
public interface UserMapper {

    User selectById(int id);

    @Select("select * from user where id = #{id}")
    User selectById2(int id);

    int insert(User user);

    @Update("update user set name = #{name}, age = #{age} where id = #{id}")
    int updateById(User user);

    // 使用 ${} 不会被转义
    // select * from user where name = ?
    // 使用 #{column} 会被处理成  select * from user where ? = ?
    @Select("select * from user where ${column} = #{value}")
    User selectByColumn(@Param("column") String column, @Param("value") String value);

    User selectByLikeColumn(@Param("column") String column, @Param("value") String value);

    @SelectProvider(type = UserSqlBuilder.class, method = "buildGetUsersByName")
    List<User> getUsersByName(String name);

    class UserSqlBuilder {
        public static String buildGetUsersByName(final String name) {
            return new SQL(){{
                SELECT("*");
                FROM("user");
                if (name != null) {
                    WHERE("name like #{value} || '%'");
                }
                ORDER_BY("id");
            }}.toString();
        }
    }
}
