package com.hhx.mybatis;


import com.hhx.mybatis.entity.User;
import com.hhx.mybatis.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@Slf4j
public class Test001 {

    private SqlSession sqlSession;

    @Before
    public void init() throws IOException, SQLException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis001.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
        createTable_user();
    }


    @Test
    public void test001() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        log.info("insert result {}", userMapper.insert(new User(666, "胡汇鑫", 18)));
        log.info("selectById result {}", userMapper.selectById(666));
        log.info("second selectById result {}", userMapper.selectById(666));
        log.info("selectById result {}", userMapper.selectById2(666));
        log.info("selectByColumn result {}", userMapper.selectByColumn("name", "胡汇鑫"));
        log.info("selectByLikeColumn result {}", userMapper.selectByLikeColumn("name", "胡"));
        log.info("insert result {}", userMapper.insert(new User(667, "胡汇鑫的方可心", 16)));
        log.info("getUsersByName result {}", userMapper.getUsersByName("胡"));
    }

    @Test
    public void test002() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User(666, "胡汇鑫", 18);
        log.info("insert result {}", userMapper.insert(user));
        log.info("selectById result {}", user = userMapper.selectById(666));
        user.setName("小心心");
        log.info("update result {}", userMapper.updateById(user));
        log.info("selectById result {}", userMapper.selectById(666));
    }



    private void createTable_user() throws IOException, SQLException {
        executeSql(IOUtils.toString(Resources.getResourceAsStream("ddl/user.sql"), "utf-8"));
    }

    private void executeSql(String sql) throws SQLException{
        Connection connection = sqlSession.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }
}
