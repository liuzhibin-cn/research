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
演示用逻辑表及分片规则方案如下：
![](logical-table-and-datanode.png)

逻辑表作用说明：
- `member_account`：会员账号`account`与会员ID`member_id`对应关系，主键为`account`，分片键为`account_hash`。
   1. 会员注册时，除插入`member`表，同时插入`member_account`；
   2. 会员登录，以及注册时判断账号`account`是否已经注册过，
- `member_order`：
