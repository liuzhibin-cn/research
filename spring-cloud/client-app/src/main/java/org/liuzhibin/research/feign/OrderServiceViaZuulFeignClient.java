package org.liuzhibin.research.feign;

import org.liuzhibin.research.order.service.rest.OrderRestService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 通过Zuul服务网关访问Order服务的Feign代理。
 * 
 * <p>Feign根据接口、方法、参数的注解生成代理对象，处理对服务方法的调用和返回。<br />
 * <code>name="xxx"</code>指定了注册到注册中心的服务名称，Feign根据这个名称从注册中心获取服务地址，并追加接口、
 * 方法上通过{@link RequestMapping @RequestMapping}指定的路径。</p>
 * 
 * <p>在通过服务网关访问服务的情况下，{@link FeignClient @FeignClient}指定的服务名称为网关服务。Feign将调用请求
 * 发送给网关服务，由网关服务进行路由。<br />
 * 网关路由规则参考工程spring-cloud-zuul-server中的application.yml</p>
 * 
 * <p>Feign支持接口继承，参考<a href="http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html#spring-cloud-feign-inheritance">http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html#spring-cloud-feign-inheritance</a>。<br />
 * <span style="color:red">Bug描述：</span><br />
 * 下面{@link OrderServiceViaZuulFeignClient}，在应用启动创建FeignClient时，会使用<code>@RequestMapping(value="/sdk/order/order")</code>
 * 指定的路径注册URL Mapping，但在实际执行时，使用的是{@link OrderRestService}上的<code>@RequestMapping(value="/order")</code>，
 * 导致服务网关无法路由而报错。通过在<code>@FeignClient</code>中指定<code>path="/sdk/order"</code>可解决这个问题，
 * <span style="color:red">但这一行为未来Feign升级时可能会发生变化</span>。</p>
 * 
 * @author Richie 刘志斌 yudi@sina.com
 * Oct 31, 2016
 */
@FeignClient(name="zuul-server", path="/sdk/order", configuration=FeignConfiguration.class)
@RequestMapping(value="/sdk/order/order")
public interface OrderServiceViaZuulFeignClient extends OrderRestService {
}