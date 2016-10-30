package org.liuzhibin.research.order.service.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.liuzhibin.research.order.service.domain.OrderAddress;

@Mapper
public interface OrderAddressDAO {
    @Select("select * from ord_address where ord_id=#{orderId}")
    @Results({ @Result(id = true, property = "orderId", column = "ord_id"),
            @Result(property = "province", column = "province"), @Result(property = "city", column = "city"),
            @Result(property = "district", column = "district"), @Result(property = "phone", column = "phone"),
            @Result(property = "contact", column = "contact"), @Result(property = "address", column = "address") })
    OrderAddress get(@Param("orderId") int orderId);

    @Insert("insert into ord_address(ord_id,province,city,district,phone,contact,address) values (#{orderId},#{province},#{city},#{district},#{phone},#{contact},#{address})")
    int create(OrderAddress address);
}