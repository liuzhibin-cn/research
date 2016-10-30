package org.liuzhibin.research.order.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.liuzhibin.research.order.service.OrderStatus;
import org.liuzhibin.research.order.service.client.rest.OrderDTO;
import org.liuzhibin.research.order.service.client.rest.OrderDetailDTO;
import org.liuzhibin.research.order.service.client.rest.OrderRestService;
import org.liuzhibin.research.order.service.domain.Order;
import org.liuzhibin.research.order.service.domain.OrderAddress;
import org.liuzhibin.research.order.service.domain.OrderDetail;
import org.liuzhibin.research.order.service.impl.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class OrderRestServiceImpl implements OrderRestService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    DiscoveryClient client;

    @Override
    public String ping(@RequestParam(name = "msg") String message) {
        log.info("Message received: " + message);
        String result = "Pong from " + client.getLocalServiceInstance().getHost() + ":"
                + client.getLocalServiceInstance().getPort();
        log.info("Message sent: " + result);
        return result;
    }

    @Override
    public int createOrder(@RequestBody OrderDTO orderDto) {
        if (orderDto == null)
            return 0;

        log.info("Order received: " + jsonEncode(orderDto));

        Order order = new Order();
        order.setStatus(orderDto.getStatus());

        OrderAddress address = new OrderAddress();
        address.setProvince(orderDto.getProvince());
        address.setCity(orderDto.getCity());
        address.setDistrict(orderDto.getDistrict());
        address.setPhone(orderDto.getPhone());
        address.setContact(orderDto.getContact());
        address.setAddress(orderDto.getAddress());

        if (orderDto.getDetails() != null) {
            order.setDetails(new ArrayList<OrderDetail>(orderDto.getDetails().size()));
            for (OrderDetailDTO detailDto : orderDto.getDetails()) {
                OrderDetail detail = new OrderDetail();
                detail.setItemId(detailDto.getItemId());
                detail.setItemName(detailDto.getItemName());
                detail.setQuantity(detailDto.getQuantity());
                detail.setPrice(detailDto.getPrice());
                detail.setAmount(detailDto.getAmount());
                order.getDetails().add(detail);
            }
        }

        int id = orderService.createOrder(order, address);

        log.info("Order created: " + id);

        return id;
    }

    @Override
    public OrderDTO getOrder(@PathVariable("id") Integer id) {
        Order order = orderService.loadOrderAndDetails(id);
        OrderAddress address = orderService.loadOrderAddress(id);
        OrderDTO dto = convertToDTO(order, address);

        log.info("Get order " + id + ": " + jsonEncode(dto));

        return dto;
    }

    @Override
    public List<OrderDTO> findOrders(@RequestParam(name = "status", required = false) OrderStatus status) {
        List<Order> orders = orderService.findOrders(status);
        if (orders == null)
            return null;
        List<OrderDTO> dtos = new ArrayList<OrderDTO>(orders.size());
        for (Order order : orders) {
            OrderAddress address = orderService.loadOrderAddress(order.getOrderId());
            OrderDTO dto = convertToDTO(order, address);
            dtos.add(dto);
        }

        log.info("Find order " + status + ":" + jsonEncode(dtos));

        return dtos;
    }

    @Override
    public void updateOrder(@PathVariable("id") Integer id,
            @RequestParam(name = "status", required = false) OrderStatus status) {
        if (id == null)
            return;
        if (status != null) {
            orderService.updateOrderStatus(id, status);
        }

        log.info("Update order: " + id + ", " + status);
    }

    private OrderDTO convertToDTO(Order order, OrderAddress address) {
        if (order == null)
            return null;
        OrderDTO orderDto = new OrderDTO();

        orderDto.setOrderId(order.getOrderId());
        orderDto.setOrderNo(order.getOrderNo());
        orderDto.setStatus(order.getStatus());
        orderDto.setCreateTime(order.getCreateTime());

        if (address != null) {
            orderDto.setProvince(address.getProvince());
            orderDto.setCity(address.getCity());
            orderDto.setDistrict(address.getDistrict());
            orderDto.setPhone(address.getPhone());
            orderDto.setContact(address.getContact());
            orderDto.setAddress(address.getAddress());
        }

        if (order.getDetails() != null) {
            orderDto.setDetails(new ArrayList<OrderDetailDTO>(order.getDetails().size()));
            for (OrderDetail detail : order.getDetails()) {
                OrderDetailDTO detailDto = new OrderDetailDTO();
                detailDto.setLineId(detail.getLineId());
                detailDto.setOrderId(detail.getOrderId());
                detailDto.setItemId(detail.getItemId());
                detailDto.setItemName(detail.getItemName());
                detailDto.setQuantity(detail.getQuantity());
                detailDto.setPrice(detail.getPrice());
                detailDto.setAmount(detail.getAmount());
                detailDto.setCreateTime(detail.getCreateTime());
                orderDto.getDetails().add(detailDto);
            }
        }

        return orderDto;
    }

    private String jsonEncode(Object obj) {
        if (obj == null)
            return "null";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(obj);
    }
}