# 项目说明

使用`Spring Boot`开发管理项目的简单演示

1. 演示使用`Spring Boot`建立web项目；
2. 演示在`Spring Boot`环境下使用`MyBatis`访问`MySQL`数据库，执行增删改查操作；

# 关于Spring Boot

`Spring Boot`的优点：

1. **减少XML配置，提升开发效率** <br />
   传统方式使用`Spring`时，重度依赖XML配置文件，开发人员在项目初始化、编码过程中需要花费不少精力维护这些XML。<br />
   `Spring Boot`极大简化了`Spring`的使用方式，使用`Spring Boot`可以快速启动项目，编码过程中减少了很多细节关注点，使开发人员可以更多精力集中在业务逻辑的实现上。
2. **提供了一种新的、更方便使用的、基于Spring的“开箱即用”式组件集成方式**<br />
   基于整体开发过程来看，`Spring`在底层**编码、运行时**层级，提供**机制（IoC等）**和**组件（MVC等）**支持，供开发者灵活选择使用。<br />
   **开箱即用**意味着对于使用者来说更简单、方便，但对于组件提供者来说，也意味着需要做更多的封装工作，提供更多的缺省默认配置。`Spring Boot`提供了一个很好的、统一规范的方式实现这个目的，可以将`Spring Boot`中的各种`starter`称之为**面向开发者的微服务**。<br />
   `Spring Boot`使得Java开发在某种程度上拥有了类似`Ruby on Rails`的开发体验。
3. **为项目打包、部署方面提供支持** <br />
   基于`Maven`、`Ant`、`Shell`等工具完全可以实现自动化编译、打包、部署工具，但自行实现需要花费一些精力，也存在不规范、不统一的弊端，例如项目A和项目B、公司A和公司B差异很大。<br />
   `Spring Boot`使得编译打包，部署测试、生产环境变得更容易，有助于为Java应用的测试、发布提供一种行业标准化方式。

使用`Spring Boot`的注意事项：

1. 需要注意`Spring`本身已经发展成为一个庞大的体系，`Spring Boot`同样对自家的和行业中常用的组件提供了集成支持，例如`Hibernate`、`Spring ORM`等基于`JPA`的数据持久化方案、`Spring Security`等等。<br />
   在使用`Spring Boot`时需要注意**按需选择**，并不是`Spring Boot`能够提供的，都是适合你的。
2. 目前某些组件不提供`Spring Boot`集成支持，需要自己实现。<br />
   非特殊情况下，简单集成`Spring Boot`是比较容易的。

# 运行演示项目

### 下载代码
在shell下执行下面命令，将github中的项目代码克隆到你的本地目录中：

```shell
git clone https://github.com/liuzhibin-cn/research.git ./
```

### 配置数据库

1. 在`MySQL`中建立演示数据库`demo_db`；
2. 使用下面脚本创建演示用的表：

    ```sql
    CREATE TABLE `demo_db`.`sys_user` (
      `usr_id` INT NOT NULL AUTO_INCREMENT,
      `usr_name` VARCHAR(30) NOT NULL DEFAULT '',
      `create_time` DATETIME NOT NULL DEFAULT current_timestamp,
      PRIMARY KEY (`usr_id`));
    ```
    
3. 编辑`src/main/resources/application.yml`文件，修改其中的数据库连接信息，包括数据库名、用户、密码等

### 使用maven插件运行

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
   
### 在Eclipse中运行

1. 打开Eclipse菜单：`File -> Import`
2. 在对话框中选择：`Maven -> Existing Maven Projects`
3. 下一步，项目根目录选择`spring-boot`目录，确定
4. 在Eclipse中直接 `Run As -> Java Application` 启动即可

### 打包后运行

在`spring-boot`目录中执行下面命令，启动web项目：

```shell
mvn package
java -jar target/demo-spring-boot-0.0.1-SNAPSHOT.jar
```

# 参考：
[Spring Boot Reference Guide](http://docs.spring.io/spring-boot/docs/current/reference/html/index.html)