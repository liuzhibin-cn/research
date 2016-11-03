package org.liuzhibin.research.order.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.liuzhibin.research.order.service.OrderStatus;
import org.liuzhibin.research.order.service.domain.Order;

@Mapper
public interface OrderDAO {
    @Select("select * from ord_order where ord_id=#{id}")
    @Results({ @Result(id = true, property = "orderId", column = "ord_id"),
            @Result(property = "orderNo", column = "ord_no"), @Result(property = "status", column = "status"),
            @Result(property = "createTime", column = "create_time") })
    Order get(@Param("id") int id);

    @Select("select * from ord_order where status=#{status}")
    @Results({ @Result(id = true, property = "orderId", column = "ord_id"),
            @Result(property = "orderNo", column = "ord_no"), @Result(property = "status", column = "status"),
            @Result(property = "createTime", column = "create_time") })
    List<Order> find(@Param("status") OrderStatus status);

    @Insert("insert into ord_order(ord_no, status, create_time) values (#{orderNo},#{status},#{createTime})")
    @SelectKey(before = false, keyColumn = "ord_id", keyProperty = "orderId", resultType = Integer.class, statement = "select last_insert_id()", statementType = StatementType.STATEMENT)
    int create(Order order);

    @Update("update ord_order set status = #{status} where ord_id = #{orderId}")
    int updateStatus(Order order);
}