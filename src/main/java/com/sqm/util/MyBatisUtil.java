package com.sqm.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p> </p>
 *
 * @author sqm
 * @version 1.0
 */
public class MyBatisUtil {
    //私有静态对象
    private static SqlSessionFactory factory;

    //获取session对象
    public static SqlSession getSession() {
        try {
            if (factory == null) {
                InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                factory = new SqlSessionFactoryBuilder().build(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return factory.openSession();
    }
}
