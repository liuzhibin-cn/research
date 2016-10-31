package org.liuzhibin.research.order.service.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.liuzhibin.research.order.service.DateUtils;

/**
 * 订单明细
 * 
 * @author 刘志斌 yudi@sina.com
 * @since 2016/10/30 13:54:21
 */
public class OrderDetail {
    private Integer lineId;
    private Integer orderId;
    private Integer itemId = 0;
    private String itemName;
    private Integer quantity = 0;
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal amount = BigDecimal.ZERO;
    private Date createTime = DateUtils.defaultDate();

    /**
     * 获取 订单明细ID。
     */
    public Integer getLineId() {
        return this.lineId;
    }

    /**
     * 设置 订单明细ID。
     *
     * @param value
     *            属性值
     */
    public void setLineId(Integer value) {
        if (value == null)
            this.lineId = 0;
        else
            this.lineId = value;
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
     * 获取 产品ID。
     */
    public Integer getItemId() {
        return this.itemId;
    }

    /**
     * 设置 产品ID。
     *
     * @param value
     *            属性值
     */
    public void setItemId(Integer value) {
        if (value == null)
            this.itemId = 0;
        else
            this.itemId = value;
    }

    /**
     * 获取 产品名称。
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * 设置 产品名称。
     *
     * @param value
     *            属性值
     */
    public void setItemName(String value) {
        if (value == null)
            this.itemName = "";
        else
            this.itemName = value;
    }

    /**
     * 获取 数量。
     */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     * 设置 数量。
     *
     * @param value
     *            属性值
     */
    public void setQuantity(Integer value) {
        if (value == null)
            this.quantity = 0;
        else
            this.quantity = value;
    }

    /**
     * 获取 价格。
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * 设置 价格。
     *
     * @param value
     *            属性值
     */
    public void setPrice(BigDecimal value) {
        if (value == null)
            this.price = BigDecimal.ZERO;
        else
            this.price = value;
    }

    /**
     * 获取 金额。
     */
    public BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * 设置 金额。
     *
     * @param value
     *            属性值
     */
    public void setAmount(BigDecimal value) {
        if (value == null)
            this.amount = BigDecimal.ZERO;
        else
            this.amount = value;
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