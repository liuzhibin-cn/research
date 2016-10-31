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

/**
 * 订单对外提供的REST服务接口。
 * 
 * @author Richie 刘志斌 yudi@sina.com
 * Oct 31, 2016
 */
@RequestMapping(value = "/order")
public interface OrderRestService {
    /**
     * 客户端、服务端通讯的简单演示。客户端发送一个Ping消息，服务端响应一个Pong消息。<br />
     * 路径：/order/ping?msg=Ping <br />
     * 方法：GET
     * 
     * @param message 客户端发送给服务端的消息，使用URL传参。
     * @return 返回服务端响应消息。
     */
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    String ping(@RequestParam(name = "msg") String message);

    /**
     * 创建订单并返回新建的订单对象。演示请求参数为复杂对象的情况。<br />
     * 路径：/order/create <br />
     * 方法：POST 
     * 
     * <p><strong>注意</strong>：HTTP接口最多只能通过RequestBody传递一个复杂对象参数，如果需要传递多个复杂对象参数，需要额外定义一个包装对象。</p>
     * 
     * <p><code>consumes=MediaType.APPLICATION_JSON_UTF8_VALUE</code>: 客户端调用、服务端处理请求时，对RequestBody都使用JSON格式、UTF-8编码进行序列化反序列化。
     * 客户端使用Feign时，由Feign生成的代理对象会根据这一要求自动采用JSON序列化OrderDTO参数。</p>
     * 
     * @param order 要创建的订单对象，通过RequestBody POST传递，JSON格式，UTF-8编码。
     * @return 新建订单的ID。
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    int createOrder(@RequestBody OrderDTO order);

    /**
     * 获取订单详情。演示返回值为复杂对象的情况。<br />
     * 路径：/order/get/{id} <br />
     * 方法：GET 
     * 
     * <p><code>produces=MediaType.APPLICATION_JSON_UTF8_VALUE</code>: 服务端返回、客户端接收返回值时，对ResponseBody都使用JSON格式、UTF-8编码进行序列化反序列化。
     * 客户端使用Feign时，由Feign生成的代理对象会根据这一要求自动采用JSON反序列化OrderDTO结果。</p>
     * 
     * @param id 订单ID。
     * @return 通过ResponseBody返回订单对象（包含地址信息和订单明细），JSON格式，UTF-8编码。
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    OrderDTO getOrder(@PathVariable("id") Integer id);

    /**
     * 查询订单列表。<br />
     * 路径：/order/find?status=New <br />
     * 方法：GET 
     * 
     * @param status 订单状态，不提供时返回null。
     * @return 通过ResponseBody返回符合条件的订单列表（包含地址信息，不含订单明细），JSON格式，UTF-8编码。
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    List<OrderDTO> findOrders(@RequestParam(name = "status", required = false) OrderStatus status);

    /**
     * 更新订单（状态）。<br />
     * 路径：/order/update/{id}?status=Closed <br />
     * 方法：POST 
     * 
     * @param id 订单ID
     * @param status 新的订单态。
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    void updateOrder(@PathVariable("id") Integer id, @RequestParam(name = "status", required = false) OrderStatus status);
}