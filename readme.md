[TOC]

# 1.MyBatis入门

## 1.1 mybatis安装

​	要使用 MyBatis， 只需将 [mybatis-x.x.x.jar](https://github.com/mybatis/mybatis-3/releases) 文件置于 classpath 中即可。

​	如果使用 Maven 来构建项目，则需将下面的 dependency 代码置于 pom.xml 文件中：

```xml
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis</artifactId>
  <version>x.x.x</version>
</dependency>
```

## 1.2 MyBatis概述

-   MyBatis是一个优秀的基于 Java 的持久层框架，它**内部封装了 JDBC**，使开发者**只需关注SQL 语句**本身，而不用再花费精力去处理诸如注册驱动、创建 Connection、配置 Statement
    等繁杂过程。

### MyBatis 与 Hibernate

-   Hibernate
    -   全自动ORM:自动封装数据库,实现了POJO 和数据库表之间的映射，以及 SQL 的自动生成和执行。
-   mybatis
    -   半自动ORM:做好POJO与SQL语句的映射但不会自动生成SQL语句
-   mybatis特点:
    -   在 XML 文件中配置 SQL 语句，实现了 **SQL 语句与代码的分离**，提高程序维护性
    -   程序员自己去编写SQL 语句，程序员可以结合数据库自身的特点灵活控制SQL 语句，因此能够实现比Hibernate等全自动 ORM框架更高的查询效率，能够完成复杂查询。
    -   简单，易于学习，易于使用，上手快。
-   mybatis体系结构
-   ![](D:\04_Java学习笔记\07_mybatis01.jpg)
-   ​

###1.3 mybatis工作原理

-   ![](D:\04_Java学习笔记\07_mybatis02.jpg)

## 1.4 mybatis程序

-   将student信息写入database中

### 1.4.1 基本程序

1.  导入jar包

    1.  mybatis-3.4.5.jar
    2.  mybatis依赖jar包,所有依赖包[官网上有](http://www.mybatis.org/mybatis-3/zh/dependencies.html),先列出必要的
        1.  ognl-3.1.15.jar
        2.  javassist-3.22.0-CR2.jar
        3.  slf4j-api-1.7.25.jar
        4.  slf4j-api-log4j12-1.7.25.jar
        5.  log4j-1.2.17.jar
        6.  commons-logging-1.2.jar
        7.  log4j-core-2.10.0.jar
        8.  log4j-api-2.10.0.jar
        9.  cglib-3.2.5.jar
            1.  cglib的依赖包ant-1.9.6.jar与ant-launcher-1.9.6.jar
        10.  数据库连接池:commons-dbcp-1.4.jar与commons-pool-1.5.4.jar
    3.  测试包junit-4.12.jar
    4.  jdbc包mysql-conncetor-java-5.1.6.jar
    5.  mybatis增强缓存包mybatis-encache-1.0.3.jar
    6.  spring与mybatis整合包mybatis-spring-1.2.2.jar(可选)

    maven中pom.xml配置:

    ```xml
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        </properties>
        
        <dependencies>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>5.1.6</version>
            </dependency>
            <dependency>
                <groupId>org.mybatis</groupId>
                <artifactId>mybatis</artifactId>
                <version>3.4.5</version>
            </dependency>
            <dependency>
                <groupId>ognl</groupId>
                <artifactId>ognl</artifactId>
                <version>3.1.15</version>
            </dependency>
            <dependency>
                <groupId>org.javassist</groupId>
                <artifactId>javassist</artifactId>
                <version>3.22.0-CR2</version>
            </dependency>
            <dependency>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-api</artifactId>
                <version>1.7.25</version>
            </dependency>
            <dependency>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-log4j12</artifactId>
                <version>1.7.25</version>
            </dependency>
            <dependency>
                <groupId>log4j</groupId>
                <artifactId>log4j</artifactId>
                <version>1.2.17</version>
            </dependency>
            <dependency>
                <groupId>org.apache.logging.log4j</groupId>
                <artifactId>log4j-core</artifactId>
                <version>2.10.0</version>
            </dependency>
            <dependency>
                <groupId>org.apache.logging.log4j</groupId>
                <artifactId>log4j-api</artifactId>
                <version>2.10.0</version>
            </dependency>
            <dependency>
                <groupId>commons-logging</groupId>
                <artifactId>commons-logging</artifactId>
                <version>1.2</version>
            </dependency>
            <dependency>
                <groupId>cglib</groupId>
                <artifactId>cglib</artifactId>
                <version>3.2.5</version>
            </dependency>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>4.12</version>
            </dependency>
            <dependency>
                <groupId>commons-dbcp</groupId>
                <artifactId>commons-dbcp</artifactId>
                <version>1.4</version>
            </dependency>

            <dependency>
                <groupId>org.mybatis.caches</groupId>
                <artifactId>mybatis-ehcache</artifactId>
                <version>1.0.3</version>
            </dependency>
        </dependencies>
    ```

2.  定义实体pojo类:Student类

3.  在mysql数据库中生成表结构schema:与student对应

4.  定义dao接口:提供CRUD功能

5.  **定义映射文件**

    -   映射文件简称mapper,一般放在dao包中,格式为xml.如mapper.xml

    -   mapper的约束dtd文件在mybatis核心jar包的org.apache.ibatis.builder.xml中(**mybatis-3-mapper.dtd**),此包中还有mybatis的主配置文件(**mybatis-3-config.dtd**)

    -   映射文件头

        -   ```xml
            <?xml version="1.0" encoding="UTF-8" ?>
            <!DOCTYPE mapper
              PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
              "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
            ```

        -   ​

    -   映射文件的属性

        -   mapper标签:根元素

            -   namespace属性:每个mapper的ns必须不同,此属性用于区分不同mapper的同名的sql映射id

            -   insert标签:内是SQL插入语句

                -   id属性:在命名空间中唯一的标识符，可以被用来引用这条语句。
                -   parameterType:将会传入这条语句的参数类的完全限定名或别名。

            -   ```xml
                <mapper namespace="studentdao">
                    <insert id="insertStudent" parameterType="com.sqm.pojo.Student">
                        INSERT INTO student(id,name,age,score)
                        VALUES (#{id},#{name},#{age},#{score})
                    </insert>
                </mapper>
                ```

            -   `#{xxx}`:代表预处理语句,在sql中将被识别为?,占位符

    -   映射文件的顶级元素(mapper标签的子标签),按照应该被定义的顺序:

    -   `cache` – 给定命名空间的缓存配置。

    -   `cache-ref` – 其他命名空间缓存配置的引用。

    -   `resultMap` – 是最复杂也是最强大的元素，用来描述如何从数据库结果集中来加载对象。

    -   `parameterMap` –~~已废弃！老式风格的参数映射。内联参数是首选,这个元素可能在将来被移除，这里不会记录。~~

    -   `sql` – 可被其他语句引用的可重用语句块。

    -   `insert` – 映射插入语句

    -   `update` – 映射更新语句

    -   `delete` – 映射删除语句

    -   `select` – 映射查询语句

        ​

6.  定义主配置文件

    1.  主配置文件也是xml格式,dtd约束为mybatis-3-config.dtd,主配置文件命名随意,例如mybatis.xml

        ```xml
        <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE configuration
                PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-config.dtd">
        ```

    2.  主配置文件负责配置mybatis运行环境和注册映射文件

    3.  主配置文件的顶层结构如下:

        -   configuration 配置
        -   properties 属性
        -   settings 设置
        -   typeAliases 类型别名
        -   typeHandlers 类型处理器
        -   objectFactory 对象工厂
        -   plugins 插件
        -   environments 环境
            -   environment 环境变量
                -   transactionManager 事务管理器
                -   dataSource 数据源
        -   databaseIdProvider 数据库厂商标识
        -   mappers 映射器

    4.  environments标签中定义数据源的四大属性:

        -   ```xml
            <dataSource type="POOLED">
              <property name="driver" value="${driver}"/>
              <property name="url" value="${url}"/>
              <property name="username" value="${username}"/>
              <property name="password" value="${password}"/>
            </dataSource>
            ```

    5.  映射器mappers中的子标签mapper用于注册各个映射

        -   ```xml
            <mappers>
              <mapper resource="mapper/StudentDaoMapper.xml"/>
            </mappers>
            ```

7.  定义dao实现类

    -   ```java
        public class StudentDaoImpl implements IStudentDao{
        	private SqlSession session;
            public void insertStudent(Student sutdent){
                try{
        			//1.读取主配置文件
        			//2.创建SqlSessionFactory对象
        			//3.创建SqlSession对象
        			//4.通过SqlSession操作数据
        			//5.提交SqlSession

                } catch(IOException e){
                	e.printStackTrace();
                } finally {
                	//6.关闭SqlSession

                }	
            } 
        }
        ```

8.  定义测试类

    -   ```java
        IStudentDao dao = new StudentDaoImpl();
        Student student = new Student("sqm",26,88);
        dao.insertStudent(student);
        ```

9.  添加**日志控制文件**

    -   mybatis中常用log4j进行日志处理,将log4j.properties放入源目录下

    -   日志级别为debug,可显示执行的sql语句,参数值,对db的影响条数

    -   日志级别为trace,可多显示出查询出的每条记录的每个字段名及值

    -   若使用跟日志对象rootLogger,会输出太多信息,建议更改为mapper的namespace值:如logger.test

        -   ```properties
            //输出太多信息
            log4j.rootLogger=debug,console

            #log4j.logger.namespace_value=...
            log4j.logger.test = debug,console
            ```

### 1.4.2 使用mybatis工具类

-   简化获取SqlSession对象的方法,通过在工具类中定义单例SqlSessionFactory对象获取SqlSession

-   ```java
    public class MyBatisUtil{
    	//私有静态对象
        private static SqlSessionFactory factory;

        //获取session对象
        public static SqlSession getSession() {
            try {
                if (factory == null) {
                    InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                    factory = new SqlSessionFactoryBuilder().build(inputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return factory.openSession();
        }
    }	
    ```

#### 修改Dao接口的实现类

-   由于SqlSession对象必须关闭,所以在dao类的方法中添加finally语句加入session.close();此时无需catch代码块

-   ```java
        public void insertStudent(Student sutdent){
            try {
                //1.使用工具类获取SqlSession对象
                session = MyBatisUtil.getSession();
                //2.通过SqlSession操作数据
                session.insert("insertStudent", student);
                //3.提交SqlSession
                session.commit();
            } finally {
                //4.关闭SqlSession
                if (session != null) {
                    session.close();
                }
            }
        } 
    ```

### 1.4.3 从属性文件中获取dbcp连接四要素

-   从properties文件中获取dbcp连接四要素

-   在源目录下定义jdbc.properties

-   ```properties
    jdbc.driver=com.mysql.jdbc.Driver
    jdbc.url=jdbc:mysql:///student
    jdbc.username=root
    jdbc.password=admin
    ```

-   修改主配置文件,**通过修改properties标签的resource属性定位属性文**件

    -   这些属性都是可外部配置且可动态替换的

-   ```xml
    //注册属性文件
    <properties resource="jdbc.properties">
      <property name="username" value="dev_user"/>
      <property name="password" value="F2Fa3!33TYyg"/>
    </properties>

    ...

    <dataSource type="POOLED">
      <property name="driver" value="${jdbc.driver}"/>
      <property name="url" value="${jdbc.url}"/>
      <property name="username" value="${jdbc.username}"/>
      <property name="password" value="${jdbc.password}"/>
    </dataSource>
    ```

-   添加多个映射文件

    -   每个映射文件的namespace属性不同,用于区分不同mapper的同名的操作

    -   ```xml
        <mappers>
          <mapper resource="com/sqm/dao/mapper.xml"/>
          <mapper resource="com/sqm/dao/mapper2.xml"/>
        </mappers>
        ```

    -   ```xml
        <mapper namespace="test">
          <insert id="insertStudent"></insert...>
        </mapper>
        <mapper namespace="reyco">
            <insert id="insertStudent"></insert...>
        </mapper>
        ```

-   修改dao实现类

    -   dao实现时session操作的方法可指定声明参数格式为 ` "命名空间.方法"`的参数进行操作

        -   ```java
            session.insert("test.insertStudent",student);
            ```

## 1.5 主配置文件详解

-   主配置文件名可以随意命名，其主要完成以下几个功能：
    -   注册存放 DB 连接四要素的属性文件(properties标签)	
    -   注册实体类的全限定性类名的别名(typeAliases标签)
    -   配置 MyBatis 运行环境，即数据源与事务管理器(environments标签)
    -   注册映射文件(mappers标签)
-   主配置文件的顶层结构如下(**有先后顺序** ):
    -   configuration 配置
    -   properties 属性
    -   settings 设置
    -   typeAliases 类型别名
    -   typeHandlers 类型处理器
    -   objectFactory 对象工厂
    -   plugins 插件
    -   environments 环境
        -   environment 环境变量
            -   transactionManager 事务管理器
            -   dataSource 数据源
    -   databaseIdProvider 数据库厂商标识
    -   mappers 映射器

#### 通过properties标签设置属性文件

-   通常用来注册jdbc四要素的属性文件,resource属性值即为属性文件的路径

-   ```xml
    	<properties resource="jdbc.properties"/>
    ```

#### 通过typeAliases标签指定别名

-   一般项目中的路径层次很多,可以通过指定**特定类或包**别名的方式来简化配置文件

-   package标签:标签的name属性指定要简化的包,此包下的所有类的类名都被简化

    -   ```xml
            <typeAliases>
                <package name="com.sqm.pojo"/>
            </typeAliases>
        ```

-   typealias标签:单独指定某个类名的别名,type属性指定要简化类名的类,alias属性指定简化后的别名

    -   ```xml
            <typeAliases>   
                <typeAlias type="com.sqm.util.MyBatisUtil" alias="MyBatisUtil"/>
                <typeAlias type="com.sqm.dao.impl.StudentDaoImpl" alias="StudentDao"/>
            </typeAliases>
        ```

    -   注意:package标签和typealias标签不能同时存在

-   mybatis中所有基本类型的别名为**基本类型前加下划线**:例如:int别名为`_int`,boolean别名为` _boolean`

-   mybatis中所有包装类的别名为包装类名的**小写形式**:例如:String别名为`string`,Object别名为`object`,Date别名为`date`,HashMap别名为`hashmap`

#### 通过environments标签配置mybatis运行环境

-   environments标签可包含多个environment子标签,每个environment标签代表了一个运行环境

    -   environments的default属性指定了当前mybaits运行的环境
        -   environment的id属性代表了不同环境名称,作为environments的default值出现

-   environment的子标签:

-   **transactionManager**标签指定mybatis的**事务处理器**

    -   MyBatis 支持两种事务管理器类型：JDBC 与 MANAGED。

        -   JDBC：使用 JDBC 的事务管理机制。即通过Connection 的 commit()方法提交，通过rollback()方法回滚。但默认情况下，MyBatis将自动提交功能关闭了，改为了**手动提交**,即程序中需要显式的对事务进行提交或回滚。
        -   MANAGED：由**容器**来管理事务的整个生命周期（如 Spring 容器）

    -   ```xml
                    <transactionManager type="JDBC">
                        <property name="" value=""/>
                    </transactionManager>
        ```

-   **dataSource**标签配置mybatis使用的数据源类型与数据库连接的基本属性

    -   type属性指定使用的数据源类型常见有类型有：UNPOOLED、POOLED、JDNI

        -   UNPOOLED ：不使用连接池。即每次请求，都会为其创建一个DB 连接，使用完毕后，会马上将此连接关闭。
        -   POOLED：使用数据库连接池来维护连接。
        -   JNDI：数据源可以定义到应用的外部，通过JNDI 容器获取数据库连接。

    -   property子标签指定jdbc的连接属性,即可以直接指定,也可以通过properties中指定的属性源中读取数据

    -   ```xml
                    <dataSource type="POOLED">
                        <property name="driver" value="${jdbc.driver}"/>
                        <property name="url" value="${jdbc.url}"/>
                        <property name="username" value="${jdbc.username}"/>
                        <property name="password" value="${jdbc.password}"/>
                    </dataSource>
        ```

#### 通过mappers标签指定映射文件

-   mappers的**子标签mapper**指定所用的映射文件路径

    -   通过resource属性指定:在项目中的相对路径

    -   ```xml
        <mappers>
                <mapper resource="mapper/StudentDaoMapper.xml"/>
        </mappers>
        ```

    -   通过url属性指定:可以通过本地文件目录或网络的url读取映射文件(此方法不常用)

    -   ```xml
                <mapper url="F:/Git/MybatisTest/src/main/resources/mapperStudentDaoMapper.xml"/>
        		<mapper url="http://www.xxx.com/resources/mapper.xml"/>
        ```

    -   通过class属性指定:直接指定要映射的类的接口的全类名

        -   映射文件名要与接口名称相同
        -   映射文件要与接口在同一个包内
        -   映射文件中的mapper标签的namespace属性值为接口的全类名

    -   ```xml
            <mappers>
                <mapper class="com.sqm.dao.StudentDao"/>
            </mappers>
        ```

-   当映射文件较多时可用**package标签**指定映射文件存放的包,package的name属性指定包的全名

    -   映射的类**要使用mapper动态代理实现**

    -   映射文件名要与接口名相同

    -   映射文件要与接口在同一个包内

    -   mapper标签的namespace属性值为接口的全类名

    -   ```xml
            <mappers>
                <mapper package="com.sqm.dao."/>
            </mappers>
        ```

-   后面两种通过类或包指定mapper.xml的方式,在maven中不适用

## 1.6 API 详解

### 1.6.1 Resources类

-   mybatis的资源类,用于读取资源文件,通过不同的方法读取并解析配置文件,返回**不同类型的IO流对象**
-   getResourceAsFile:以文件形式读取
-   getResourceAsProperties:以properties形式读取
-   getResourceAsReader:以字符流读取
-   getResourceAsStream:以字节流读取
-   getResourceURL:读取url路径例如网络的资源文件
    -   所有方法参数可以为(String resource):resource文件的路径
        -   (ClassLoader loader,String resource):指定读取的类加载器

### 1.6.2 SqlSessionFactoryBuilder类

-   仅用于创建工厂对象,方法build()用完即可即可销毁,build()方法重载多,根据获取的资源文件的对象类型不同而重载
    -   build的第一个参数为获取的配置文件,第二个参数一般为资源文件要使用的环境environment的id值,若不指定则使用default的环境

### 1.6.3 SqlSessionFactory接口

-   此接口创建的工厂对象为一个**重量级对象**(**资源消耗较大**),线程安全,仅仅需要一个工厂对象即可,使用openSession()方法创建一个sql会话对象
    -   openSession(boolean flag):参数若不设,默认为false,即需手动commit()的对象;参数若为true,则自动提交语句

### 1.6.4 SqlSession接口

-   用于操作持久化内容,一个sqlSession对应一次**数据库会话**,sqlsession创建则会话开始,sqlsession关闭则会话结束
-   sqlsession接口对象是**线程不安全的**,所以每一次操作必须执行close(),被关闭的前提是已经提交;若没有提交要关闭,则会执行回滚操作,若被提交则直接关闭会话
-   SqlSession接口中的CURD会话操作语句:
    -   **insert(String statement,[Object parameter])**:执行插入sql语句,**第一个参数statement,要使用的语句在mapper中的的id值**,**第二个参数为传递给statement的对象**,insert中是被插入的对象,可以不指定
    -   **delete(String statement,[Object parameter])**:执行删除sql语句,parameter是传递给statement的对象,delete指的是被删除的对象
    -   **update(String statement,[Object parameter])**:执行修改sql语句.第二个参数在update语句中指的是要更新的的对象
    -   insert,delete,update的**返回值都是int,**表示被sql语句影响的数据有几行
    -   **select(String statement,[Object parameter],[RowBounds bounds], ResultHandler handler)**:执行选择sql语句,**ResultHandler对象是对返回的结果进行操作的操作器类**;**RowBounds是限制检索索引,用于限制返回的数据区域**
    -   **selectList(String statement,[Object parameter],[RowBounds bounds])**:执行选择sql语句,**以列表集合`List<E>`方式返回**,列表中每一个元素是一行数据
    -   **selectMap(String statement, [Object parameter], [String mapKey], [RowBounds rowBounds])**:执行选择sql语句,**返回值为`Map<K,V>`**,**第三个参数mapKey为该属性作用列表中每个值的键,通常是主键id**
    -   **selectOne(String statement,[Object paramenter])**:执行选择sql语句,返回值为选择的对象类
-   SqlSession中的其他语句:
    -   commit():提交语句,无参形式;参数可为boolean形式,表示是否强制提交
    -   rollback([boolean force]):回滚语句
    -   close():关闭会话

### 1.6.5 源码分析

-   由源码可知,在资源流使用后,SqlSessionFactoryBuilder对象的build()方法会把流关闭,所以无需手动关闭inputStream
-   SqlSessionFactory中无参的openSession()方法,将事务transaction的自动提交变为false,关闭了自动提交
-   SqlSession的insert(),delete(),update()的方法,其底层均是调用执行了 **update()方法**
    -   且update()方法中均把**dirty变量设置为true**,开启了脏读,即多个事务可以同时修改同一个数据
    -   commit()方法提交修改后又将dirty变量设置为false
-   SqlSession中无需过多显式回滚

# 2. 单表的CURD操作

## 2.1 自定义 Dao 接口实现类

### 2.1.1 搭建测试环境

1.  修改dao接口,加入所有CURD方法

    ```java
    public interface StudentDao {
        //插入Student
        void insertStudent(Student student);
        //插入后指定id
        void insertStudentCatchId(Student student);
        //删除,指定id值
        void deleteStudentById(int id);
        //修改Student
        void updateStudent(Student student);
        //查询所有
        List<Student> selectAllStudents();
        //查询所有,以id-student键值对形式返回
        Map<String,Student> selectAllStudentMap();
        //根据姓名查询,返回的列表的第一个元素为所查询的对象
        List<Student> selectStudentsByName(String name);
    }
    ```

2.  修改dao实现类,实现各个具体方法

3.  修改测试类,将dao的创建放在@Before注解中

    ```java
        private StudentDao dao;
    	@Before
        public void setUp() {
            dao = new StudentDaoImpl();
        }
    ```

### 2.1.2 插入数据

1.  修改映射文件,映射文件中的#{xxx}占位符底层使用的是反射,所以**xxx是类的成员变量,而不是数据库中表的字段名**;INSERT INTO ... (..)VALUES(...)

    ```xml
    <mapper namespace="com.sqm.dao.StudentDao">
        <!--插入一个student对象-->
        <insert id="insertStudent">
            INSERT INTO student(name,age,score)
            VALUES (#{name},#{age},#{score})
        </insert>
    </mapper>
    ```

2.  修改dao实现类,使用工具类获取SqlSession对象

3.  修改测试类,执行插入语句

### 2.1.3 插入后用新id 初始化被插入对象

####1. 修改映射文件

-   若在mysql中执行完INSERT INTO 语句后加上`SELECT @@IDENTITY `或者`SELECT LAST_INSERT_ID()`,则可输出插入行的id值,主键的值

-   mybaits中映射文件的insert标签的子标签**selectKey用于获取新插入记录的主键值**;语法即为mysql中的语法,执行此语句后会自动生成id值,通常是主键,自动增长

    -   selectKey标签的属性:
        -   resultType:获取的主键类型
        -   keyProperty:主键在Java类对应的属性名,实际将与insert()方法的第二个参数对象对应
        -   order:指出selectKey操作是在insert语句之前还是之后,可以不指定,mysql为AFTER,oracle为BEFORE

-   ```xml
        <!--插入并指定其id值-->
        <insert id="insertStudentCatchId" >
            INSERT INTO student(name,age,score)
                    VALUES (#{name},#{age},#{score})
            <selectKey keyProperty="id" resultType="int" order="AFTER">
                SELECT last_insert_id();
            </selectKey>
        </insert>
    ```

#### 2. 修改dao实现类,采用插入后获取id的语句

```java
    public void insertStudentCatchId(Student student) {
        try {
            session = MyBatisUtil.getSession();
            session.insert("insertStudentCatchId", student);
            session.commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
```

####3. 修改测试类,获得插入的id值

```java
    //插入后自动生成id值
    @org.junit.Test
    public void Test02() {
        Student student = new Student("student",22,99);
        System.out.println("插入前student = " + student);
        dao.insertStudentCatchId(student);
        System.out.println("插入后student = " + student);
    }
```

#### 4. id是何时获取到的

-   无论插入操作是提交还是回滚，数据库均会为insert 的记录分配一个新的id,即使回滚，这个id 已经被占用。后面再插入并提交的记录数据，被分配的 id 是跳过此 id 后的 id
-   主键值的生成只与 insert语句是否执行有关，而与最终是否提交无关

### 2.1.4 删除数据

#### 1. 修改映射文件

-   删除语句的动态参数`#{xxx}`也表示一个占位符,代表delete()的第二个参数parameter,xxx可以为任意值,**无需与实际参数值相同**; DELETE FROM ... WHERE

-   ```xml
        <!--根据给定的id删除数据-->
        <delete id="deleteStudentById">
            DELETE FROM student.student WHERE id=#{choose}
        </delete>
    ```

#### 2.修改dao实现类

```java
    public void deleteStudentById(int id) {
        try {
            session = MyBatisUtil.getSession();
            session.delete("deleteStudentById", id);
            session.commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
```

#### 3. 修改测试类

````java
    //删除指定的id的数据
    @org.junit.Test
    public void Test03(){
        dao.deleteStudentById(6);
    }
````

### 2.1.5 修改数据

#### 1. 修改映射文件

-   修改语句的动态参数`#{xxx}`**必需与实际参数值类型相同**,即为student对象的属性名称,不能随意填写;UPDATE ... SET ... WHERE...

-   ```xml
        <!--根据给定的Student对象修改数据-->
        <update id="updateStudent">
            UPDATE student.student SET name=#{name},age=#{age},score=#{score}
            WHERE id=#{id}
        </update>
    ```

#### 2.修改dao实现类

```java
    public void updateStudent(Student student) {
        try {
            session = MyBatisUtil.getSession();
            session.update("updateStudent", student);
            session.commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
```

#### 3. 修改测试类

```java
    //修改指定的Student数据
    @org.junit.Test
    public void Test04(){
        Student student = new Student("define", 32, 99);
        student.setId(5);
        dao.updateStudent(student);
    }
```



###2.1.6 查询所有对象并返回List

#### 1. 修改映射文件

-   select标签的resultType属性不是最终的List类型,而是每查询到一条记录,将记录封装为对象的类型;SELECT * FROM student

-   **同一个映射文件一般是映射同一个类的操作**,例如StudentDao一个映射文件,TeacherDao一个映射文件

    -   resultType使用全限定类名,同样可使用**别名**用于简化写法

-   ```xml
        <!--查询所有数据并返回list-->
        <select id="selectAllStudents" resultType="Student">
            SELECT * FROM student.student
        </select>
    ```

#### 2.修改dao实现类

```java
    public List<Student> selectAllStudents() {
        //创建接收容器
        List<Student> students = null;
        try {
            session = MyBatisUtil.getSession();
            students = session.selectList("selectAllStudents");
            //select语句无需commit
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return students;
    }

```

#### 3. 修改测试类

```java
    //获取所有Student对象并以List形式返回
    @org.junit.Test
    public void Test05() {
        List<Student> students = dao.selectAllStudents();
        for (Student student : students) {
            System.out.println(student.toString());
        }
    }
```

### 2.1.7 查询所有对象并返回Map

#### 1. 修改映射文件

-   映射文件不用修改,与selectAllStudents的select语句相同

-   ```xml
        <!--查询所有数据并返回Map-->
        <select id="selectAllStudents" resultType="Student">
            SELECT * FROM student.student
        </select>
    ```

#### 2.修改dao实现类

-   使用selectMap()方法完成查询,此方法返回的所有记录,每一条封装成一个value对象,并作为Map集合的value,将指定属性,也就是方法的String mapKey参数所对应的字段名作为Map集合的key,**方法原型为:`Map<K,V> selectMap(String statement,String mapKey)`**

```java
    public Map selectAllStudentMap(String mapKey) {
        //创建接受容器
        Map studentMap = null;
        try {
            session = MyBatisUtil.getSession();
            studentMap = session.selectMap("selectAllStudents", mapKey);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return studentMap;
    }
```

#### 3. 修改测试类

-   在实际使用时再限制类型,因为key的类型可以是任意数据库中对应的类型

```java
    //获取所有对象,以map形式返回,key为每个指定的唯一字段,value为每一条数据
    @org.junit.Test
    public void test06() {
        Map<Integer, Student> studentMap = dao.selectAllStudentMap("id");
        Set<Integer> studentMapSet = studentMap.keySet();
        for (Integer key : studentMapSet) {
            Student student = studentMap.get(key);
            System.out.println(key + ":" +student.toString());
        }
    }
```

#### 4. 说明

-   若指定的key在数据库中并不唯一,则后面的记录值会覆盖掉前面的值;即指定 key 的 value
    值，一定是 DB 中该同名属性的最后一条记录值

### 2.1.8 查询单个对象

#### 1. 修改映射文件

-   ```xml
        <!--通过id值查询单个对象-->
        <select id="selectStudentById" resultType="Student">
            SELECT *
            FROM student.student
            WHERE id = #{id}
        </select>
    ```

#### 2.修改dao实现类

-   使用SqlSession的selectOne()方法,其会将查询的结果记录封装为一个指定类型的对象;方法原型为：`Object selectOne (String statement, Object parameter)`
    -   statement： 映射文件中配置的SQL语句的id
    -   parameter：查询条件中动态参数的值 

```java
    public Student selectStudentById(int id) {
        //定义返回值的容器
        Student student = null;
        try {
            session = MyBatisUtil.getSession();
            student= session.selectOne("selectStudentById", id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return student;
    }
```

#### 3. 修改测试类

```java
    //获取指定id的单个student对象
    @org.junit.Test
    public void test07() {
        Student student = dao.selectStudentById(3);
        System.out.println(student.toString());
    }

```

### 2.1.9 模糊查询

#### 1. 修改映射文件

-   在进行模糊查询时,用到WHERE ... LIKE ... 语句,LIKE语句中,%代表零或任意多个字符,_代表一个字符,例如:`WHERE name LIKE _a%`

-   也可以使用字符串的拼接,SQL 中的字符串的拼接使用的是函数**concat(arg1,arg2,…)**。**注意不能使用Java 中的字符串连接符+**

    -   ```mysql
        SELECT * FROM student.student WHERE name LIKE concat('%',#{value},'%')
        ```

    -   `#{xxx}`相当于preparedStatement语句中的?占位符

    -   在mapper的select标签中${value}代表的是直接将参数拼接到sql语句中,可能会发生sql注入

    -   ```mysql
        SELECT * FROM student.student WHERE name LIKE '%${value}%'
        ```

#### 2.修改dao实现类

```java
    public List<Student> selectStudentsByName(String name) {
        List<Student> students;
        try {
            session = MyBatisUtil.getSession();
            students = session.selectList("selectStudentsByName", name);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return students;
    }
```

#### 3. 修改测试类

```java
    //模糊查询
    @org.junit.Test
    public void test08() {
        String name = "i";
        List<Student> students = dao.selectStudentsByName(name);
        System.out.println("name中包含" + name + "的student有:");
        for (Student student : students) {
            System.out.println(student.toString());
        }
    }
```

#### 4. $与#的区别

-   \$与#的区别是很大的。**#为占位符**，**而\$为字符串拼接符**。
-   字符串拼接是**将参数值以硬编码的方式直接拼接到了 SQL 语句中**。字符串拼接就会引发两个问题：**SQL 注入问题与没有使用预编译所导致的执行效率低下问题**。
-   占位符的引入，解决以上两个问题;占位符的底层执行时，**是为 SQL 动态进行赋值**
    -   一般情况下，动态参数的值是**由用户输入的**,例如用户指定查询某个id，则**不能使用拼接符$**，因为有可能会出现SQL注入；
    -   若**动态参数的值是由系统计算生成的，则可以使用拼接符$**,例如从student对象中获取属性值与数据库字段对应,但仍存在执行效率问题
-   在 MyBatis中:
    -   使用#号占位符，则后台执行 SQL使用的为PreparedStatement，将会防止 SQL 注入
    -   而使用$符，则为字符串拼接，使用的为 Statement，将无法防止 SQL 注入

#### 5. SQL的预编译回顾

-   当 Java代码通过 JDBC 的 PreparedStatement 向 DB 发送一条 SQL 语句时，DBMS 会首先编译 SQL 语句，然后将编译好的 SQL 放入到 DBMS 的数据库缓存池中再执行。当 DBMS 再次接收到对该数据库操作的 SQL 时，先从 DB 缓存池中查找该语句是否被编译过，若被编译过， 则直接执行，否则先编译后将编译结果放入 DB 缓存池，再执行。

###2.1.10 根据给定的Map进行查询

-   mapper 中 SQL 语句的动态参数也可以是 Map 的 key,例如同时指定student对象的age和score来查询
-   将多条查询语句条件的参数封装在Map\<String,Object\>中

#### 1. 修改配置文件

```xml
    <!--通过指定的字段名来查找符合条件的数据-->
    <select id="selectStudentsByMap" resultType="Student">
        SELECT *
        FROM student.student
        WHERE age &lt;= '%' #{ageCondition} '%' AND score >= '%' #{scoreCondition} '%';
    </select>
```

### 2. 修改dao实现类

```java
   	//根据给定Map模糊查询,map的key为字段名,value为模糊查询的条件
	public List<Student> selectStudentsByMap(Map<String,Object> map) {
        List<Student> students;
        try {
            session = MyBatisUtil.getSession();
            students = session.selectList("selectStudentsByMap", map);
        } finally {
            session.close();
        }
        return students;
    }
```

###3. 修改测试类

```java
    //根据给定的map模糊查询
    @org.junit.Test
    public void test09() {
        Object ageCondition = 22;
        Object scoreCondition = 80;
        //指定的map中的key必须包含mapper中sql语句要查找的项目
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ageCondition",ageCondition);
        map.put("scoreCondition",scoreCondition);
        List<Student> students = dao.selectStudentsByMap(map);
        System.out.println("年龄小于等于" + ageCondition + "且得分大于等于" + scoreCondition + "的student有");
        for (Student student : students) {
            System.out.println(student.toString());
        }
    }
```

### 2.1.11 根据给定的Map进行查询2

-   mapper 中 SQL 语句的动态参数也可以是 Map 的 key,例如//根据id查询单个学生对象,既可以指定特定id的Student对象来查询,也可以通过直接查询id来查询,将两种查询方法封装在Map中
    -   直接通过id查询,则在map中封装为key="studentId",value=id值
    -   通过指定id的student对象查询,则在map中封装为key="student",value=student对象

#### 1. 修改配置文件

\#{studentId}代表直接获取map中key为studentId的值

\#{student.id}代表获取map中key为student的对象的id属性的值

```xml
    <!--通过id值或指定id的Student对象来查询单个对象-->
    <select id="selectStudent" resultMap="studentMap">
        SELECT *
        FROM student.student
        WHERE s_id = #{studentId} OR s_id = #{student.id};
    </select>
```

### 2. 修改dao实现类

```java
    //根据id查询单个学生对象,既可以指定特定id的Student对象来查询,也可以通过直接查询id来查询,将两种查询方法封装在Map中
	public Student selectStudent(Map map) {
        Student student;
        try {
            session = MyBatisUtil.getSession();
            student = session.selectOne("selectStudent", map);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return student;
    }
```

#### 3. 修改测试类

```java
    //通过指定id值或指定特定id值的student对象来获取student对象
    @org.junit.Test
    public void test07() {
        int id = 2;
        Student student = new Student();
        student.setId(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("studentId", id);
        map.put("student", student);
        System.out.println("要查询的id值为:" + id + ";要查询的student对象为:" +student.toString());
        student = dao.selectStudent(map);
        System.out.println(student.toString());
    }
```

#

## 2.2 属性名与查询字段名不相同的情况

-   mapper的子标签中,resultType属性值可以将查询结果直接映射为实体 Bean对象的条件是，SQL 查询的字段名与实体 Bean 的属性名一致,**自动将查询结果字段名称作为对象的属性名,通过反射机制完成对象的创建**
-   若属性名与查询字段名不同时,则需要用到**resultMap标签**来指定属性名与查询字段名之间的映射关系

### 2.2.1 使用结果映射resultMap

-   在mapper的子标签中使用resultMap标签确立实体类的属性与查询表的字段的映射关系,这里的Map是mapper的意思,resultMap是对resultType的增强

-   ```xml
    <mapper namespace="com.sqm.dao.StudentDao">
        <!--设定结果映射-->
        <resultMap id="studentMap" type="Student">
            <!--id标签专指id属性即主键字段与实体类的id属性对应的关系-->
            <id column="s_id" property="id"/>
            <!--result标签指其他属性间的对应关系,没有区别的属性可以不设对应关系,仍然为resultType的关系-->
            <result column="s_name" property="name"/>
            <result column="s_age" property="age"/>
            <result column="s_score" property="score"/>
        </resultMap>	
     	
        <!--通过名字中包含的字符串模糊查询-->
        <select id="selectStudentsByName" resultMap="studentMap">
            SELECT *
            FROM student.student
            WHERE s_name LIKE concat('%',#{value},'%')
        </select>

    </mapper>  
    ```

    -   type：指定要映射的实体类
    -   id：指定该 resultMap 映射关系的名称,之后的sql语句操作方法在resultMap属性中引用其值
    -   `<id>`标签：id 的字段名 column 与实体类的属性property 间的映射关系
    -   `<result>`标签：id 以外其它字段名 column 与实体类的属性 property间的映射关系,若字段名与实体类的属性名相同，可以不写入resultMap中。

##2.3 Mapper动态代理

-   Java语句中的Dao 的实现类其实并没有干什么实质性的工作,它仅通过 SqlSession相关 API定位到映射文件 mapper中相应 id 的SQL语句;真正对 DB 进行操作的工作其实是由框架通过 mapper中的**SQL语句操作标签**完成的

### 2.3.1  映射文件的 namespace 属性值

-   MyBatis 框架要求，将映射文件中mapper标签的 namespace 属性设为 Dao 接口的全类名;通过接口名即可定位到映射文件 mapper

-   ```xml
    <mapper namespace="com.sqm.dao.StudentDao">
    ```

### 2.3.2 日志输出控制文件

-   若为读取根Logger则输出太多无关紧要的语句,通过限定logger的namespace与mapper对应即可


-   ```properties
    log4j.logger.com.sqm.dao.StudentDao=debug,console
    ```

### 2.3.3 **Dao接口方法名**

-   MyBatis 框架要求，接口中的方法名，与映射文件中相应的SQL 标签的 id 值相同。系统

    会自动根据方法名到相应的映射文件中查找同名的 SQL 映射id

-   通过方法名就可定位到映射文件mapper 中相应的SQL语句

-   ```java
        //插入Student
        void insertStudent(Student student);
    ```

-   ```xml
        <!--插入一个student对象-->
        <insert id="insertStudent">
    ```

### 2.3.4 Dao对象的获取

-   之前dao对象的方法通过session来获取mapper中的方法

-   现在只需调用 SqlSession的 getMapper(Class\<T\> class)方法，即可获取指定接口的实现类对象。该方法的参数为指定 Dao接口类的 class值

-   ```java
    session = factory.operSession();
    dao = session.getMapper(StudentDao.class);
    ```

### 2.3.5 删除dao实现类

-   由于通过调用 Dao 接口的方法，不仅可以从SQL 映射文件中找到所要执行SQL 语句，

    还**可通过方法参数及返回值**将 SQL语句的动态参数传入，将查询结果返回。所以，Dao 的实现工作，完全可以由 MyBatis系统自动根据映射文件完成。所以，Dao 的实现类就不再需要了。

### 2.3.6 修改测试类

#### 1. @Before 与@After 注解方法

-   在@Before注解方法中获取到 SqlSession对象后，通过 SqlSession 的 getMapper()方法创建 Dao 接口实现类的动态代理对象。

-   在@After 注解方法中关闭 SqlSession 对象。

-   即把之前在dao实现类中获取session的代码放在测试类中

-   ```java
        //通过session的getMapper(Class<T> type)动态代理获得dao对象
        @Before
        public void setUp() {
            session = MyBatisUtil.getSession();
            dao = session.getMapper(StudentDao.class);
        }

        //将关闭session方法放入@after中
        @After
        public void tearDown() {
            session.close();
        }
    ```

#### 2. 添加SqlSession提交方法

-   通过commit()方法在每个test后提交增删改操作
-   即把之前在dao实现类中获取session的代码放在测试类中

#### 3. 删除 session.selectMap()相关方法的代码

-   MyBatis 框架对于 **Dao 查询**的自动实现，底层只会调用 **selectOne()与 selectList()**方法
-   接受类型为List则自动选择selectList();否则自动选择selectOne()方法

### 2.3.7 多查询条件无法整体接收的解决办法

[之前的例子是通过Map设置多查询条件](###2.1.10 根据给定的Map进行查询),还可以在mapper的sql语句中通过**参数索引\#{index}的方式**逐个接收每个参数

-   索引由arg0,arg1....表示,表示参数在接口方法中的索引位置

#### 1. 修改dao接口

```java
List<Student> selectStudentByConditions(String name,int age);
```

#### 2. 修改测试类

```java
  	//根据给定的条件来筛选数据
    @org.junit.Test
    public void test10() {
        String name = "s";
        int age = 25;
        int score = 85;
        List<Student> students = dao.selectStudentsByConditions(name, age,score);
        System.out.println("名字中包含" + name + "且年龄小于等于" + age + "得分大于" + score +"的学生有");
        for (Student student : students) {
            System.out.println(student.toString());
        }
    }
```

#### 3. 修改映射文件

```xml
    <!--通过给定的条件来筛选数据-->
    <select id="selectStudentsByConditions" resultMap="studentMap">
        SELECT *
        FROM student.student
        WHERE s_name LIKE '%' #{arg0} '%' AND s_age &lt;= #{arg1} AND s_score &gt;= #{arg2}
    </select>
```

