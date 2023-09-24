package xyz.cybertheye.mybatis.session;

import java.sql.Connection;

/**
 * @description:
 */

public interface SqlSession extends AutoCloseable{
    /**
     * Retrieves current configuration.
     *
     * @return Configuration
     */
    Configuration getConfiguration();

    /**
     * Retrieves a mapper.
     *
     * @param <T>
     *          the mapper type
     * @param type
     *          Mapper interface class
     *
     * @return a mapper bound to this SqlSession
     */
    <T> T getMapper(Class<T> type);

    /**
     * Retrieves inner database connection.
     *
     * @return Connection
     */
    Connection getConnection();
}
