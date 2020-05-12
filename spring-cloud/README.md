# 架构概况

![Spring Cloud Netflix Demo Project Architecture Overview](https://richie-leo.github.io/ydres/img/10/180/1014/spring-cloud-demo-architecture.jpg)

* **Eureka Server: 注册中心** <br />
  `Spring Cloud Netflix`的微服务注册中心，提供服务注册、发现功能，主要工作机制如下：
  1. 服务启动时，通过`Eureka Client`将自己注册到`Eureka Server`。<br />
     主要注册信息包括主机名、端口号、服务名，这样，注册中心就维护了一份所有服务可用实例清单。
  2. 服务的客户端启动时，通过`Eureka Client`向注册中心获取服务实例清单，注册中心将被请求服务所有可用实例返回给该客户端。
  3. 客户端在本地缓存服务实例清单，每次调用服务方法时，使用`Ribbon`客户端负载均衡确定调用哪一个服务实例，直接与这个服务实例通讯。服务调用过程不经过注册中心。
  4. 每个服务实例启动之后，每30秒（默认）向注册中心发送一次心跳服务，证明自己仍然是可用状态。<br />
     如果注册中心在一定时间内没有收到某个服务实例的心跳信息，则注册中心会将该服务标记为不可用状态，并且会将该服务实例的状态同步给注册中心集群内的其它节点。
  5. 每个客户端实例启动后，同样每30秒（默认）向注册中心发送一次请求，获取最新的可用服务实例清单。<br />
     这样可以确保如果某个服务实例下线或者故障停机，每个客户端能够及时刷新这些状态，`Ribbon`客户端负载均衡时不会再将请求分配给已下线或停机的服务实例。

* **Zuul Server: 服务网关** <br />
  服务网关主要作用是代理、路由，主要工作机制如下：
  1. 不使用服务网关时，服务的客户端使用服务名称直接调用服务；
  2. 使用服务网关后，服务的客户端将请求发送给Zuul网关服务，由网关服务网将调用请求路由给服务端，将请求执行结果返回给客户端；
  
  Zuul服务在服务客户端和服务端之间加入了一个中间层，它不参与任何业务逻辑处理，只是在中间发挥路由转发作用，可以用于以下一些用途：
  
  1. 可以在Zuul服务上添加非业务逻辑控制，例如统一的安全认证、权限管理、性能和故障监控、流量管控等，避免每个微服务都需要各自实现这些公共功能；
  2. 客户端在调用服务时，不再使用目标服务名称，而是使用Zuul服务名。对于客户端来说，Zuul服务就是服务提供者，无需关心其背后会路由给谁。这样，Zuul服务可以将大量的微服务整合起来，逻辑上形成一个大的服务，这对于某些任务，例如统一规范对外的REST服务路径等，比较方便；
  3. Zuul代理还可以用于将采用微服务架构开发的新应用，与未采用微服务架构的老应用集成起来。

* **Hystrix Dashboard: Hystrix监控仪表盘** <br />
  `Hystrix`是`Spring Cloud Netflix`中的断路器。考虑下面2个典型的场景：
  1. 前面提到，某服务一段时间没有给注册中心发送心跳信息，注册中心会将该服务标记为不可用状态。而客户端也需要间隔一定时间（默认30秒）重新向注册中心获取服务实例清单时，才能知道该服务实例不可用。在此期间，客户端仍然会不断将请求发送给这个服务实例，有可能遇到调用出错的情况。
  2. 某个服务短暂性出现压力过载、网络延迟过大等现象，无法响应，但客户端仍然会不断将请求发送过去，在这个点上阻塞等待，这样可能导致被阻塞的请求迅速扩散，从而造成整个服务体系的雪崩效应。
  
  为了避免发生雪崩效应，`Spring Cloud Netflix`提供了`Hystrix`断路器，其作用类似电路的保险丝，当电流过载时进行熔断保护，避免更大的灾难。<br />
  `Hystrix`的工作机制是将每个请求统一封装成`HystrixCommand`，在`HystrixCommand`基础上提供熔断保护、性能监控等控制。
  
  启用了`Hystrix`的REST应用，可以通过`/hystrix.stream`这个API获取到`Hystrix`的性能监控指标。而`Hystrix Dashboard`是`Spring Cloud`中图形化方式查看`Hystrix`性能统计指标的一个应用，参考本文后面部分。

* **DemoService: 演示用微服务** <br />
  类似Hello World的一个演示用微服务：客户端向服务端发送一个Ping消息，服务端向客户端回应一个Pong消息。

* **OrderService: 演示用微服务** <br />
  一个比较典型的演示用微服务：
  1. 使用Spring Cloud方式简单演示订单的创建、更新、获取、查询功能；
  2. 使用MySQL数据库，mybatis数据访问，简单演示在Spring Cloud中如何通过mybatis进行各种数据库操作；

# 启动运行

### 前期准备
本演示项目使用的开发环境：`Mac OSX`、`JDK 1.8`

从github下载演示项目代码：
```shell
git clone https://github.com/liuzhibin-cn/research.git ./
```

建立演示数据库：

1. 在`MySQL`数据库中使用`spring-cloud/order-service-impl/src/main/resources/db.sql`脚本建立演示数据库和表；
2. 修改`spring-cloud/order-service-impl/src/main/resources/application.yml`JDBC配置，包括`数据库名`、`账号`、`密码`；

进入`spring-cloud/order-service-client`目录，执行下面命令将`OrderService`客户端和服务端共享的jar包安装到本地maven仓库：
```shell
mvn install
```

### 启动Eureka注册中心
项目演示了使用2个erueka server建立高可用注册中心。<br />
进入`spring-cloud/eureka-server`目录，执行下面命令编译打包：
```shell
mvn package
```
启动第1个注册中心：
```shell
java -jar target/spring-cloud-eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1
```
启动第2个注册中心：
```shell
java -jar target/spring-cloud-eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2
```

*注意：2个注册中心通过互相注册同步复制的方式提供高可用性，在启动第1个注册中心时因为第2个注册中心还没有启动，因此会有一些报错信息，等第2个注册中心启动完成后就恢复正常了。*

### 启动其它服务
除了注册中心之外，演示项目中的其它所有服务和应用都通过下面maven命令启动：
```shell
mvn spring-boot:run
```
依次进入`demo-service`、`order-service-impl`、`zuul-server`、`client-app`、`hystrix-dashboard`目录，执行上述命令启动相关服务和应用。<br />
每次启动一个服务或应用，都必须新开一个shell终端、命令行窗口。

# 功能演示

### 访问注册中心
通过[http://localhost:9001](http://localhost:9001)或者[http://localhost:9002](http://localhost:9002)访问注册中心，可以看到各服务和应用的注册状态：<br />
![Eureka Server Status](https://richie-leo.github.io/ydres/img/10/180/1014/eureka-status.png)

### 直接访问微服务
Demo服务：[http://localhost:10200/ping?msg=Ping](http://localhost:10200/ping?msg=Ping)。在浏览器可以看到服务返回消息，在Demo服务的启动窗口可以看到服务端的日志输出。<br />
Order服务：[http://localhost:10100/order/find?status=New](http://localhost:10100/order/find?status=New)，此时浏览器没有输出内容，因为我们还没有创建任何订单，后面创建好订单后再访问该地址，浏览器会以JSON格式输出查询结果。

### 访问客户端应用
浏览器打开[http://localhost:12000](http://localhost:12000)可以看到客户端功能演示用的全部URL清单。<br />
其中，URL包含`no-zuul`的，客户端应用直接调用微服务接口，不通过网关服务；URL包含`via-zuul`的，客户端通过网关服务调用微服务接口，即客户端应用以`HTTP REST`形式向`zuul`网关服务发送请求，网关服务根据规则将请求路由到Demo服务或Order服务（同样使用`HTTP REST`形式）。

1. `/demo/ping?msg=Ping`: 客户端应用向`DemoService`发送一个`Ping`消息（`msg=Ping`为发送的消息内容），`DemoService`返回一个`Pong`消息。
2. `/demo/benchmark`: 简单的性能测试。客户端应用分3轮调用`DemoService`的`ping`接口，每轮调用5000次，执行完成后在浏览器中显示平均执行时间。注意：该URL执行完毕需用时30-40秒左右。
3. `/order/create`: 客户端应用调用微服务`OrderService`的创建订单接口，随机创建一个订单。
4. `/order/find?status=New`: 客户端应用调用微服务`OrderService`的订单查询接口，返回指定状态的所有订单列表。可选的状态值为：`New`、`Confirmed`、`Shipped`、`Canceled`、`Closed`。
5. `/order/get/1`: 客户端应用调用微服务`OrderService`的订单获取接口，返回订单详情。URL最后一位数字为订单ID。
6. `/order/update/1?status=Close`: 客户端应用调用微服务`OrderService`的订单状态更新接口，通过URL指定订单ID和要更新的目标状态值。

# Hystrix Dashboard 监控服务
浏览器打开[http://localhost:9300/hystrix](http://localhost:9300/hystrix)，界面显示如下：<br />
![Hystrix Initial Screen](https://richie-leo.github.io/ydres/img/10/180/1014/hystrix-dashboard-1.png)

-------------------------------------
在输入框中输入：`http://localhost:12000/hystrix.stream`，点击`Monitor Stream`按钮。<br />
如果是客户端应用刚启动，还未执行过任何请求，则界面显示如下（长时间处于Loading状态，或者报错无法连接上）：<br />
![Hystrix Loading Screen](https://richie-leo.github.io/ydres/img/10/180/1014/hystrix-dashboard-2.png)

-------------------------------------
访问几次客户端应用的演示功能，刷新`Hystrix Dashboard`页面，则会显示监控内容。可以开启另外一个浏览器执行`benchmark`演示功能，观察`Hystrix Dashboard`的监控情况：<br />
![Hystrix Monitor Screen](https://richie-leo.github.io/ydres/img/10/180/1014/hystrix-dashboard-3.png)

# 高可用部署方案说明

### 微服务高可用性部署方案
任何一个微服务都可以部署多个实例，上面示例中的`DemoService`、`OrderService`不需要做任何更改，即可实现在新的机器上部署新的实例（本示例中要求服务器安装`JDK1.8`）：

* **DemoService** <br />
  按上面方法从github下载项目源代码；<br />
  使用下面maven命令编译、启动新的`DemoService`服务实例：
  ```shell
  cd spring-cloud/demo-service
  mvn spring-boot:run
  ```

* **OrderService** <br />
  按上面方法从github下载项目源代码；<br />
  使用下面maven命令将`spring-cloud-order-service-client`安装到本地maven仓库：
  ```shell
  cd spring-cloud/order-service-client
  mvn install
  ```
  使用下面maven命令编译、启动新的`OrderService`服务实例：
  ```shell
  cd spring-cloud/order-service-impl
  mvn spring-boot:run
  ```
  
  可以使用上面方法为`DemoService`、`OrderService`部署N个新的实例。

### Zuul Server高可用性部署方案
`Zuul Server`本身也是一个普通的微服务，其高可用性部署方案与上面微服务的高可用性部署方案完全相同：

按上面方法从github下载项目源代码；<br />
使用下面maven命令编译、启动新的`Zuul Server`实例：
```shell
cd spring-cloud/zuul-server
mvn spring-boot:run
```

### Eureka Server高可用性部署方案
在本示例中，已经配置了2个Eureka注册中心组成一个集群：
* 集群中的2个节点会互相同步复制，任何一个服务，在任意一个注册中心注册的信息，都会同步复制到另外一个注册中心节点上，因此客户端无论连接哪个注册中心节点，都能获取到完整的服务实例清单；
* 示例中的每个服务、客户端，都使用下面方式指定注册中心，当其中任何一个注册中心节点故障停机，都会自动使用另外一个注册中心节点。

  ```yaml
  eureka:
    client:
        serviceUrl:
            defaultZone: http://localhost:9001/eureka/,http://localhost:9002/eureka/
  ```
  
可以参考[Spring Cloud构建微服务架构（六）高可用服务注册中心](http://blog.didispace.com/springcloud6/)，像下面图示一样部署3个或更多个Eureka Server实例组成的高可用集群：

![Eureka Server高可用方案](https://richie-leo.github.io/ydres/img/10/180/1014/eureka-ha.png)

# 示例项目部分功能介绍，及一些关键配置说明

Spring Boot主张“约定优于配置”，很多组件和框架非常简单容易就可以使用起来，隐藏在背后的是Spring Boot使用了很多默认配置，而对于一些关键配置参数，采用默认配置可能根本不适合生产环境。

### Feign：优秀的HTTP REST服务客户端

本示例项目中使用了Feign作为HTTP REST服务的客户端调用工具，Feign是一个非常优秀、便利的HTTP调用组件。

示例项目中`OrderService`提供了一个创建订单的REST接口：<br />
接口地址：`/order/create`<br />
HTTP方法：`POST`<br />
所需参数：一个`OrderDTO`对象，必须采用JSON序列化，通过`RequestBody`方式提交<br />

如果使用`httpclient`，我们有不少必须工作：
* 初始化创建合适的`HttpConnectionManager`对象，设置并发链接数、超时时间等；
* 创建`HttpClient`对象；
* 使用`OrderDTO`对象创建`HttpEntity`，使用`HttpClient`对象执行请求；
* 解析`HttpResponse`，获得调用结果；

使用Feign完成上面的HTTP接口调用工作非常简单，我们只需要定义一个接口：
```java
@RequestMapping(value="/order")
@FeignClient(name="order-service", configuration=FeignConfiguration.class)
public interface OrderRestServiceFeignClient {
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    int createOrder(@RequestBody OrderDTO order);
}
```

在Spring Boot的启动主类上添加下面注解，让Spring容器启动时创建这个FeignClient的Bean实例：
```java
@EnableFeignClients(basePackages="org.liuzhibin.research.feign")
```

然后在代码中就可以直接调用这个接口方法了：
```java
@Autowired
OrderRestServiceFeignClient orderServiceClient;

public OrderDTO create() {
    OrderDTO order = new OrderDTO();
    ...
    int orderId = orderServiceClient.createOrder(order);
    ...
}
```

### 为Feign选择HTTP客户端

Feign支持`HttpClient`、`OkHttp`、`URLHttpConnection`等，默认情况下将使用`URLHttpConnection`来执行HTTP请求。<br />
`HttpClient`提供连接池、并发链接数等管理，功能比较丰富，建议在Spring Cloud架构中采用`HttpClient`。`OkHttp`是一个新的HTTP客户端组件，支持一些新的HTTP特性，目前阶段建议持续观察。

指定使用`HttpClient`方法如下：

`pom.xml`
```xml
<dependency>
    <groupId>com.netflix.feign</groupId>
    <artifactId>feign-httpclient</artifactId>
    <version>8.18.0</version>
</dependency>
<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>httpclient</artifactId>
</dependency>
```

`application.yml`
```yaml
feign.httpclient.enabled: true
```

### HTTP连接超时、执行超时设置
默认情况下FeignClient的连接超时时间为10秒、执行超时时间为60秒，可以为上下文提供一个`feign.Request.Options`的bean更改默认设置：

```java
 @Configuration
 public class FeignConfiguration {
    @Bean
    Request.Options feignOptions() {
        return new Options(3 * 1000, 3 * 60 * 1000); //连接超时设置为3秒，执行超时3分钟
    }
 }
```

不管使用哪种HTTP客户端，Feign在执行请求时，都会将这2个参数设置到相应的客户端上。

Hystrix的超时时间可以通过`application.yml`设置，例如：

```yaml
hystrix:
    command:
        default:
            execution:
                isolation:
                    thread:
                        timeoutInMilliseconds: 60000
```

### HTTP错误重试

默认情况下，有几个地方会涉及到HTTP错误重试（发生HTTP调用错误时，自动重试几次）。

* **HttpClient** <br />
  默认使用`DefaultHttpRequestRetryHandler(3, false)`：如果请求已经成功发送给服务器，则不会重试，在请求未发送给服务器的情况下，会自动重试3次。这样的默认处理，可以尽可能避免偶然的HTTP错误导致调用失败，又避免对不支持幂等性的服务接口重试调用。<br />
  Feign默认创建的`HttpClient`未设置任何参数，全部使用`HttpClient`的默认值，但是它会先尝试从上下文中获取`HttpClient`的bean。下面方式可以为Feign提供自定义的`HttpClient` Bean（对任何http错误请求自动重试1次）：

 ```java
 @Configuration
 public class FeignConfiguration {
    private HttpClient httpClient;
    
    /**
     * FeignClient使用默认方式创建HttpClient对象，未设置MaxConnPerRoute、MaxConnTotal属性。
     * @return
     */
    @Bean
    HttpClient buildHttpClient() { 
        if(httpClient!=null) return httpClient;
        synchronized (this) {
            if(httpClient!=null) return httpClient;
            httpClient = HttpClientBuilder.create()
                .setMaxConnPerRoute(15).setMaxConnTotal(30) //并发连接数
                .setConnectionManagerShared(false)
                 //第2个参数，表示如果request已经成功发送给服务器，是否仍然可以重试
                 //支持幂等性的请求可以将第2个参数设为true
                .setRetryHandler(new DefaultHttpRequestRetryHandler(1, true))
                .disableCookieManagement()
                .disableAuthCaching()
                .build();
        }
        return httpClient; 
    }
 }
 ```

* **Feign** <br />
  Feign本身也实现了重试机制，默认情况下，对出错的http请求自动重试6次，在`feign.Retryer.Default`中实现。<br />
  下面示例代码将禁止Feign自动重试：

 ```java
 @Configuration
 public class FeignConfiguration {
    @Bean
    Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }
 }
 ```
 
  下面示例代码表示：对出错http请求自动重试3次，第一次间隔50毫秒重试，后续重试将逐级增加间隔时间，最大间隔时间500毫秒。
  
 ```java
 @Configuration
 public class FeignConfiguration {
    @Bean
    Retryer feignRetryer() {
        return new Retryer.Default(50, 500, 3);
    }
 }
 ```

> **注意**：上面是在Spring Boot应用中单独使用Feign，未和Spring Cloud（包括Ribbon、Hystrix、Zuul等）结合使用。如果和这些组件结合使用，需查看它们是否重新封装提供了默认配置，具体参考这些组件源码。

# 参考

1. [Spring Cloud Netflix Reference](http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html)
2. [Netflix Hystrix](https://github.com/Netflix/Hystrix)、[Netflix Hystrix Wiki](https://github.com/Netflix/Hystrix/wiki)
3. [Netflix Zuul](https://github.com/Netflix/zuul)、[Netflix Zuul Wiki](https://github.com/Netflix/zuul/wiki)
4. [Netflix Eureka Wiki](https://github.com/Netflix/eureka/wiki)
5. [Netflix Ribbon](https://github.com/Netflix/ribbon)、[Netflix Ribbon Wiki](https://github.com/Netflix/ribbon/wiki)
6. [实施微服务，我们需要哪些基础框架？](http://blog.csdn.net/neosmith/article/details/52118930)、[使用Spring Cloud Netflix技术栈实施微服务架构](http://blog.csdn.net/neosmith/article/details/52204113)
7. [Eureka! Why You Shouldn’t Use ZooKeeper for Service Discovery](https://tech.knewton.com/blog/2014/12/eureka-shouldnt-use-zookeeper-service-discovery/)、[为什么不要把ZooKeeper用于服务发现](http://www.infoq.com/cn/news/2014/12/zookeeper-service-finding/)
8. [Open-Source Service Discovery](http://jasonwilder.com/blog/2014/02/04/service-discovery-in-the-cloud/)、[开源的服务发现项目Zookeeper，Doozer，Etcd](http://blog.csdn.net/shlazww/article/details/38736511)
9. Spring Cloud构建微服务架构：[（一）服务注册与发现](http://blog.didispace.com/springcloud1/)、[（二）服务消费者](http://blog.didispace.com/springcloud2/)、[（三）断路器](http://blog.didispace.com/springcloud3/)、[（四）分布式配置中心](http://blog.didispace.com/springcloud4/)、[（四）分布式配置中心（续）](http://blog.didispace.com/springcloud4-2/)、[（五）服务网关](http://blog.didispace.com/springcloud5/)、[（六）高可用服务注册中心](http://blog.didispace.com/springcloud6/)、[（七）消息总线](http://blog.didispace.com/springcloud7/) 
10. [Spring Boot Profiles](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html)、[Spring Boot Profile-specific properties](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config-profile-specific-properties)
11. [SpringBoot配置属性之MVC](https://segmentfault.com/a/1190000004315890) 
12. [Spring Boot多数据源配置与使用](http://www.jianshu.com/p/34730e595a8c)
