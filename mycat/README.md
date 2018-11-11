### MyCat启动、停止
```sh
./bin/mycat start
./bin/mycat stop
./bin/mycat console
```
mycat启动之后，在Mac OSX环境通过mysql客户端连接mycat，需要指定protocol为TCP，否则即使指定了mycat的8066端口，在没有任何报错信息的情况下，连接的是3306上的mysql
```sh
mysql -h localhost -P 8066 -uroot -p --protocol=TCP
```
