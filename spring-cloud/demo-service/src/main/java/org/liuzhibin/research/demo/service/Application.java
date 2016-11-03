package org.liuzhibin.research.demo.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
//这里不需要使用ComponentScan指定Spring扫描范围，因为Demo服务的所有实现类都放在org.liuzhibin.research.demo.service包中，
//没有指定扫描范围的情况下，Spring仍然会扫描启动类所在的包
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

}
