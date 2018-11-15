package org.liuzhibin.research.mycat.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.liuzhibin.research.mycat.dao.MemberDao;
import org.liuzhibin.research.mycat.dao.OrderDao;
import org.liuzhibin.research.mycat.dao.ProductDao;
import org.liuzhibin.research.mycat.domain.Cart;
import org.liuzhibin.research.mycat.domain.Member;
import org.liuzhibin.research.mycat.domain.MemberAccount;
import org.liuzhibin.research.mycat.domain.Order;
import org.liuzhibin.research.mycat.domain.OrderDetail;
import org.liuzhibin.research.mycat.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemoService {
	static Date BASE_LINE = null;
	static Date DEFAULT_TIME = null;
	@Autowired
	MemberDao memberDao;
	@Autowired
	ProductDao productDao;
	@Autowired
	OrderDao orderDao;
	
	static {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			BASE_LINE = sdf.parse("2018-01-01");
			DEFAULT_TIME = sdf.parse("1900-01-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 演示用户注册处理逻辑
	 * @param mobile
	 * @param password
	 * @param email
	 * @param nickname
	 * @return
	 */
	public Member registerByMobile(String mobile, String password, String email, String nickname) {
		//简单校验
		if(mobile==null || mobile.isEmpty() || mobile.trim().length()!=11) {
			throw new BizException("Invalid mobile: " + mobile);
		}
		if(password==null || password.trim().length()<6) {
			throw new BizException("Invalid password: " + password);
		}
		
		//账号是否已经注册过
		if(memberDao.isRegistered(mobile, this.getAccountHashcode(mobile)) > 0) {
			throw new BizException("Account: " + mobile + " already registered");
		}
		
		//注册
		//1. 插入member，主键和分片字段均为 member_id
		Member member = new Member();
		member.setMemberId(this.newId());
		member.setAccount(mobile);
		member.setPassword(password);
		member.setMobile(mobile);
		member.setNickname(nickname==null || nickname.isEmpty() ? mobile.substring(0, 3) + "****" + mobile.substring(7, 11) : nickname);
		member.setEmail(email);
		member.setCreatedAt(new Date());
		memberDao.createMember(member);
		//2. 插入member_account，分片字段为account_hash，主键为account
		MemberAccount ma = new MemberAccount();
		ma.setAccount(mobile);
		ma.setAccountHash(this.getAccountHashcode(mobile));
		ma.setMemberId(member.getMemberId());
		memberDao.createMemberAccount(ma);
		return member;
	}
	/**
	 * 演示用户使用账号密码登录的逻辑处理
	 * @param account
	 * @param password
	 * @return
	 */
	public Member login(String account, String password) {
		if(account==null || account.trim().isEmpty()) {
			throw new BizException("Empty account");
		}
		if(password==null || password.trim().isEmpty()) {
			throw new BizException("Empty password");
		}
		//1. 通过account获取member_id，分片键 account_hash
		MemberAccount ma = memberDao.getMemberAccount(account, this.getAccountHashcode(account));
		if(ma==null) {
			throw new BizException("Account error");
		}
		//2. 通过member_id获取member对象，分片键 member_id
		Member member = memberDao.getMember(ma.getMemberId());
		if(member==null) {
			throw new BizException("Account error");
		}
		if(!password.equals(member.getPassword())) {
			throw new BizException("Incorrect password");
		}
		return member;
	}
	
	/**
	 * 简单演示创建订单逻辑
	 * @param cart
	 */
	public Order createOrder(Cart cart) {
		//数据校验
		if(cart==null) {
			throw new BizException("Null cart");
		}
		if(cart.getItems()==null || cart.getItems().isEmpty()) {
			throw new BizException("Invalid cart, empty cart items");
		}
		if(cart.getMemberId()<=0) {
			throw new BizException("Invalid cart, empty member id");
		}
		
		//创建订单、订单明细
		Order order = new Order();
		order.setOrderId(this.newId());
		order.setStatus("New");
		order.setMemberId(cart.getMemberId());
		order.setPayStatus("New");
		order.setPayTime(DEFAULT_TIME);
		order.setContact(cart.getContact());
		order.setPhone(cart.getPhone());
		order.setAddress(cart.getAddress());
		order.setCreatedAt(new Date());
		
		cart.getItems().forEach( item -> {
			OrderDetail detail = new OrderDetail();
			detail.setOrderId(order.getOrderId());
			detail.setProductId(item.getProductId());
			Product product = productDao.get(item.getProductId());
			detail.setSku(product.getSku());
			detail.setTitle(product.getTitle());
			detail.setQuantity(item.getQuantity());
			detail.setPrice(item.getPrice());
			detail.setSubtotal(item.getSubtotal());
			detail.setDiscount(item.getDiscount());
			detail.setCreatedAt(new Date());
			orderDao.createOrderDetail(detail);
			
			order.setTotal( order.getTotal() + item.getSubtotal() );
			order.setDiscount( order.getDiscount() + item.getDiscount() );
		} );
		
		orderDao.createOrder(order);
		
		//添加会员、订单关系
		memberDao.createMemberOrder(order.getMemberId(), order.getOrderId());
		
		return order;
	}
	
	/**
	 * 演示获取会员订单
	 * @param memberId
	 * @param offset
	 * @param count
	 * @return
	 */
	public List<Order> findMemberOrders(long memberId, int offset, int count) {
		List<Long> orderIds = memberDao.findMemberOrder(memberId, offset, count);
		if(orderIds==null || orderIds.isEmpty()) return null;
		return orderDao.findOrders(orderIds);
	}
	/**
	 * 演示获取订单明细
	 * @param orderId
	 * @return
	 */
	public List<OrderDetail> getOrderDetails(long orderId) {
		return orderDao.getOrderDetails(orderId);
	}
	
	public long newId() {
		//高43位毫秒数 + 低21位随机数
		return ((System.currentTimeMillis() - BASE_LINE.getTime()) << 21) | (Math.round(Math.random() * 2097150) & 0x1FFFFF);
	}
	public int getAccountHashcode(String account) {
		int hashcode = account.hashCode();
		return hashcode<0 ? -hashcode : hashcode;
	}
}