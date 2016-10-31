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

/**
 * 将OrderServiceImpl封装成REST形式的对外开放接口。
 * 
 * <ul>
 *  <li><strong>领域模型封装工作</strong><br />
 *      业务逻辑在OrderServiceImpl中实现；OrderServiceImpl提供细粒度接口。<br />
 *      OrderRestServiceImpl根据客户端场景提供合适的粗粒度接口（REST方式）；OrderRestServiceImpl仅对OrderServiceImpl进行简单的组装，
 *      不能包含过多的判断等业务逻辑处理。
 *  </li>
 *  <li><strong>对象模型转换工作</strong><br />
 *      OrderServiceImpl使用org.liuzhibin.research.order.service.domain中定义的对象模型，为细粒度对象模型，
 *      实际开发中与表结构绑定比较紧密（即通常将DAO作为领域对象模型）。<br />
 *      OrderRestServiceImpl使用org.liuzhibin.research.order.service.client.rest中定义的对象模型（DTO），为粗粒度对象模型，
 *      专用于与客户端的交互，为客户端屏蔽内部细粒度对象模型的复杂性，提高网络通讯效率。
 *  </li>
 * </ul>
 * 
 * <p>
 *      <strong>客户端服务端公共jar包</strong><br />
 *      spring-cloud-demo-service-client是服务端、客户端公共的jar包。
 *      对外接口、DTO对象定义在spring-cloud-demo-service-client中，方便客户端使用，代价是将对REST服务的弱类型依赖变成了强类型依赖。<br />
 *      如果服务接口的参数以基本数据类型为主，没必要采用公共jar包方式；如果服务接口参数大量采用自定义业务对象，则使用这种公共jar方式比较方便。
 * </p>
 *  
 *  <p>
 *      <strong>Spring MVC Annotation说明</strong><br />
 *      1. 类和方法层级的RequestMapping声明在OrderRestService上；<br />
 *      2. 参数层级的annotation在OrderRestService和OrderRestServiceImpl都有说明，必须保持一致；<br />
 *      这是因为服务端实现接口时，实现类无法从接口继承到参数的annotation，因此在实现类中必须重复声明一下。<br />
 *      TODO: 两处同时维护且必须一致，给维护工作带来麻烦和隐患，方案待完善。
 *  </p>
 *  
 * @author Richie 刘志斌 yudi@sina.com
 * Oct 31, 2016
 */
@RestController
public class OrderRestServiceImpl implements OrderRestService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private DiscoveryClient client;

    /*
     * (non-Javadoc)
     */
    @Override
    public String ping(@RequestParam(name = "msg") String message) {
        log.info("Message received: " + message);
        String result = "Pong from " + client.getLocalServiceInstance().getHost() + ":"
                + client.getLocalServiceInstance().getPort();
        log.info("Message sent: " + result);
        return result;
    }

    /*
     * (non-Javadoc)
     */
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

    /*
     * (non-Javadoc)
     */
    @Override
    public OrderDTO getOrder(@PathVariable("id") Integer id) {
        Order order = orderService.loadOrderAndDetails(id);
        OrderAddress address = orderService.loadOrderAddress(id);
        OrderDTO dto = convertToDTO(order, address);

        log.info("Get order " + id + ": " + jsonEncode(dto));

        return dto;
    }

    /*
     * (non-Javadoc)
     */
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

    /*
     * (non-Javadoc)
     */
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