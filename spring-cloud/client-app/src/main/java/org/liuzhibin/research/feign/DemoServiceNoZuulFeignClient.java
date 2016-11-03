package org.liuzhibin.research.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 直接访问Demo服务(不通过Zuul服务网关)的Feign代理。
 * 
 * <p>Feign根据接口、方法、参数的注解生成代理对象，处理对服务方法的调用和返回。<br />
 * <code>name="xxx"</code>指定了注册到注册中心的服务名称，Feign根据这个名称从注册中心获取服务地址，并追加接口、
 * 方法上通过{@link RequestMapping @RequestMapping}指定的路径。</p>
 * 
 * @author Richie 刘志斌 yudi@sina.com
 * Nov 3, 2016
 */
@FeignClient(name="demo-service", configuration=FeignConfiguration.class)
public interface DemoServiceNoZuulFeignClient {
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    String ping(@RequestParam(name = "msg") String message);
}