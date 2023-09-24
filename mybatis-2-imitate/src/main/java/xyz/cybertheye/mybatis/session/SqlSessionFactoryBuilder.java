package xyz.cybertheye.mybatis.session;

/**
 * @description:
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(Configuration configuration){
        return new DefaultSqlSessionFactory(configuration);
    }
}
