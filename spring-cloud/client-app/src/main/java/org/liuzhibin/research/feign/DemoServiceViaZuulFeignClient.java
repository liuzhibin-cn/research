package org.liuzhibin.research.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 通过Zuul服务网关访问Demo服务的Feign代理。
 * 
 * <p>Feign根据接口、方法、参数的注解生成代理对象，处理对服务方法的调用和返回。<br />
 * <code>name="xxx"</code>指定了注册到注册中心的服务名称，Feign根据这个名称从注册中心获取服务地址，并追加接口、
 * 方法上通过{@link RequestMapping @RequestMapping}指定的路径。</p>
 * 
 * <p>在通过服务网关访问服务的情况下，{@link FeignClient @FeignClient}指定的服务名称为网关服务。Feign将调用请求
 * 发送给网关服务，由网关服务进行路由。</p>
 * 
 * @author Richie 刘志斌 yudi@sina.com
 * Nov 3, 2016
 */
@FeignClient(name="zuul-server", path="/demo", configuration=FeignConfiguration.class) 
public interface DemoServiceViaZuulFeignClient {
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    String ping(@RequestParam(name = "msg") String message);
}