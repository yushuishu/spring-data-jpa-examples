# spring-data-jpa-examples
探索spring-data-jpa的使用

<br>
<br>

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

<br>
<br>

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

<br>
<br>

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

<br>
<br>

# 测试

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


<br>

### 结论

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


<br>
<br>

## QueryDslController  （QueryDsl使用）

### querydsl

官网：http://querydsl.com/ 

GitHub：https://github.com/querydsl/querydsl 

当前官网版本：5.0.0 ,  4.4.0  ,  4.3.1  ,  4.2.2  ,  4.1.4

示例工程版本：4.3.1

<br>

### 空表数据查询

`url：querydsl/department/list`

查看`queryDsl`和`repository` 查询空数据时，返回的结果 null 还是对象

<br>

### 等于某个值的-结果集

`url：：querydsl/eq/list`

查询集合，条件使用等于eq 方法
<br>
其它常用方法：

     - eq() ：等于
     - ne() ：不等于
     - gt   ：大于
     - goe  ：大于等于
     - lt   ：小于
     - loe  ：小于等于

<br>

### 等于某个值的-单个对象

`url ：querydsl/eq/find`

查询当个对象值，条件使用等于eq 方法

<br>

### 条件封装的使用(BooleanBuilder)

`url ：querydsl/booleanBuilder/find`

封装条件的函数：
<br>
&emsp;&emsp;1、解决条件多，代码长
<br>
&emsp;&emsp;2、条件逻辑判断（如：非空判断），方便的灵活使用查询条件

<br>

### 模糊分页的用法-任意位置

`url ：querydsl/like/page`

关键字`keyword`模糊匹配查询，使用`like`方法，也是SQL中的`like`函数

<br>

### 模糊检索的用法2-开始位置

`url ：querydsl/startsWith/list`

关键字`keyword`模糊匹配查询，条件使用`startsWith`方法，表示指定字段值，必须以`keyword`开头

<br>

### 模糊检索的用法3-区间

`url ：querydsl/between/list`

使用示例：`qLibraryCollection.createTime.between(startDate, endDate)`

比如条件时间区间，确定开始时间和结束时间是必须的查询条件，这时候可以使用`between`方法，
否则只能通过逻辑判断（非空判断），使用`goe`或`loe`方法，最后将时间条件放入到`booleanBuilder`方法中，

<br>

### SQL中的语法IN

`url ：querydsl/in/list`

和`eq`等方法的使用一样简单：`qUser.userAge.in(userAgeList)`

<br>

### 聚合函数-groupBy

`url ：querydsl/groupBy/list`

聚合函数：分组

<br>

### 聚合函数-avg()

`url ：querydsl/user/avg/find`

聚合函数：平均值

<br>

### 聚合函数-sum()

`url ：querydsl/user/sum/find`

聚合函数：字段值求和

<br>

### 聚合函数-concat()

`url ：querydsl/user/concat/find`

聚合函数：多列字段值拼接

`qUser.userName.concat(":").concat(qUser.userAge.stringValue()).as("nameConcatAge")`

结果：`张三:18`

<br>

### 聚合函数-contains()

`url ：querydsl/user/contains/find`

聚合函数：字段的值必须包含指定的值

<br>

### 聚合函数-DATE_FORMAT()

`url ：querydsl/user/date_format/find`

时间处理：`DATE_FORMAT({0},'%Y-%m-%d')`

<br>

### stringTemplate字符模板

`url ：querydsl/stringTemplate/use/list`

<br>

### CASE...WHEN...THEN...

`url ：querydsl/stringExpression/list`

```sql
SELECT
CASE
		
	WHEN SUBSTRING
		( librarycol0_.book_call_number, 1, 1 ) IN ( ? , ? , ? ) THEN
			SUBSTRING ( librarycol0_.book_call_number, 1, 1 ) ELSE'其它' 
			END AS col_0_0_,
		COUNT ( librarycol0_.library_collection_id ) AS col_1_0_ 
	FROM
		ss_library_collection librarycol0_ 
	GROUP BY
		SUBSTRING ( librarycol0_.book_call_number, 1, 1 ) 
ORDER BY
	COUNT ( librarycol0_.library_collection_id ) DESC
```

<br>

### 多表连接查询

`url ：querydsl/multi/table/join/list`

leftJoin().on()  ：左连接

rightJoin().on() ：右连接

innerJoin().on() ：内连接

<br>

### 一对多

`url ：querydsl/one_to_many/list`

一对多查询，qProduct主表（产品表） qOrder从表（订单表）。
通过SQL内连接的查询方式，一次性的查询出所有数据，再通过使用`querydsl`的内置函数处理`笛卡尔积`，避免通过业务层逻辑（分组操作或多次执行查询）处理各个产品的所有订单数据

查询示例结果
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "productId": "1",
      "productName": "手机",
      "productDescription": "智能5G手机",
      "productCount": 500,
      "productPrice": 5000,
      "specification": "台",
      "orderList": [
        {
          "createTime": null,
          "createUserId": null,
          "updateTime": null,
          "updateUserId": null,
          "orderId": "1",
          "uid": "123123423",
          "productId": "1",
          "userName": "张三",
          "productName": "手机",
          "price": 50000,
          "count": "10",
          "status": 1
        },
        {
          "createTime": null,
          "createUserId": null,
          "updateTime": null,
          "updateUserId": null,
          "orderId": "2",
          "uid": "2354634456",
          "productId": "1",
          "userName": "李四",
          "productName": "手机",
          "price": 5000,
          "count": "1",
          "status": 2
        }
      ]
    },
    {
      "productId": "2",
      "productName": "牛肉",
      "productDescription": "内蒙古牛肉",
      "productCount": 2500,
      "productPrice": 80,
      "specification": "200g",
      "orderList": [
        {
          "createTime": null,
          "createUserId": null,
          "updateTime": null,
          "updateUserId": null,
          "orderId": "3",
          "uid": "67324535",
          "productId": "2",
          "userName": "张三",
          "productName": "牛肉",
          "price": 800,
          "count": "10",
          "status": 2
        }
      ]
    }
  ]
}
```

<br>

### 一对多(条件筛选并分页)

`url ：querydsl/one_to_many/condition/page`

一对多查询，qProduct主表（产品表） qOrder从表（订单表）。
在上一个接口示例查询的基础上，增加分页操作。

这里主要关注的是分页中的总数数据查询，因为内连接会产生笛卡尔积，所以需要去重。需要注意是builder的条件都是qProduct主表的字段条件


<br>
<hr>

<p><span style="float:right;">2022-12-17</span></p>


