package org.liuzhibin.research.springcloud.client;

import org.liuzhibin.research.order.service.client.rest.OrderRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 使用Feign生成服务代理对象。
 * 
 * @author Richie 刘志斌 yudi@sina.com
 * Oct 31, 2016
 */
@FeignClient("order-service") //这里的名称必须和服务的spring.application.name对应
public interface OrderServiceProxy extends OrderRestService {
}