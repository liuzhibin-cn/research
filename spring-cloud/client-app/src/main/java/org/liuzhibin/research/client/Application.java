package org.liuzhibin.research.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@EnableDiscoveryClient
//EnableFeignClients与ComponentScan中分别指定basePackages的区别，参考org.liuzhibin.research.feign.FeignConfiguration
@EnableFeignClients(basePackages="org.liuzhibin.research.feign")
@SpringBootApplication
@RestController
@ComponentScan(basePackages = "org.liuzhibin.research.client")
@EnableHystrix
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
        sb.append("<div style='font-size:14px;padding:20px;line-height:25px;'>");
        sb.append("<a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/via-zuul/demo/ping?msg=Ping>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/via-zuul/demo/ping?msg=Ping</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/via-zuul/demo/benchmark>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/via-zuul/demo/benchmark</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/via-zuul/order/create>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/via-zuul/order/create</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/via-zuul/order/find?status=New>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/via-zuul/order/find?status=New</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/via-zuul/order/get/1>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/via-zuul/order/get/1</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/via-zuul/order/update/1?status=Close>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/via-zuul/order/update/1?status=Close</a>");
        sb.append("</div>");
        
        sb.append("<div style='font-size:14px;padding:20px;line-height:25px;'>");
        sb.append("<a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/no-zuul/demo/ping?msg=Ping>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/no-zuul/demo/ping?msg=Ping</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/no-zuul/demo/benchmark>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/no-zuul/demo/benchmark</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/no-zuul/order/create>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/no-zuul/order/create</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/no-zuul/order/find?status=New>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/no-zuul/order/find?status=New</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/no-zuul/order/get/1>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/no-zuul/order/get/1</a>");
        sb.append("<br /><a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/no-zuul/order/update/1?status=Close>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/no-zuul/order/update/1?status=Close</a>");
        sb.append("</div>");
        
        return sb.toString();
    }

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(Application.class, args);
    }

}
