package org.liuzhibin.research.order.service.domain;

import java.util.Date;
import java.util.List;

import org.liuzhibin.research.order.service.DateUtils;
import org.liuzhibin.research.order.service.OrderStatus;

/**
 * 订单
 *
 * @author 刘志斌 yudi@sina.com
 * @since 2016/10/30 13:54:21
 */
public class Order {
    private Integer orderId = 0;
    private String orderNo = "";
    private OrderStatus status = OrderStatus.New;
    private Date createTime = DateUtils.defaultDate();
    private List<OrderDetail> details;

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    /**
     * 获取 订单ID。
     */
    public Integer getOrderId() {
        return this.orderId;
    }

    /**
     * 设置 订单ID。
     *
     * @param value
     *            属性值
     */
    public void setOrderId(Integer value) {
        if (value == null)
            this.orderId = 0;
        else
            this.orderId = value;
    }

    /**
     * 获取 订单号。
     */
    public String getOrderNo() {
        return this.orderNo;
    }

    /**
     * 设置 订单号。
     *
     * @param value
     *            属性值
     */
    public void setOrderNo(String value) {
        if (value == null)
            this.orderNo = "";
        else
            this.orderNo = value;
    }

    /**
     * 获取 订单状态。
     */
    public OrderStatus getStatus() {
        return this.status;
    }

    /**
     * 设置 订单状态。
     *
     * @param value
     *            属性值
     */
    public void setStatus(OrderStatus value) {
        if (value == null)
            this.status = OrderStatus.Undefined;
        else
            this.status = value;
    }

    /**
     * 获取 创建时间。
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value
     *            属性值
     */
    public void setCreateTime(Date value) {
        if (value == null)
            this.createTime = DateUtils.defaultDate();
        else
            this.createTime = value;
    }

}