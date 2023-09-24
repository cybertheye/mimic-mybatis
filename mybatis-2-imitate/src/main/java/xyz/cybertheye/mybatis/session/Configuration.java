package xyz.cybertheye.mybatis.session;

import lombok.Data;

import java.util.Properties;

/**
 * @description:
 */
@Data
public class Configuration {
    private String url;
    private String username;
    private String password;

    public void initFromProperties(Properties properties){
        this.url = properties.getProperty("url");
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
    }

}
