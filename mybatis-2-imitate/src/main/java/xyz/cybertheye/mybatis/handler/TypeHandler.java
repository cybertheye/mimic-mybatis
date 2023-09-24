package xyz.cybertheye.mybatis.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description:
 */

public interface TypeHandler<T> {

    void setParameter(PreparedStatement ps, int i, T parameter) throws SQLException;

    T getResult(ResultSet rs, String columnName) throws SQLException;
}
