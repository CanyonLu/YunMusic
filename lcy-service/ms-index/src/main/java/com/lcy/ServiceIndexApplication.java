package com.lcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ServiceIndexApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ServiceIndexApplication.class, args);
        System.out.println("        ヾ(◍°∇°◍)ﾉﾞ    ServiceIndexApplication启动成功      ヾ(◍°∇°◍)ﾉﾞ\n" +
                "                    _\n" +
                "            "
                + "      _(_)_                           wWWWw   _\n" +
                "      @@@@       (_)@(_)   vVVVv     _     @@@@  (___) _(_)_\n" +
                "     @@()@@ wWWWw  (_)\\    (___)   _(_)_  @@()@@   Y  (_)@(_)\n" +
                "      @@@@  (___)     `|/    Y    (_)@(_)  @@@@   \\|/   (_)\\\n" +
                "       /      Y       \\|    \\|/    /(_)    \\|      |/      |\n" +
                "    \\ |     \\ |/       | / \\ | /  \\|/       |/    \\|      \\|/\n" +
                "    \\\\|//   \\\\|///  \\\\\\|//\\\\\\|/// \\|///  \\\\\\|//  \\\\|//  \\\\\\|// \n" +
                "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }

}
