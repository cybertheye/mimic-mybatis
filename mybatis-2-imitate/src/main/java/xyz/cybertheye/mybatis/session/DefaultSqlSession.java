package xyz.cybertheye.mybatis.session;

import xyz.cybertheye.mybatis.proxy.MapperProxyFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @description:
 */
public class DefaultSqlSession implements SqlSession{
    private Configuration configuration;
    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return MapperProxyFactory.getMapper(type,this);
    }

    //druid
    private Connection connection;

    @Override
    public Connection getConnection() {
        try {
            if(connection!=null && !connection.isClosed()){
                return connection;
            }
            connection = DriverManager.getConnection(
                    this.configuration.getUrl(),
                    this.configuration.getUsername(),
                    this.configuration.getPassword()
            );
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if(connection!=null && !connection.isClosed()){
            connection.close();
        }
    }
}
