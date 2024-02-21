package com.lux.task.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "books-api")
public class BooksApiConfig {
    private String baseUrl;
    private String basePath;
    private int port;
    private String username;
    private String password;

}
