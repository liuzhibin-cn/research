package org.liuzhibin.research.mycat.test;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liuzhibin.research.mycat.Application;
import org.liuzhibin.research.mycat.dao.ProductDao;
import org.liuzhibin.research.mycat.domain.Cart;
import org.liuzhibin.research.mycat.domain.Member;
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
	
	AtomicLong successCounter = new AtomicLong(0), failCounter = new AtomicLong(0);
	long prevSuccess = 0, prevFail = 0;
	
	@Test
	public void fullCaseTest() {
		this.runTest();
	}
	
	@Test
	public void fullCasePerfTest() {
		RealCaseTest test = this;
		int threads = 10;
		for(int i=0; i<threads; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						boolean result = test.runTest();
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
	@Test
	public void perfTest() {
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
						}
						catch(BizException e) {
							
						}
						catch(Exception e) {
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
	
	private boolean runTest() {
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
}