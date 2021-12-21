## 1. 系统架构演变概述

**目标**：了解项目架构的演变历程

**小结**：

```mermaid
graph LR;
1[集中式架构] --> 2[垂直拆分]
2 --> 3[分布式服务]
3 --> 4[SOA面向服务架构]
4 --> 5[微服务架构]
```



## 2. 微服务架构说明

**目标**：了解SOA与微服务架构的区别以及说出微服务架构的特点

**分析**：

SOA使用了ESB组件的面向服务架构：ESB自身实现复杂；应用服务粒度较大，所有服务之间的通信都经过ESB会降低通信速度；部署、测试ESB比较麻烦。

**小结**：

**微服务架构**：是一套使用小服务或者单一业务来开发单个应用的方式或途径。

微服务架构特点：

- 单一职责
- 服务粒度小
- 面向服务（对外暴露REST api）
- 服务之间相互独立

与使用ESB的SOA架构的区别：微服务架构没有使用ESB，有服务治理注册中心；业务粒度小。



## 3. 服务调用方式说明

**目标**：能够说出服务调用方式种类

**小结**：

- RPC：基于socket，速度快，效率高；webservice、dubbo
- HTTP：基于TCP，封装比较臃肿；对服务和调用方没有任何技术、语言的限定，自由灵活；RESTful，Spring Cloud

## 4. Spring RestTemplate示例工程导入

**目标**：了解Spring RestTemplate的应用

**分析**：

一般情况下有如下三种http客户端工具类包都可以方便的进行http服务调用：

- httpClient
- okHttp
- JDK原生URLConnection

spring 提供了RestTemplate的工具类对上述的3种http客户端工具类进行了封装，可在spring项目中使用RestTemplate进行服务调用。

**小结**：

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test(){
        String url = "http://localhost/user/8";
        //restTemplate可以对json格式字符串进行反序列化
        User user = restTemplate.getForObject(url, User.class);
        System.out.println(user);
    }
}

```



## 5. Spring Cloud概述

**目标**：Spring Cloud整合的组件和版本特征

**小结**：

- 整合的组件可以有很多组件；常见的组件有：eureka注册中心，Gateway网关，Ribbon负载均衡，Feign服务调用，Hystrix熔断器。在有需要的时候项目添加对于的启动器依赖即可。
- 版本特征：以英文单词命名（伦敦地铁站名）

## 6. 创建微服务工程

**目标**：创建微服务父工程heima-springcloud、用户服务工程user-service、服务消费工程consumer-demo

**分析**：

需求：查询数据库中的用户数据并输出到浏览器

- 父工程heima-springcloud：添加spring boot父坐标和管理其它组件的依赖
- 用户服务工程user-service：整合mybatis查询数据库中用户数据；提供查询用户服务
- 服务消费工程consumer-demo：利用查询用户服务获取用户数据并输出到浏览器

**小结**：

```xml
            <!-- springCloud -->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

```

通过 `scope` 的import可以继承 `spring-cloud-dependencies` 工程中的依赖

## 7. 搭建配置user-service工程

**目标**：配置user-service工程并能够根据用户id查询数据库中用户

**分析**：

需求：可以访问http://localhost:9091/user/8输出用户数据

实现步骤：

1. 添加启动器依赖（web、通用Mapper）；
2. 创建启动引导类和配置文件；
3. 修改配置文件中的参数；
4. 编写测试代码（UserMapper，UserService，UserController）；
5. 测试

**小结**：

- 添加启动器依赖

```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- 通用Mapper启动器 -->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
        </dependency>
        <!-- mysql驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

    </dependencies>

```



- 编写配置文件

```yml
server:
  port: 9091
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/springcloud
    username: root
    password: root

mybatis:
  type-aliases-package: com.itheima.user.pojo

```



## 8. 搭建配置consumer-demo工程

**目标**：编写测试类使用restTemplate访问user-service的路径根据id查询用户

**分析**：

需求：访问http://localhost:8080/consumer/8 使用RestTemplate获取http://localhost:9091/user/8的数据

实现步骤：

1. 添加启动器依赖；
2. 创建启动引导类（注册RestTemplate）和配置文件；
3. 编写测试代码（ConsumerController中使用restTemplate访问服务获取数据）
4. 测试

**小结**：

- 服务管理
  如何自动注册和发现
  如何实现状态监管
  如何实现动态路由
- 服务如何实现负载均衡
- 服务如何解决容灾问题
- 服务如何实现统一配置

上述的问题都可以通过Spring Cloud的各种组件解决。

## 9. Eureka注册中心说明

**目标**：说出Eureka的主要功能

**小结**：

Eureka的主要功能是进行服务管理，定期检查服务状态，返回服务地址列表。

![1560439174201](assets/1560439174201.png)

## 10. 搭建eureka-server工程

**目标**：添加eureka对应依赖和编写引导类搭建eureka服务并可访问eureka服务界面

**分析**：

Eureka是服务注册中心，只做服务注册；自身并不提供服务也不消费服务。可以搭建web工程使用Eureka，可以使用Spring Boot方式搭建。

搭建步骤：

1. 创建工程；
2. 添加启动器依赖；
3. 编写启动引导类（添加Eureka的服务注解）和配置文件；
4. 修改配置文件（端口，应用名称...）；
5. 启动测试

**小结**：

- 启动器依赖

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
```



- 配置文件

```yml
server:
  port: 10086
spring:
  application:
    name: eureka-server
eureka:
  client:
    service-url:
      # eureka 服务地址，如果是集群的话；需要指定其它集群eureka地址
      defaultZone: http://127.0.0.1:10086/eureka
    # 不注册自己
    register-with-eureka: false
    # 不拉取服务
    fetch-registry: false
```



## 11. 服务注册与发现

**目标**：将user-service的服务注册到eureka并在consumer-demo中可以根据服务名称调用

**分析**：

- 服务注册：在服务提供工程user-service上添加Eureka客户端依赖；自动将服务注册到EurekaServer服务地址列表。
  - 添加依赖；
  - 改造启动引导类；添加开启Eureka客户端发现的注解；
  - 修改配置文件；设置Eureka 服务地址
- 服务发现：在服务消费工程consumer-demo上添加Eureka客户端依赖；可以使用工具类根据服务名称获取对应的服务地址列表。
  - 添加依赖；
  - 改造启动引导类；添加开启Eureka客户端发现的注解；
  - 修改配置文件；设置Eureka 服务地址；
  - 改造处理器类ConsumerController，可以使用工具类DiscoveryClient根据服务名称获取对应服务地址列表。

**小结**：

- 添加Eureka客户端依赖；

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

```

- 添加启动引导类注解；

  ![1560519608668](assets/1560519608668.png)

- 修改配置

```yml
spring:
  application:
    name: consumer-demo
eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:10086/eureka
```

