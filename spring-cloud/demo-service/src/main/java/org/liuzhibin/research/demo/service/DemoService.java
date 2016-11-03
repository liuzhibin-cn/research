package org.liuzhibin.research.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private DiscoveryClient client;
    
    /**
     * 客户端、服务端通讯的简单演示。客户端发送一个Ping消息，服务端响应一个Pong消息。<br />
     * 路径：/order/ping?msg=Ping <br />
     * 方法：GET
     * 
     * @param message 客户端发送给服务端的消息，使用URL传参。
     * @return 返回服务端响应消息。
     */
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping(@RequestParam(name = "msg") String message) {
        log.info("Message received: " + message);
        String result = "Pong from " + client.getLocalServiceInstance().getHost() + ":"
                + client.getLocalServiceInstance().getPort();
        log.info("Message sent: " + result);
        return result;
    }
}