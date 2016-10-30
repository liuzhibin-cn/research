package org.liuzhibin.research.order.service.client.rest;

import java.util.Date;
import java.util.List;

import org.liuzhibin.research.order.service.OrderStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderDTO {
    @SuppressWarnings("deprecation")
    private static final Date defaultDate = new Date(1900, 1, 1);

    // @JsonProperty(value = "oid")
    private Integer orderId = 0;
    // @JsonProperty(value = "no")
    private String orderNo = "";
    private OrderStatus status = OrderStatus.New;
    // @JsonProperty(value = "p")
    private String province = "";
    // @JsonProperty(value = "c")
    private String city = "";
    // @JsonProperty(value = "d")
    private String district = "";
    private String phone = "";
    private String contact = "";
    // @JsonProperty(value = "addr")
    private String address = "";
    // @JsonProperty(value = "time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime = defaultDate;
    private List<OrderDetailDTO> details;

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
     * 获取 省。
     */
    public String getProvince() {
        return this.province;
    }

    /**
     * 设置 省。
     *
     * @param value
     *            属性值
     */
    public void setProvince(String value) {
        if (value == null)
            this.province = "";
        else
            this.province = value;
    }

    /**
     * 获取 城市。
     */
    public String getCity() {
        return this.city;
    }

    /**
     * 设置 城市。
     *
     * @param value
     *            属性值
     */
    public void setCity(String value) {
        if (value == null)
            this.city = "";
        else
            this.city = value;
    }

    /**
     * 获取 区县。
     */
    public String getDistrict() {
        return this.district;
    }

    /**
     * 设置 区县。
     *
     * @param value
     *            属性值
     */
    public void setDistrict(String value) {
        if (value == null)
            this.district = "";
        else
            this.district = value;
    }

    /**
     * 获取 联系电话。
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * 设置 联系电话。
     *
     * @param value
     *            属性值
     */
    public void setPhone(String value) {
        if (value == null)
            this.phone = "";
        else
            this.phone = value;
    }

    /**
     * 获取 联系人。
     */
    public String getContact() {
        return this.contact;
    }

    /**
     * 设置 联系人。
     *
     * @param value
     *            属性值
     */
    public void setContact(String value) {
        if (value == null)
            this.contact = "";
        else
            this.contact = value;
    }

    /**
     * 获取 详细地址。
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置 详细地址。
     *
     * @param value
     *            属性值
     */
    public void setAddress(String value) {
        if (value == null)
            this.address = "";
        else
            this.address = value;
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
            this.createTime = defaultDate;
        else
            this.createTime = value;
    }

    public List<OrderDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetailDTO> details) {
        this.details = details;
    }

}