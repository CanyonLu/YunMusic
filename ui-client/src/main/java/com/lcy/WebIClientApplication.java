package com.lcy;

import com.lcy.util.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 只负责页面渲染与静态资源，业务数据全部通过 HTTP 调用 ms-client（:8089/client）。
 */
@SpringBootApplication
public class WebIClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebIClientApplication.class, args);
        Banner.print("ui-client", 8088, "/ui");
    }
}
