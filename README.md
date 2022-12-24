## Spring
![spring](https://pic3.zhimg.com/v2-fef1bbc24d789492e9d5bdcc5447b782_r.jpg)
### 核心概念
#### IoC (Inversion of control)：控制反转
1. 使用对象时在程序中不使用new产生对象（耦合度高），转换为由外部提供对象
2. 对象的控制权由程序转移到外部，实现解耦
3. 如何实现？Spring提供了一个IoC容器，IoC负责对象创建、初始化，这些创建或被管理的对象在IoC容器中称为Bean
#### DI (Dependency Injection)：依赖注入
1. 在容器中建立bean之间的依赖关系：在IoC容器中，service依赖于dao
2. 最终，对想从IoC容器中获取，且获取到的bean已经绑定了所有的依赖关系
***
### IoC案例分析
1. 管什么？（Service和Dao）
2. 如何将被管理的对象告知IoC容器？（配置）
3. 如何获取到IoC容器？（接口）
4. 获取到IoC容器后，如何从容器中获取到bean？（接口方法）
5. 过程分析（Maven项目）
* 导入spring的坐标spring-context
* 配置bean：class为bean的类型，即配置的bean的全路径类名
```
<bean id="bookDao" class="org.example.dao.impl.BookDaoImpl" />
<bean id="bookService" class="org.example.service.impl.BookServiceImpl" />
```
* 获取IoC容器，ApplicationContext是接口
```
ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
```
* 获取bean
```
BookDao bookDao = (BookDao) ctx.getBean("bookDao");
```
***
### DI案例分析
1. 删除业务层中使用new方式创建的dao对象
2. 提供依赖对象对应的setter方法
````
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
````
3. 配置service和dao的关系：name表示配置哪一个具体的属性，ref表示参照哪一个bean
```
 <bean id="bookService" class="org.example.service.impl.BookServiceImpl">
        <property name="bookDao" ref="bookDao" />
 </bean>
```
***
### bean
#### 相关配置
1. name：为bean设置别名
2. bean默认为单例对象，可通过修改scope属性改为非单例对象
* 适合交给容器管理的bean：表现层对象、业务层对象、数据层对象、工具对象
* 不适合交给容器管理的bean：封装实体的域对象
#### 实例化bean的三种方式
1. 提供可访问的构造方法（无参），无参构造方法不存在，抛出异常BeanCreationException
2. 静态工厂
3. 实例工厂：先配置工厂的bean，再让使用工厂的bean指向工厂的bean
4. 使用FactoryBean（Spring提供的方式），默认创建的对象也是单例对象
#### bean生命周期
1. bean生命周期控制
* 方式一：配置/提供生命周期控制方法
```
<bean id="bookDao" class="org.example.dao.impl.BookDaoImpl" init-method="init" destroy-method="destroy"/>
```
* 方式二：接口控制，实现InitializingBean, DisposableBean接口
``` 
public class BookServiceImpl implements BookService, InitializingBean, DisposableBean {...}
```
2. bean的destroy方法只有在关闭容器时才执行
3. 如何关闭容器？（2种方式）
* ctx.close()：更暴力
``` 
ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
ctx.close();
```
* 设置容器关闭钩子
``` 
ctx.registerShutdownHook();
```
***

