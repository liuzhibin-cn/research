# Spring Boot演示工程

1. 演示使用Spring Boot建立web项目；
2. 演示在Spring Boot环境下使用MyBatis访问MySQL数据库，执行增删改查操作；

# 运行演示项目

### 下载代码
```shell
git clone https://github.com/liuzhibin-cn/research.git ./
```

### 配置数据库

1. 在MySQL中建立演示数据库`demo_db`；
2. 使用下面脚本创建演示用的表：

    ```sql
    CREATE TABLE `demo_db`.`sys_user` (
      `usr_id` INT NOT NULL AUTO_INCREMENT,
      `usr_name` VARCHAR(30) NOT NULL DEFAULT '',
      `create_time` DATETIME NOT NULL DEFAULT current_timestamp,
      PRIMARY KEY (`usr_id`));
    ```
    
3. 在项目中的文件`application.yml`中修改数据库连接信息，包括数据库名、用户、密码等

### 运行

在`spring-boot`目录中执行下面命令，启动web项目：
```shell
mvn spring-boot:run
```

在浏览器中访问：

1. 创建测试用户：[http://127.0.0.1:8001/demo/user/create](http://127.0.0.1:8001/demo/user/create) 
2. 查询测试用户：<br />
   查询所有用户：[http://127.0.0.1:8001/demo/user/find](http://127.0.0.1:8001/demo/user/find) <br />
   查询特定用户：[http://127.0.0.1:8001/demo/user/find?id=2](http://127.0.0.1:8001/demo/user/find?id=2) 
3. 删除测试用户：<br />
   删除所有用户：[http://127.0.0.1:8001/demo/user/delete](http://127.0.0.1:8001/demo/user/delete) <br />
   删除特定用户：[http://127.0.0.1:8001/demo/user/delete?id=2](http://127.0.0.1:8001/demo/user/delete?id=2) 
   
# 在Eclipse中运行

1. 打开Eclipse菜单：`File -> Import`
2. 在对话框中选择：`Maven -> Existing Maven Projects`
3. 下一步，项目根目录选择`spring-boot`目录
4. 在Eclipse中直接 `Run As -> Java Application` 启动即可