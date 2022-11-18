# spring-data-jpa-examples
探索spring-data-jpa的使用



# 技术栈
Spring Boot: 2.3.3.RELEASE

Spring Data Jpa: 2.3.3.RELEASE  (已包含Hibernate5.4.20.Final)

PostgreSQL: 42.2.14

HikariCP: 3.4.5

Hibernate-validator: 6.1.2.Final

querydsl: 4.3.1

Hutool-all: 5.7.22

lombok: 1.18.22

FastJson: 1.2.79

guava: 31.1-jre

com.github.xiaoymin.knife4j-spring-boot-starter: 2.0.9



# 包路径详情

```text
- com.shuishu.demo.jpa
    - common
        - config
            - domain    (基础实体类)
            - exception (全局异常)
            - id        (主键id)
            - jdbc      (jdbc连接)
            - swagger   (swagger公用dto显示隐藏不同字段)
        - domain        (实体)
        - dsl           (querydsl语句)
        - repository    (spring-data-jpa语句)
        - utils         (工具类)
    - config            (配置swagger的接口文档)
    - controller        (访问接口)
    - service           (service接口)
        - impl          (service接口实现)
```



# 知识点

Hibernate 默认情况下查询出来的数据对象时持久态。 
Hibernate 中对象的三种状态：

- 临时状态：通过new新建的对象，这样的对象是没有被持久化的，也不在session缓存中
- 游离状态：已经被持久化，但不在session缓存中
- 持久状态：已经被持久化，也在session缓存中

持久态到游离态的方法有：session.close()、session.evict(obj)、session.clear()
- close()：关闭session，整个session中的持久态对象都成为游离态
- clear()：清楚session中的所有缓存，所有持久化对象变为游离态
- evict(obj)：把某个持久化状态的对象从session中清除，该对象变为游离态



# 测试和报告

## TransactionController （事务、并发）


- 并发更新(不加锁) transaction/concurrence/update/{userId} 
数据正常：querydsl的add方法累加数字，一般情况下的更新操作

- 并发更新(加锁) transaction/concurrence/lock/update/{userId}

  数据正常：querydsl的add方法累加数字，查询的持久态对象加行锁，


- 持久态: 普通查询，set是否更新 transaction/find/{userId}

  数据被更新：普通查询出来的对象是持久态


- 持久态: 并发查询，set是否更新(不加锁) transaction/concurrence/find/{userId}

  数据错误：<font color='red' size="4px">查询不加锁</font>，更新操作在代码层面，从持久态对象中get旧值加一操作，再赋值给持久态。


- 持久态: 并发查询，set是否更新(加锁) transaction/concurrence/lock/find/{userId}

  数据正常：<font color='red' size="4px">查询加锁</font>，更新操作在代码层面，从持久态对象中get旧值加一操作，再赋值给持久态。


- 游离态: 普通查询，set是否更新 transaction/find2/{userId}

  数据没有更新：持久态 转 游离态  `entityManager.unwrap(Session.class).evict(user);` （数据没变化，这样就没必要测试 并发操作了）


- 临时态: 普通查询，set是否更新 transaction/find2/{userId}

  数据没有更新：使用 new 创建新的对象，将查询出来的对象的值（持久态对象）copy 到 新创建的对象中，使用 set 操作更新，观察数据库




## 结论

1、JPA查询到的对象属性被set后，自动执行update

JPA查询后的对象处于持久态，持久态的对象属性在被set后，会自动执行update语句更新数据库。只要把持久态的对象转换为游离态或者是临时态，就可以解决问题。
出现这种现象的前提，查询以及对查询后的实体set值都必须在一个事务里，并且在方法执行结束，事务提交前，不管有没有显示调用update，JPA都会自动调用update。



**解决方案：**

1）隔离Entity：new一个对象，把查询出来的对象通过 BeanUtils.copyProperties 赋值给new出来的对象，更新操作使用new之后的对象

2）把持久化状态的obj从session中清除

引入EntityManager

@PersistenceContext：javax.persistence.PersistenceContext

entityManager：javax.persistence.EntityManager

```text
@PersistenceContext
private EntityManager entityManager;
```


把查询出来的实体对象，从 session 中清除

Session：org.hibernate.Session

user：业务实体对象（查询出来的对象）

```text
entityManager.unwrap(Session.class).evict(user);
```



2、持久态对象 set 操作之后，更新SQL的执行时间

- 只有再次查询表数据时，会先执行跟新语句。
- 当前事务结束





## QueryDslController  （QueryDsl的高级应用）

### querydsl

官网：http://querydsl.com/ 

GitHub：https://github.com/querydsl/querydsl 

当前官网版本：5.0.0 ,  4.4.0  ,  4.3.1  ,  4.2.2  ,  4.1.4

示例工程版本：4.3.1

























