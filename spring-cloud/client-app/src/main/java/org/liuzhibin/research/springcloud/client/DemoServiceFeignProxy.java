package org.liuzhibin.research.springcloud.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用Feign生成Demo服务的代理对象。
 * 
 * @author Richie 刘志斌 yudi@sina.com
 * Nov 3, 2016
 */
@FeignClient("demo-service") //FeignClient的名称必须和服务的spring.application.name对应
public interface DemoServiceFeignProxy {
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    String ping(@RequestParam(name = "msg") String message);
}