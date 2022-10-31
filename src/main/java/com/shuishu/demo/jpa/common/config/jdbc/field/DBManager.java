package com.shuishu.demo.jpa.common.config.jdbc.field;


import com.shuishu.demo.jpa.common.config.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author shuishu
 * @date 2022/3/22 14:17
 */

@Component
public class DBManager {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private Connection connection = null;

    public Connection getConnection() {
        try {
            if (connection == null) {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
                return connection;
            } else {
                return connection;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeAll(Statement statement, Connection connection) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeSql(String sql) {
        Connection connection = getConnection();
        Statement stsm = null;
        if (connection == null) {
            throw new BusinessException("数据库连接失败");
        }
        try {
            stsm = connection.createStatement();
            // 发送sql语句
            stsm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(stsm, null);
        }
    }
}
