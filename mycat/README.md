### MyCat管理
```sh
./mycat-server/bin/mycat start
./mycat-server/bin/mycat stop
./mycat-server/bin/mycat console
```
mycat启动之后，在Mac OSX环境通过mysql客户端连接mycat，需要指定protocol为TCP，否则即使指定了mycat的8066端口，在没有任何报错信息的情况下，连接的是3306上的mysql
```sh
mysql -h localhost -P 8066 -uroot -p --protocol=TCP
mysql -h localhost -P 9066 -uroot -p --protocol=TCP
```
登录mycat的账号密码，使用`server.xml`中`<user>`节点的配置。
登录mycat管理端口9066后，执行`show @@database;`显示`schema.xml`中的schema名称列表：
```
+-------------+
| DATABASE    |
+-------------+
| test_schema |
+-------------+
```
执行`show @@datasource;`显示所有datanode节点：
```
+----------+-----------+-------+-----------+------+------+--------+------+------+---------+-----------+------------+
| DATANODE | NAME      | TYPE  | HOST      | PORT | W/R  | ACTIVE | IDLE | SIZE | EXECUTE | READ_LOAD | WRITE_LOAD |
+----------+-----------+-------+-----------+------+------+--------+------+------+---------+-----------+------------+
| dn1      | localhost | mysql | localhost | 3306 | W    |      0 |    1 |   10 |      26 |         0 |          0 |
| dn0      | localhost | mysql | localhost | 3306 | W    |      0 |    1 |   10 |      26 |         0 |          0 |
| dn3      | 127.0.0.1 | mysql | 127.0.0.1 | 3306 | W    |      0 |    1 |   10 |      26 |         0 |          0 |
| dn2      | localhost | mysql | localhost | 3306 | W    |      0 |    1 |   10 |      26 |         0 |          0 |
| dn4      | 127.0.0.1 | mysql | 127.0.0.1 | 3306 | W    |      0 |    1 |   10 |      26 |         0 |          0 |
+----------+-----------+-------+-----------+------+------+--------+------+------+---------+-----------+------------+
```

### 表及分片规则
以简单的B2C电商系统作为演示应用，演示用逻辑表及分片规则方案如下：
![](logical-table-and-datanode.png)

逻辑表作用说明：
- `member_account`：会员账号`account`与会员ID `member_id`对应关系，主键为`account`，分片键为`account_hash`。<br />
   其它表通过`member_id`与会员表关联，整个系统以`member_id`存取会员数据，因此会员表`member`选择`member_id`作为分片键；<br />
   会员使用`account` + `password`登录为高频场景，因此添加`member_account`表，相当于由应用维护的一个索引。这个也进行水平拆分，使用`account`的hashcode作为分片键；<br />
   1. 会员注册时提供`account`值，由应用生成`member_id`值å，除插入`member`表，同时插入`member_account`表，2个插入操作mycat都可以根据分片字段路由到对应的datanode；
   2. 会员登录，以及注册时判断账号`account`是否已经注册过，都先通过`member_account`表查询`member_id`值，这个查询可以使用分片键完成路由。随后所有会员数据访问都通过`member_id`存取`member`表，同样使用分片键完成路由；
- `member_order`：会员ID `member_id`与会员订单`order_id`对应关系，主键为`member_id` + `order_id`，分片键为`member_id`。<br />
   其作用同`member_account`，是应用维护的一个索引，用于会员查询自己的订单。
