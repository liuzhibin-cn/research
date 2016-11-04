package org.liuzhibin.research.client;

import org.liuzhibin.research.feign.DemoServiceFeignClient;
import org.liuzhibin.research.feign.DemoServiceViaZuulFeignClient;
import org.liuzhibin.research.feign.OrderServiceViaZuulFeignClient;
import org.liuzhibin.research.order.service.rest.OrderRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端通过网关访问服务。网关路由规则参考工程spring-cloud-zuul-server中的application.yml
 * 
 * @author Richie 刘志斌 yudi@sina.com
 * Nov 4, 2016
 */
@RestController
@RequestMapping("/via-zuul/")
public class ClientViaZuulController extends AbstractDemoController{
    @Autowired
    OrderServiceViaZuulFeignClient orderService;
    @Autowired
    DemoServiceViaZuulFeignClient demoService;
    
    @Override
    OrderRestService getOrderService() {
        return orderService;
    }
    @Override
    DemoServiceFeignClient getDemoService() {
        return demoService;
    }

}