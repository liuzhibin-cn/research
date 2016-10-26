package org.liuzhibin.research.springboot;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Insert("insert into sys_user(usr_id, usr_name, create_time) values (#{id},#{name},#{createTime})")
    int createUser(User user);

    @Delete("delete from sys_user where usr_id=#{id}")
    int deleteUser(@Param("id") int id);

    @Delete("truncate table sys_user")
    int deleteAll();

    @Select("select * from sys_user")
    @Results({ @Result(id = true, property = "id", column = "usr_id"), @Result(property = "name", column = "usr_name"),
            @Result(property = "createTime", column = "create_time") })
    List<User> findAll();

    @Select("select * from sys_user where usr_id=#{id}")
    @Results({ @Result(id = true, property = "id", column = "usr_id"), @Result(property = "name", column = "usr_name"),
            @Result(property = "createTime", column = "create_time") })
    User getUser(@Param("id") int id);
}