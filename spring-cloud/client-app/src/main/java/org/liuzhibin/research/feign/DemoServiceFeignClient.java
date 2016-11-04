package org.liuzhibin.research.feign;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface DemoServiceFeignClient {
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    String ping(@RequestParam(name = "msg") String message);
}