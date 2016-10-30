package org.liuzhibin.research.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = "org.liuzhibin.research.order.service.dao")
@RestController
@ComponentScan(basePackages = "org.liuzhibin.research.order")
public class Application {
    @RequestMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody List<String> home() {
        List<String> urls = new ArrayList<String>();
        urls.add("/order/ping?msg=Ping");
        urls.add("/order/find?status=New");
        urls.add("/order/get/1");
        urls.add("/order/update/1?status=Close");
        return urls;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }
}