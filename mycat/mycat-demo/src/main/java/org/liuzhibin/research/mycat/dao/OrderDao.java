package org.liuzhibin.research.mycat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.liuzhibin.research.mycat.domain.Order;
import org.liuzhibin.research.mycat.domain.OrderDetail;

@Mapper
public interface OrderDao {
	@Insert("insert into order_header (order_id, member_id, status, total, discount, payment, pay_time, pay_status, contact, phone, address, created_at) " 
			+ "values(#{orderId}, #{memberId}, #{status}, #{total}, #{discount}, #{payment}, #{payTime}, #{payStatus}, #{contact}, #{phone}, #{address}, #{createdAt})")
	int createOrder(Order order);
	@Insert("insert into order_detail (detail_id, order_id, product_id, sku, title, quantity, price, subtotal, discount, created_at) " 
			+ "values(next value for MYCATSEQ_ORDERDETAIL, #{orderId}, #{productId}, #{sku}, #{title}, #{quantity}, #{price}, #{subtotal}, #{discount}, #{createdAt})")
	int createOrderDetail(OrderDetail detail);
	
	List<Order> findOrders(@Param("orderIds") List<Long> orderIds);
	
	@Select("select * from order_detail where order_id = #{orderId}")
	@ResultMap("orderDetail")
	List<OrderDetail> getOrderDetails(long orderId);
}