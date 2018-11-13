package org.liuzhibin.research.mycat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;
import org.liuzhibin.research.mycat.domain.Product;

@Mapper
public interface ProductDao {
	@Insert("insert into product (sku, category, price, title, created_at) values (#{sku}, #{category}, #{price}, #{title}, #{createdAt})")
	@SelectKey(before=false, keyProperty="productId", keyColumn="product_id", statement="select last_insert_id()", resultType=Integer.class, statementType=StatementType.STATEMENT)
	int insertWithAutoIncreament(Product product);
	
	@Insert("insert into product (product_id, sku, category, price, title, created_at) values (#{productId}, #{sku}, #{category}, #{price}, #{title}, #{createdAt})")
	int insertWithoutAutoIncreament(Product product);
	
	@Select("select * from product where product_id=#{productId}")
	@ResultMap("product")
	Product get(int productId);
	
	@Delete("delete from product where product_id=#{productId}")
	int delete(int productId);

	@Select("select * from product limit #{offset}, #{count}")
	@ResultMap("product")
	List<Product> findProducts(int offset, int count);
}