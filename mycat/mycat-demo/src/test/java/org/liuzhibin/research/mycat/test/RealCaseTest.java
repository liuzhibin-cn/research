package org.liuzhibin.research.mycat.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liuzhibin.research.mycat.Application;
import org.liuzhibin.research.mycat.MyProperties;
import org.liuzhibin.research.mycat.dao.ProductDao;
import org.liuzhibin.research.mycat.domain.Cart;
import org.liuzhibin.research.mycat.domain.Member;
import org.liuzhibin.research.mycat.domain.MemberAccount;
import org.liuzhibin.research.mycat.domain.Order;
import org.liuzhibin.research.mycat.domain.OrderDetail;
import org.liuzhibin.research.mycat.domain.Product;
import org.liuzhibin.research.mycat.service.BizException;
import org.liuzhibin.research.mycat.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE, classes={Application.class})
public class RealCaseTest {
	@Autowired
	DemoService service;
	@Autowired
	ProductDao productDao;
	@Autowired
	MyProperties properties;
	DataSource ds = null;
	
	AtomicLong successCounter = new AtomicLong(0), failCounter = new AtomicLong(0);
	long prevSuccess = 0, prevFail = 0, start = 0;
	
	@Test
	public void fullCaseTest() {
		this.fullCaseTestDo();
	}	
	@Test
	public void fullCasePerfTest() {
		RealCaseTest test = this;
		int threads = 50;
		for(int i=0; i<threads; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						boolean result = test.fullCaseTestDo();
						if(result) successCounter.getAndIncrement();
						else failCounter.getAndIncrement();
					}
				}
			}, "thread-" + i);
			t.setDaemon(true);
			t.start();
		}
		System.out.println(threads + " threads started:");
		
		try {
			int count = 0;
			while(count++ < 120) {
				Thread.sleep(1000);
				long currentSuccess = successCounter.get();
				long currentFail = failCounter.get();
				System.out.println( "   TPS: " + String.format("%4d", currentSuccess - prevSuccess) + ", Errors: " + String.format("%4d", currentFail - prevFail) );
				prevSuccess = currentSuccess;
				prevFail = currentFail;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	private boolean fullCaseTestDo() {
		try {
			//随机查10个产品
			List<Product> products = productDao.findProducts((int)Math.round(Math.random()*20), 10);
			Product p1 = products.get((int)Math.floor(Math.random()*10)), p2 = products.get((int)Math.floor(Math.random()*10));
			//注册
			String mobile = "186" + String.format("%08d", Math.round(Math.random()*100000000));
			String email = "test" + Math.round(Math.random()*100000000) + "@test.com";
			Member member = service.registerByMobile(mobile, "qwe123", email, null);
			//登录
			member = service.login(member.getAccount(), member.getPassword());
			Assert.assertNotNull(member);
			//下单
			Cart cart = new Cart(member.getMemberId())
				.saveAddress(member.getNickname(), member.getMobile(), "北京市海淀区翠微路17号院")
				.addProduct(p1.getProductId(), 1, p1.getPrice(), p1.getPrice(), (1-Math.random()/10) * p1.getPrice())
				.addProduct(p2.getProductId(), 2, p2.getPrice(), p2.getPrice()*2, (1-Math.random()/10) * p2.getPrice() * 2);
			Order order = service.createOrder(cart);
			Assert.assertNotNull(order);
			//查订单列表
			List<Order> myOrders = service.findMemberOrders(member.getMemberId(), 0, 10);
			Assert.assertNotNull(myOrders);
			Assert.assertEquals(1, myOrders.size());
			//查订单明细
			List<OrderDetail> details = service.getOrderDetails(order.getOrderId());
			Assert.assertNotNull(details);
			Assert.assertEquals(2, details.size());
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	@Test
	public void mycatRegisterPerfTest() {
		int threads = 50;
		for(int i=0; i<threads; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						String mobile = "186" + String.format("%08d", Math.round(Math.random()*100000000));
						String email = "test" + Math.round(Math.random()*100000000) + "@test.com";
						try {
							service.registerByMobile(mobile, "qwe123", email, null);
							successCounter.getAndIncrement();
						} catch(Exception e) {
							failCounter.getAndIncrement();
						}
					}
				}
			}, "thread-" + i);
			t.setDaemon(true);
			t.start();
		}
		System.out.println(threads + " threads started:");
		
		try {
			int count = 0;
			while(count++ < 120) {
				Thread.sleep(1000);
				long currentSuccess = successCounter.get();
				long currentFail = failCounter.get();
				System.out.println( "   TPS: " + String.format("%4d", currentSuccess - prevSuccess) + ", Errors: " + String.format("%4d", currentFail - prevFail) );
				prevSuccess = currentSuccess;
				prevFail = currentFail;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	@Test
	public void jdbcRegisterPerfTest() {
		int threads = 50;
		RealCaseTest me = this;
		for(int i=0; i<threads; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						String mobile = "186" + String.format("%08d", Math.round(Math.random()*100000000));
						String email = "test" + Math.round(Math.random()*100000000) + "@test.com";
						try {
							me.registerByJdbc(mobile, "qwe123", email, null);
							if(me.start>0) successCounter.getAndIncrement();
						} catch(Exception e) {
							if(me.start>0) failCounter.getAndIncrement();
						}
					}
				}
			}, "thread-" + i);
			t.setDaemon(true);
			t.start();
		}
		System.out.println(threads + " threads started:");
		
		try {
			int count = 1;
			while(count++ < 120) {
				Thread.sleep(1000);
				if(count<10) continue;
				if(start<=0) {
					start = System.currentTimeMillis();
					continue;
				}
				long currentSuccess = successCounter.get();
				long currentFail = failCounter.get();
				System.out.println( "   TPS: " + String.format("%5d", currentSuccess*1000/(System.currentTimeMillis() - start)) + ", Errors: " + String.format("%4d", currentFail - prevFail) );
//				prevSuccess = currentSuccess;
				prevFail = currentFail;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	@Test
	public void jdbcRegisterTest() {
		String mobile = "186" + String.format("%08d", Math.round(Math.random()*100000000));
		String email = "test" + Math.round(Math.random()*100000000) + "@test.com";
		try {
			this.registerByJdbc(mobile, "qwe123", email, null);
		} catch(Exception e) {
			failCounter.getAndIncrement();
		}
	}
	
	private Member registerByJdbc(String mobile, String password, String email, String nickname) {
		//简单校验
		if(mobile==null || mobile.isEmpty() || mobile.trim().length()!=11) {
			throw new BizException("Invalid mobile: " + mobile);
		}
		if(password==null || password.trim().length()<6) {
			throw new BizException("Invalid password: " + password);
		}
				
		try (Connection con = this.getConnection()) {
			//账号是否已经注册过
			if(this.isRegistered(con, mobile, service.getAccountHashcode(mobile)) > 0) {
				throw new BizException("Account: " + mobile + " already registered");
			}
			
			//注册
			//1. 插入member，主键和分片字段均为 member_id
			Member member = new Member();
			member.setMemberId(service.newId());
			member.setAccount(mobile);
			member.setPassword(password);
			member.setMobile(mobile);
			member.setNickname(nickname==null || nickname.isEmpty() ? mobile.substring(0, 3) + "****" + mobile.substring(7, 11) : nickname);
			member.setEmail(email);
			member.setCreatedAt(new Date());
			this.createMember(con, member);
			//2. 插入member_account，分片字段为account_hash，主键为account
			MemberAccount ma = new MemberAccount();
			ma.setAccount(mobile);
			ma.setAccountHash(service.getAccountHashcode(mobile));
			ma.setMemberId(member.getMemberId());
			this.createMemberAccount(con, ma);
			return member;
		} catch(Exception e) {
			throw new BizException(e.getMessage());
		}
	}
	private int createMemberAccount(Connection con, MemberAccount ma) {
		try (PreparedStatement stmt = con.prepareStatement("insert into member_account(account, account_hash, member_id) values (?, ?, ?)")) {
			stmt.setString(1, ma.getAccount());
			stmt.setInt(2, ma.getAccountHash());
			stmt.setLong(3, ma.getMemberId());
			int rows = stmt.executeUpdate();
			stmt.close();
			return rows;
		} catch(Exception e) {
			throw new BizException(e.getMessage());
		}
	}
	private int createMember(Connection con, Member member) {
			try (PreparedStatement stmt = con.prepareStatement("insert into member (member_id, account, password, nickname, mobile, email, created_at) values (?, ?, ?, ?, ?, ?, ?)")) {
				stmt.setLong(1, member.getMemberId());
				stmt.setString(2, member.getAccount());
				stmt.setString(3, member.getPassword());
				stmt.setString(4, member.getNickname());
				stmt.setString(5, member.getMobile());
				stmt.setString(6, member.getEmail());
				stmt.setTimestamp(7, new java.sql.Timestamp(member.getCreatedAt().getTime()));
				int rows = stmt.executeUpdate();
				stmt.close();
				return rows;
			}  catch(Exception e) {
				throw new BizException(e.getMessage());
			}
	}
	private int isRegistered(Connection con, String account, int accountHash) {
		try (PreparedStatement stmt = con.prepareStatement("select count(*) from member_account where account=? and account_hash=?")) {
			stmt.setString(1, account);
			stmt.setInt(2, accountHash);
			ResultSet rs = stmt.executeQuery();
			int result = 0;
			if(rs.next()) {
				result = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			return result;
		} catch(Exception e) {
			throw new BizException(e.getMessage());
		}
	}
	private Connection getConnection() {
		if(ds==null) {
			synchronized (this) {
				if(ds==null) {
					ds = createDataSource();
				}
			}
		}
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			throw new BizException(e.getMessage());
		}
	}
	private DataSource createDataSource() {
		PoolProperties p = new PoolProperties();
        p.setUrl(properties.getDbUrl());
        p.setUsername(properties.getDbUser());
        p.setPassword(properties.getDbPassword());
        p.setDriverClassName("com.mysql.cj.jdbc.Driver");
        p.setJmxEnabled(false);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("/* ping */ select 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(10000);
        p.setTimeBetweenEvictionRunsMillis(10000);
        p.setMaxActive(5);
        p.setInitialSize(5);
        p.setMaxWait(2000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(5);
        p.setLogAbandoned(false);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        
        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);
        return datasource;
	}
}