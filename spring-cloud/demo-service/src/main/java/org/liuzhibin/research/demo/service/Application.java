package org.liuzhibin.research.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
//这里不需要使用ComponentScan指定Spring扫描范围，因为Demo服务的所有实现类都放在org.liuzhibin.research.demo.service包中，
//没有指定扫描范围的情况下，Spring仍然会扫描启动类所在的包
public class Application {
    @Autowired
    private DiscoveryClient client;

    /**
     * 用简单的HTML显示Demo服务REST API接口清单
     * 
     * @return
     */
    @RequestMapping(produces = "text/html;charset=UTF-8")
    public String home() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div style='font-size:14px;padding:20px;line-height:25px;'>");
        sb.append("<a href=")
            .append(client.getLocalServiceInstance().getUri())
            .append("/ping?msg=Ping>")
            .append(client.getLocalServiceInstance().getUri())
            .append("/ping?msg=Ping</a>");
        sb.append("</div>");
        return sb.toString();
    }
    
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

}
