package com.lcy;

import com.lcy.util.Banner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 数据源由 druid-spring-boot-starter 提供，故排除 Spring Boot 默认的数据源自动配置。
 */
@MapperScan("com.lcy.mapper")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ServiceClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceClientApplication.class, args);
        Banner.print("ms-client", 8089, "/client");
    }
}
