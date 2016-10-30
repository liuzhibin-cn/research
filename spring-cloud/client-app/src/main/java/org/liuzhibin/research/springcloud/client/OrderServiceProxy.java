package org.liuzhibin.research.springcloud.client;

import org.liuzhibin.research.order.service.client.rest.OrderRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("order-service")
public interface OrderServiceProxy extends OrderRestService {
}