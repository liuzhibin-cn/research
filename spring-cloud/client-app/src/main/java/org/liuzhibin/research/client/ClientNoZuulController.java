package org.liuzhibin.research.client;

import org.liuzhibin.research.feign.DemoServiceFeignClient;
import org.liuzhibin.research.feign.DemoServiceNoZuulFeignClient;
import org.liuzhibin.research.feign.OrderServiceNoZuulFeignClient;
import org.liuzhibin.research.order.service.rest.OrderRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端直接访问服务。
 * 
 * @author Richie 刘志斌 yudi@sina.com
 * Nov 4, 2016
 */
@RestController
@RequestMapping("/no-zuul")
public class ClientNoZuulController extends AbstractDemoController {
    @Autowired
    OrderServiceNoZuulFeignClient orderService;
    @Autowired
    DemoServiceNoZuulFeignClient demoService;
    
    @Override
    OrderRestService getOrderService() {
        return orderService;
    }
    @Override
    DemoServiceFeignClient getDemoService() {
        return demoService;
    }   
}