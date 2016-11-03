package org.liuzhibin.research.order.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;
import org.liuzhibin.research.order.service.domain.OrderDetail;

@Mapper
public interface OrderDetailDAO {
    @Select("select * from ord_order_detail where ord_id=#{orderId}")
    @Results({ @Result(id = true, property = "lineId", column = "line_id"),
            @Result(property = "orderId", column = "ord_id"), @Result(property = "itemId", column = "itm_id"),
            @Result(property = "itemName", column = "itm_name"), @Result(property = "quantity", column = "qty"),
            @Result(property = "price", column = "price"), @Result(property = "amount", column = "amt"),
            @Result(property = "createTime", column = "create_time") })
    List<OrderDetail> loadOrderDetails(@Param("orderId") int orderId);

    @Insert("insert into ord_order_detail(ord_id,itm_id,itm_name, qty,price,amt,create_time) values (#{orderId},#{itemId},#{itemName},#{quantity},#{price},#{amount},#{createTime})")
    @SelectKey(before = false, keyColumn = "line_id", keyProperty = "lineId", resultType = Integer.class, statement = "select last_insert_id()", statementType = StatementType.STATEMENT)
    int create(OrderDetail orderDetail);
}