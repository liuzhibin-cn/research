package org.liuzhibin.research.springcloud.client;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.liuzhibin.research.order.service.OrderStatus;
import org.liuzhibin.research.order.service.client.rest.OrderDTO;
import org.liuzhibin.research.order.service.client.rest.OrderDetailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class DemoOrderClientController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    OrderServiceFeignProxy orderService;
    @Autowired
    DemoServiceFeignProxy demoService;

    @Autowired
    DiscoveryClient client;

    @RequestMapping(value = "/demo/ping", method = RequestMethod.GET)
    public String ping() {
        String message = "Ping from " + client.getLocalServiceInstance().getHost() + ":"
                + client.getLocalServiceInstance().getPort();
        log.info("Message sent: " + message);
        message = demoService.ping(message);
        log.info("Message received: " + message);
        return message;
    }

    @RequestMapping(value = "/demo/benchmark", method = RequestMethod.GET)
    public String benchmark() {
        int count = 5000;
        StringBuilder sb = new StringBuilder();
        DecimalFormat format = new DecimalFormat("#0.00");

        // 预热
        demoService.ping("Warn-Up Ping");
        demoService.ping("Warn-Up Ping");
        demoService.ping("Warn-Up Ping");
        demoService.ping("Warn-Up Ping");

        long start = System.nanoTime(), end = 0;
        for (int i = 0; i < count; i++)
            demoService.ping("Benchmarck Ping");
        end = System.nanoTime();
        sb.append("第一轮").append(count).append("次，耗时 ").append(format.format((end - start) / 1000000 / 1000.0))
                .append(" 秒，平均 ").append(format.format((end - start) / 1000000 / 1.0 / count)).append(" 毫秒<br />");

        start = System.nanoTime();
        for (int i = 0; i < count; i++)
            demoService.ping("Benchmarck Ping");
        end = System.nanoTime();
        sb.append("第二轮").append(count).append("次，耗时 ").append(format.format((end - start) / 1000000 / 1000.0))
                .append(" 秒，平均 ").append(format.format((end - start) / 1000000 / 1.0 / count)).append(" 毫秒<br />");

        start = System.nanoTime();
        for (int i = 0; i < count; i++)
            demoService.ping("Benchmarck Ping");
        end = System.nanoTime();
        sb.append("第三轮").append(count).append("次，耗时 ").append(format.format((end - start) / 1000000 / 1000.0))
                .append(" 秒，平均 ").append(format.format((end - start) / 1000000 / 1.0 / count)).append(" 毫秒<br />");

        return sb.toString();
    }

    @RequestMapping(value = "/order/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public OrderDTO create() {
        OrderDTO order = new OrderDTO();
        order.setProvince("山东");
        order.setCity("青岛");
        order.setDistrict("崂山区");
        order.setAddress("海尔路1号");
        order.setPhone("186xxxxxxxx");
        order.setContact("张三");

        order.setDetails(new ArrayList<OrderDetailDTO>(1));

        OrderDetailDTO detail = new OrderDetailDTO();
        detail.setItemId(189165);
        detail.setItemName("西门子 8公斤 变频 滚筒洗衣机 LED显示 触摸控制 低噪音 洗涤分离 银色");
        detail.setQuantity(2);
        detail.setPrice(new BigDecimal(2699));
        detail.setAmount(detail.getPrice().multiply(new BigDecimal(detail.getQuantity())));

        order.getDetails().add(detail);

        log.info("Order sent:" + this.jsonEncode(order));
        int id = orderService.createOrder(order);
        order = orderService.getOrder(id);
        log.info("Order received:" + this.jsonEncode(order));

        return order;
    }

    @RequestMapping(value = "/order/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public List<OrderDTO> find(@RequestParam(value = "status") OrderStatus status) {
        return orderService.findOrders(status);
    }

    @RequestMapping(value = "/order/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public OrderDTO get(@PathVariable(name = "id") Integer id) {
        return orderService.getOrder(id);
    }

    @RequestMapping(value = "/order/update/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public OrderDTO update(@PathVariable(name = "id") Integer id, @RequestParam(value = "status") OrderStatus status) {
        orderService.updateOrder(id, status);
        return orderService.getOrder(id);
    }

    private String jsonEncode(Object obj) {
        if (obj == null)
            return "null";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(obj);
    }
}