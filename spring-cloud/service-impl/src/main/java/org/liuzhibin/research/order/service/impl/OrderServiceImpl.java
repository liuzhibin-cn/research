package org.liuzhibin.research.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.liuzhibin.research.order.service.OrderStatus;
import org.liuzhibin.research.order.service.dao.OrderAddressDAO;
import org.liuzhibin.research.order.service.dao.OrderDAO;
import org.liuzhibin.research.order.service.dao.OrderDetailDAO;
import org.liuzhibin.research.order.service.domain.Order;
import org.liuzhibin.research.order.service.domain.OrderAddress;
import org.liuzhibin.research.order.service.domain.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl {
    @Autowired
    private OrderDAO orderDao;
    @Autowired
    private OrderDetailDAO orderDetailDao;
    @Autowired
    private OrderAddressDAO orderAddressDao;

    private int orderNoSeq = 101893;
    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    /**
     * 创建订单。
     * 
     * @param order
     * @param address
     * @return 订单ID
     */
    public int createOrder(Order order, OrderAddress address) {
        if (order == null)
            return 0;

        Date createTime = new Date();

        order.setOrderNo("SO-" + format.format(createTime) + "-" + orderNoSeq);
        order.setStatus(OrderStatus.New);
        order.setCreateTime(createTime);
        orderDao.create(order);

        if (order.getDetails() != null) {
            for (OrderDetail detail : order.getDetails()) {
                detail.setOrderId(order.getOrderId());
                detail.setCreateTime(createTime);
                orderDetailDao.create(detail);
            }
        }

        if (address != null) {
            address.setOrderId(order.getOrderId());
            orderAddressDao.create(address);
        }

        return order.getOrderId();
    }

    /**
     * 加载订单和订单明细。
     * 
     * @param id
     * @return
     */
    public Order loadOrderAndDetails(int id) {
        Order order = loadOrder(id);
        loadOrderDetails(order);
        return order;
    }

    /**
     * 加载订单。
     * 
     * @param id
     * @return
     */
    public Order loadOrder(int id) {
        if (id <= 0)
            return null;
        return orderDao.get(id);
    }

    /**
     * 加载订单地址。
     * 
     * @param orderId
     * @return
     */
    public OrderAddress loadOrderAddress(int orderId) {
        if (orderId <= 0)
            return null;
        return orderAddressDao.get(orderId);
    }

    /**
     * 更新订单状态。
     * 
     * @param id
     * @param status
     * @return
     */
    public boolean updateOrderStatus(int id, OrderStatus status) {
        Order order = loadOrder(id);
        if (order == null)
            return false;
        order.setStatus(status);
        return orderDao.updateStatus(order) > 0;
    }

    /**
     * 查询订单。
     * 
     * @return
     */
    public List<Order> findOrders(OrderStatus status) {
        if (status == null)
            return null;
        return this.orderDao.find(status);
    }

    private void loadOrderDetails(Order order) {
        if (order == null || order.getOrderId() <= 0)
            return;
        order.setDetails(orderDetailDao.loadOrderDetails(order.getOrderId()));
    }
}