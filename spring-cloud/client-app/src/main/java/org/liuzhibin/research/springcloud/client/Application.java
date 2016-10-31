package org.liuzhibin.research.springcloud.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@RestController
public class Application {

    @RequestMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody List<String> home() {
        List<String> urls = new ArrayList<String>();
        urls.add("/demo/order/ping?msg=Ping");
        urls.add("/demo/order/benchmark");
        urls.add("/demo/order/create");
        urls.add("/demo/order/find?status=New");
        urls.add("/demo/order/get/1");
        urls.add("/demo/order/update/1?status=Close");
        return urls;
    }

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(Application.class, args);
    }

}
