# Eureka高可用性

### 概念

> `Eureka`通过“伙伴”机制实现高可用。每一台`Eureka`都需要在配置中指定另一个`Eureka`的地址作为伙伴，`Eureka`启动时会向自己的伙伴节点获取当前已经存在的注册列表， 这样在向`Eureka`集群中新加机器时就不需要担心注册列表不完整的问题。

> 除此之外，`Eureka`还支持`Region`和`Zone`的概念。其中一个`Region`可以包含多个`Zone`。`Eureka`在启动时需要指定一个`Zone`名，即当前`Eureka`属于哪个`zone`, 如果不指定则属于`defaultZone`。`Eureka Client`也需要指定`Zone`, `Client`(当与`Ribbon`配置使用时)在向Server获取注册列表时会优先向自己`Zone`的`Eureka`发请求，如果自己`Zone`中的`Eureka`全挂了才会尝试向其它`Zone`。`Region`和`Zone`可以对应于现实中的大区和机房，如在华北地区有10个机房，在华南地区有20个机房，那么分别为`Eureka`指定合理的`Region`和`Zone`能有效避免跨机房调用，同时一个地区的`Eureka`坏掉不会导致整个该地区的服务都不可用。

参考：
1. [微服务基础设施之服务注册中心: Spring Cloud Eureka](http://blog.csdn.net/neosmith/article/details/52181450)
2. [Eureka源码分析：Eureka不会进行二次Replication的原因](http://blog.csdn.net/neosmith/article/details/52912645)

### 部署



参考：[Spring Cloud构建微服务架构（六）高可用服务注册中心](http://blog.didispace.com/springcloud6/) 