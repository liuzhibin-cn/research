package org.liuzhibin.research.springcloud.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@RestController
public class Application {
    @Autowired
    private DiscoveryClient client;

    /**
     * 用简单的HTML显示客户端演示用URL地址清单
     * 
     * @return
     */
    @RequestMapping(produces = "text/html;charset=UTF-8")
    public String home() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div style='font-size:12px;padding:25px;'>");
        sb.append("<a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/demo/order/ping?msg=Ping>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/demo/order/ping?msg=Ping</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/demo/order/benchmark>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/demo/order/benchmark</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/demo/order/create>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/demo/order/create</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/demo/order/find?status=New>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/demo/order/find?status=New</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/demo/order/get/1>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/demo/order/get/1</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/demo/order/update/1?status=Close>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/demo/order/update/1?status=Close</a>");
        sb.append("</div>");
        return sb.toString();
    }

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(Application.class, args);
    }

}
