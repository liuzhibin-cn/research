package org.liuzhibin.research.order.service.client.rest;

import java.util.List;

import org.liuzhibin.research.order.service.OrderStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/order")
public interface OrderRestService {
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    String ping(@RequestParam(name = "msg") String message);

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    int createOrder(@RequestBody OrderDTO order);

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    OrderDTO getOrder(@PathVariable("id") Integer id);

    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    List<OrderDTO> findOrders(@RequestParam(name = "status", required = false) OrderStatus status);

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    void updateOrder(@PathVariable("id") Integer id,
            @RequestParam(name = "status", required = false) OrderStatus status);
}