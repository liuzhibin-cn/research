package org.liuzhibin.research.order.service.domain;

import java.io.Serializable;

/**
 * 订单地址
 * 
 * @author 刘志斌 yudi@sina.com
 * @since 2016/10/30 13:54:21
 */
public class OrderAddress implements Serializable {
    private static final long serialVersionUID = -111185308836405029L;

    private Integer orderId = 0;
    private String province = "";
    private String city = "";
    private String district = "";
    private String phone = "";
    private String contact = "";
    private String address = "";

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

}