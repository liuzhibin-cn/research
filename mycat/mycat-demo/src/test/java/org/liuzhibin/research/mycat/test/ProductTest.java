package org.liuzhibin.research.mycat.test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liuzhibin.research.mycat.Application;
import org.liuzhibin.research.mycat.dao.ProductDao;
import org.liuzhibin.research.mycat.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE, classes={Application.class})
public class ProductTest {
	@Autowired
	ProductDao productDao;
	
	/**
	 * 1. 测试非拆分表的增删查；
	 * 2. 测试使用mysql自增长主键的情况；
	 */
	@Test
	public void testAutoIncreament() {
		Product p1 = new Product(), p2 = null;
		int rows = 0;
		try {
			p1.setProductId(100);
			p1.setSku("test100");
			p1.setCategory("test");
			p1.setTitle("test100");
			p1.setPrice(10);
			p1.setCreatedAt(new Date());
			rows = productDao.insertWithoutAutoIncreament(p1);
			Assert.assertEquals(1, rows);
			p2 = productDao.get(p1.getProductId());
			this.assertEquals(p1, p2);
			rows = productDao.delete(p1.getProductId());
			Assert.assertEquals(1, rows);
		} catch(Exception e) {
			
		} finally {
			productDao.delete(100);
		}
		
		try {
			p1 = new Product();
			p1.setSku("test100");
			p1.setCategory("test");
			p1.setTitle("test100");
			p1.setPrice(10);
			p1.setCreatedAt(new Date());
			rows = productDao.insertWithAutoIncreament(p1);
			Assert.assertEquals(1, rows);
			Assert.assertTrue(p1.getProductId()>0);
			p2 = productDao.get(p1.getProductId());
			this.assertEquals(p1, p2);
			rows = productDao.delete(p1.getProductId());
			Assert.assertEquals(1, rows);
		} catch(Exception e) {
			
		} finally {
			if(p1.getProductId()>0) productDao.delete(p1.getProductId());
		}
	}
	private void assertEquals(Product p1, Product p2) {
		if(p1==null) {
			Assert.assertNull(p2);
			return;
		}
		Assert.assertEquals(p1.getProductId(), p2.getProductId());
		Assert.assertEquals(p1.getSku(), p2.getSku());
		Assert.assertEquals(p1.getCategory(), p2.getCategory());
		Assert.assertEquals(p1.getTitle(), p2.getTitle());
		Assert.assertEquals(p1.getPrice(), p2.getPrice(), 0.00001);
		//字段类型用的DATETIME，没有额外指定精度，mysql仅精确到秒，这里判断相等也同样精确到秒
		this.assertEquals(p1.getCreatedAt(), p2.getCreatedAt());
	}
	private void assertEquals(Date d1, Date d2) {
		//存入mysql时，对毫秒数部分采用了四舍五入
		Assert.assertEquals(Math.round(d1.getTime()/1000.0), Math.round(d2.getTime()/1000.0));
	}
	
	@Test
	@Ignore
	public void importProduct() throws IOException {
		Gson gson = new Gson();
		URL url = this.getClass().getClassLoader().getResource("products.json");
		String json = new String(Files.readAllBytes(Paths.get(url.getPath())));
		List<Map<String, Object>> list = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>(){}.getType());
		for(Map<String, Object> map : list) {
			Product product = new Product();
			product.setProductId(100000000 + Math.round(Float.parseFloat(map.get("product_id").toString())));
			product.setSku(map.get("sku").toString());
			product.setCategory(map.get("CATEGORY").toString());
			product.setTitle(map.get("TITLE").toString());
			product.setPrice(Double.parseDouble(map.get("PRICE").toString()));
			product.setCreatedAt(new Date());
			productDao.insertWithoutAutoIncreament(product);
		}
	}
}