## Spring
![spring](https://www.waytoeasylearn.com/wp-content/uploads/2020/01/4-2-768x400.png)
### 核心概念
#### IoC (Inversion of control)：控制反转
1. 使用对象时在程序中不使用new产生对象（耦合度高），转换为由外部提供对象
2. 对象的控制权由程序转移到外部，实现解耦
3. 如何实现？Spring提供了一个IoC容器，IoC负责对象创建、初始化，这些创建或被管理的对象在IoC容器中称为Bean
#### DI (Dependency Injection)：依赖注入
1. 在容器中建立bean之间的依赖关系：在IoC容器中，service依赖于dao
2. 最终，对想从IoC容器中获取，且获取到的bean已经绑定了所有的依赖关系
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
### 依赖注入
#### 依赖注入方式
1. setter注入
   * 引用类型:：在bean中定义引用类型属性并提供对应的setter，配置中使用property标签ref属性注入引用类型的对象
       ``` 
       private BookDao bookDao;
    
       public void setBookDao(BookDao bookDao) {
           this.bookDao = bookDao;
       }
       ```
    ``` 
    <bean id="bookService" class="org.example.service.impl.BookServiceImpl">
            <property name="bookDao" ref="bookDao" />
    </bean> 
    ```
   * 简单类型：配置中使用property标签value属性注入简单类型数据 
2. 构造器注入：和setter注入类似，在bean中定义引用类型属性并提供可访问的构造方法，将<property>标签改为<constructor-arg>
3. 推荐使用setter注入，更灵活
#### 依赖自动装配
1. IoC容器根据bean所依赖的资源在容器中自动查找并注入到bean中的过程 
2. 配置中使用bean标签autowire属性设置自动装配的类型 
3. 适用于引用类型依赖注入，不能对简单类型进行操作
### 数据源配置
#### 加载properties文件
1. 开启context命名空间
2. 使用context空间加载properties文件
   * 加载指定配置文件
    ``` 
    <context:property-placeholder location="jdbc.properties" /> 
    ```
   * 加载类路径或jar包中的全部配置文件
    ``` 
    <context:property-placeholder location="classpath*:*.properties" /> 
    ```
3. 使用属性占位符${}读取properties文件中的属性
### 容器
#### 容器初始化
1. 类路径加载配置文件
2. 文件路径加载配置文件
``` 
ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
ApplicationContext ctx = new FileSystemXmlApplicationContext("D:\\workspace-2022\\spring-learning\\applicationContext.xml");
```
3. 注意
   * BeanFactory是IoC容器的顶层接口，初始化对象时bean延迟加载。
   * ApplicationContext是Spring容器的核心接口，初始化bean时立即加载。
#### 获取bean
1. 使用bean名称获取
2. 使用bean名称获取并指定类型
3. 使用bean类型获取
``` 
BookService bookService = (BookService) ctx.getBean("bookService");
BookService bookService = ctx.getBean("bookService", BookService.class);
BookService bookService = ctx.getBean("bookService", BookService.class);
```
***
### 注解开发
#### 定义bean
1. 使用@Component定义bean
2. 核心配置文件中通过扫描组件加载bean
3. Spring提供@Component注解的三个衍生注解
   * @Controller：表现层bean定义
   * @Service：业务层bean定义
   * @Repository：数据层bean定义
4. Spring3.0开启了纯注解开发模式，用Java类代替Spring核心配置文件
``` 
@Configuration //用于设定当前类为配置类
@ComponentScan("org.example")  //设定扫描路径
public class SpringConfig {
} 
```
``` 
ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
```
5. @Scope定义bean作用范围（singleton/prototype），@PostConstruct、@PreDestroy定义bean生命周期
#### 依赖注入
1. 使用@Autowired注解开启自动装配模式（按类型）
2. 使用@Qualifier注解开启指定名称装配bean
3. 自动装配基于反射设计创建对象，且为暴力反射，无需提供setter方法 
4. 自动装配建议使用无参构造方法创建对象
5. 使用@Value实现简单类型注入
6. 使用@PropertySource注解加载properties文件，路径不允许使用通配符
#### 第三方bean管理
1. 使用@Bean配置第三方Bean，用独立的类进行管理
``` 
public class JdbcConfig {
    @Bean("dataSource")
    public DataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/spring_db");
        ds.setUsername("root");
        ds.setPassword("123456");
        return ds;
    }
}
```
2. 将独立的配置类加入核心配置
   * 方式一：（导入式）使用@Import注解手动加入配置类到核心配置
   * 方式二：（扫描式），使用@ComponentScan注解配置类所在的包
3. 第三方bean依赖注入
   * 简单类型注入：定义成员变量
   * 引用类型注入：为bean定义方法设置形参，容器会根据类型自动装配对象
*** 
### AOP
#### 简介
1. AOP：面向切面编程，在不惊动原始设计的基础上为其进行功能增强
2. Spring理念：无入侵式编程
3. 核心概念
   * 连接点（JoinPoint）：程序执行过程中的任意位置，在SpringAOP中理解为方法的执行
   * 切入点（Pointcut）：匹配连接点的式子，在SpringAOP中，一个切入点只描述一个具体方法，也可以匹配多个方法。切入点一定是连接点
   * 通知（Advice）：在切入点处执行的操作（共性功能），在SpringAOP中，功能最终以方法的形式呈现
   * 通知类：定义通知的类
   * 切面（Aspect）：描述通知和切入点的关系  
   ![SpringAOP](https://rumenz.com/java-topic/static-content/uploads/2015/01/spring-aop-diagram.jpg)
#### 思路
1. 导坐标
2. 定义dao接口与实现类
3. 制作共性功能（通知类和通知）
4. 定义切入点：切入点依托一个不具有实际意义的方法进行
5. 绑定切入点与通知关系，指定通知添加到原始连接点的具体执行位置
6. 定义通知类受Spring容器管理（@Component）。指定当前类为切面类（@Aspect）
7. 开启Spring对AOP注解驱动支持（@EnableAspectJAutoProxy）
``` 
@Component
@Aspect
public class MyAdvice {
    //定义切入点
    @Pointcut("execution(void org.example.dao.BookDao.update())")
    private void pt(){};
    
    //绑定切入点和通知的关系
    @Before("pt()")
    public void  method() {
        System.out.println(System.currentTimeMillis());
    }
}
```
``` 
@Configuration
@ComponentScan("org.example")
@EnableAspectJAutoProxy
public class SpringConfig {
}
```
#### AOP工作流程
1. Spring容器启动
2. 读取所有切面配置中的切入点
3. 初始化bean，判定bean对应类中的方法是否匹配到任意切入点
4. 匹配成功，创建原始对象（目标对象）的代理对象（Spring AOP本质：代理模式）
5. 获取的bean为代理对象时。根据代理对象的运行模式运行原始方法与增强的内容，完成操作
#### 切入点表达式
1. 切入点：要进行增强的方法
2. 切入点表达式：切入点的描述方式
   * 标准格式：
    ``` 
    execution(void org.example.dao.BookDao.update())
    ```
   * 可以使用通配符描述切入点（如：给所有业务层find方法加AOP）
    ``` 
    execution(* org.example.*.*Service.find*(..))
    ```
#### AOP通知类型
1. 前置通知 @Before()
2. 后置通知 @After()
3. 环绕通知 @Around()
   * 环绕通知必须依赖形参ProceedingJoinPoint才能实现对原始方法的调用，进而实现原始方法调用前后同时添加通知
   * 对原始方法的调用可以不接收返回值，通知方法设置成void即可，若要接收返回值，必须设定为Object类型
   * 由于无法预知原始方法是否会抛异常，因此环绕通知方法必须抛出Throwable对象
    ``` 
    @Around("pt()")
        public Object around(ProceedingJoinPoint pjp) throws Throwable {
            System.out.println("around before advice...");
            Object ret = pjp.proceed();  //对原始操作的调用
            System.out.println("around after advice...");
            return ret;
        }
    ```
   * 测量业务层接口执行效率时可以使用pjp.getSignature()获取执行签名信息
4. 返回后通知 @AfterReturning
5. 抛出异常后通知 @AfterThrowing
#### AOP通知获取数据 
1. 获取参数数据：JoinPoint对象可以获取到原始方法的调用参数，ProceedingJoinPoint是JoinPoint的子类
``` 
@Before("pt2()")
    public void before2(JoinPoint jp) {
        Object[] args = jp.getArgs();
        System.out.println(Arrays.toString(args));
    }
```
2. 获取返回值数据
3. 获取异常数据
***
### Spring事务
#### 事务简介
1. 事务作用：在数据层保障一系列的数据库操作同成功同失败
2. Spring事务作用：在数据层或业务层保障一系列的数据库操作同成功同失败
#### 步骤
1. 在业务层接口上添加Spring事务管理
``` 
public interface AccountService {

    @Transactional
    public void transfer(String out, String in, Double money);
}
```
  * 注解一般不会添加到业务层接口实现类中，降低耦合
  * 注解也可以添加到业务方法上，表示当前接口所有方法开启事务
2. 设置事务管理器，MyBatis框架使用的是JDBC事务
3. 开启注解式事务驱动
#### Spring事务角色
1. 事务管理员：事务发起方，在Spring中通常指代业务层开启事务的方法
2. 事务协调员：事务加入方，在Spring中通常代指数据层方法，也可以是业务层方法
#### 事务属性
1. 事务相关配置
   * readonly：设置是否为只读事务
   * timeout：设置事务超时时间
   * rollbackFor：设置事务回滚异常（class），Spring有些事务不回滚，需要指定
#### 事务传播行为
1. 事务传播行为：事务协调员对事务处理员所携带事务的处理态度
2. 在业务层接口添加Spring事务，设置事务的传播行为（REQUIRES_NEW：需要新事务）
``` 
@Transactional(propagation = Propagation.REQUIRES_NEW)
```
***
## SpringMVC
### 简介
SpringMVC是一种基于Java实现MVC模型的轻量级web框架，用于表现层开发 
#### 步骤
1. 使用SpringMVC需要先导入SpringMVC与Servlet坐标（导Servlet坐标时加上scope） 
2. 创建SpringMVC控制器 
3. 初始化SpringMVC环境，设定SpringMVC加载对应的bean 
4. 初始化Servlet容器，加载SpringMVC环境，设置SpringMVC技术处理的请求
### 请求与响应
#### 请求
1. 请求映射路径：@RequestMapping
2. POST请求乱码处理：为web容器添加过滤器并指定字符集。Spring-web包提供了专用的字符过滤器
``` 
@Override                                                          
protected Filter[] getServletFilters() {                           
    CharacterEncodingFilter filter = new CharacterEncodingFilter();
    filter.setEncoding("UTF-8");                                   
    return new Filter[]{filter};                                   
}                                                                  
```
#### 请求参数
1. 普通参数
   * url地址传参，地址参数名与形参变量名相同。定义形参即可接收参数
   * 请求参数名与形参名不同，使用@RequestParam绑定参数关系
2. POJO参数：请求参数名与形参对象属性名相同，定义POJO类型即可接受参数
3. 嵌套POJO参数
4. 数组参数：请求参数名与形参对应属性名相同
5. 集合保存普通参数：请求参数名与形参集合对象名相同，@RequestParam绑定参数关系  
注：json数据需要导坐标，并开启自动转换json数据的支持（@EnableWebMvc）,接受json数据时加上（@RequestBody）
#### 日期类型参数传递
1. 接收形参时，根据不同的日期格式设置不同的接收方式
2. @DateTimeFormat注解设定时间型数据格式，属性pattern为时间日期格式字符串
#### 响应
1. 响应文本数据
2. 响应json数据（对象转json）
``` 
@RequestMapping("/toJsonPOJO")
    @ResponseBody
    public User toJsonPOJO() {
        System.out.println("返回json对象");
        User user = new User();
        user.setName("hello");
        user.setAge(18);
        return user;
    }
```  
3. @ResponseBody：设置当前控制器返回值作为响应体
#### RESTful快速开发
REST：表现形式状态转换，按照REST风格访问资源时使用行为动作区分对资源进行了何种操作
1. @RestController：设置当前控制器为RESTful风格，等同于@Controller与@ResponseBody两个注解组合功能
2. @GetMapping、@PostMapping、@PutMapping、@DeleteMapping
### 异常
#### 出现异常的常见位置
1. 框架内部抛异常
2. 数据层抛异常：因外部服务器故障导致
3. 业务层抛异常：因业务逻辑书写错误导致
4. 表现层抛异常：因数据收集、校验规则导致（如：不匹配的数据类型间导致异常）
5. 工具类抛异常：因工具类书写不严谨不够健壮导致（如：必要释放的连接长期未释放）
#### 异常处理器
1. 集中、统一处理项目中出现的异常
2. 所有的异常均抛出到表现层处理
``` 
@RestControllerAdvice
public class ProjectExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex) {
        return new Result();
    }
}
```
#### 项目异常分类
1. 业务异常
2. 系统异常：项目运行过程中可预计且无法避免的异常
3. 其他异常：编程人员未预期到的异常
#### 步骤
1. 自定义项目系统级异常
2. 自定义项目业务级异常
3. 自定义异常编码
4. 触发自定义异常
5. 拦截并处理异常
### 拦截器
拦截器是一种动态拦截方法调用的机制，在springMVC中动态拦截控制器方法的执行
#### 拦截器和过滤器的区别
1. Filter属于Servlet技术，Interceptor属于SpringMVC技术
2. Filter对所有访问进行增强，Interceptor针对SpringMVC的访问进行增强
#### 步骤
1. 声明拦截器的bean，并实现HandleInterceptor接口
2. 定义配置类，继承WebMvcConfigurationSupport，实现addInterceptor方法（注意：扫描加载配置）
3. 添加拦截器并设定拦截的访问路径
``` 
@Configuration
public class SpringMvcSupport extends WebMvcConfigurationSupport {

    @Autowired
    private ProjectInterceptor projectInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(projectInterceptor).addPathPatterns("/books","/books/*");
    }
} 
```
4. 注：可以使用标准接口WebMvcConfigurer简化开发，但侵入式较强
#### 拦截器链
1. 拦截器链的运行顺序以拦截器添加顺序为准
2. 当拦截器中出现对原始处理器的拦截，后面的拦截器均终止运行
3. 当拦截器运行中断，仅运行配置在前面的拦截器的afterCompletion操作
*** 
### Spring新功能 —— Webflux
#### 基本介绍
1. Spring5添加的新模块，功能与springMVC类似。但Webflux是使用响应式编程出现的框架
2. 传统的web框架，如SpringMVC，基于Servlet容器，而Webflux是一种异步非阻塞的框架。异步非阻塞的框架在Servlet3.1之后才支持，核心是基于Reactor的相关API实现的
3. Webflux特点
   * 异步非阻塞：在资源有限的情况下，提高系统的吞吐量和弹性，以Reactor为基础实现响应式编程
   * 函数式编程：Webflux使用Java8函数式编程方式实现路由请求
4. SpringMVC和Webflux
![SpringMVC和Webflux](https://img-blog.csdnimg.cn/20190103151431670.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L21pYW95aWJvMTI=,size_16,color_FFFFFF,t_70)
#### 响应式编程
1. 面向数据流和变化传播的编程范式
2. 响应式编程操作中，Reactor是满足Reactive的规范框架
3. Reactor有两个核心类：Mono和Flux，这两个类均实现了Publisher接口，提供丰富操作符
   * Flux：实现发布者，返回N个元素
   * Mono：实现发布者，返回0或1个元素
4. Flux和Mono都可以发出三种数据信号：元素值、错误信号、完成信号
5. 三种信号特点
   * 错误信号和完成信号都代表终止信号，用于告诉订阅者数据流结束了
   * 错误信号终止数据流同时把错误信息传递给订阅者
   * 错误信号和完成信号不能共存
   * 若未发送任何元素值，而是直接发送错误信号或完成信号，表示空数据流
   * 若无错误信号或完成信号，表示是无限数据流
6. 调用just或其他方法只是声明数据流，只有订阅之后才能触发数据流
``` 
 Flux.just(1,2,3,4).subscribe(System.out::println);
 Mono.just(1).subscribe(System.out::println); 
```
7. 操作符：对数据流进行操作
   * map：元素映射为新元素
   * flatMap：元素映射为流，先把每个元素转成流，再把转换之后的流合并    
   ![操作符](https://ts1.cn.mm.bing.net/th/id/R-C.f4a3d8d76c8383a2afd30e43ede5b685?rik=Tn%2fWSPSaVfM4bA&riu=http%3a%2f%2fimages.zhuxingsheng.com%2f20220924235040_1664034640.jpg&ehk=8sepjKfAxkoV6Kx2dZ%2fAYdsYLqW8ob%2bEf%2fbyX6Wky8g%3d&risl=&pid=ImgRaw&r=0)
#### Webflux执行流程和核心API
1. SpringWebflux基于Reactor，默认容器是Netty(高性能NIO框架)
2. 执行过程：SpringWebflux核心控制器DispatchHandler，实现接口WebHandler，负责请求处理
   * HandlerMapping：请求查询到处理的方法
   * HandlerAdaptor：真正负责请求处理
   * HandlerResultHandler：响应结果处理
#### Webflux注解编程模型
1. SpringMVC方式实现：SpringMVC+Servlet+Tomcat
2. SpringWebflux方式实现：SpringWebflux+Reactor+Netty
#### Webflux函数式编程模型
1. 使用函数式编程模型操作时，需要自己初始化服务器
2. 两个核心接口：RouterFunction(实现路由功能)和HandlerFunction(处理请求生成响应的函数)
3. 核心任务：定义两个函数接口的实现并启动需要的服务器
4. SpringWebflux请求和响应：ServerRequest和ServerResponse
5. 步骤：
   * 创建Handler(具体实现方法)
   * 初始化服务器，编写Router
    ``` 
    //创建路由
        public RouterFunction<ServerResponse> routerFunction() {
            // 创建handler对象
            UserService userService = new UserServiceImpl();
            UserHandler handler = new UserHandler(userService);
            
            //设置路由
            return RouterFunctions.route(
                    GET("/user/{id}").and(accept(APPLICATION_JSON)),handler::getById)
                            .andRoute(GET("/users").and(accept(APPLICATION_JSON)),handler::getAll);
        }
    } 
    ```
    ``` 
     // 创建服务器完成适配
        public void createReactorServer() {
            RouterFunction<ServerResponse> route = routerFunction();
            HttpHandler httpHandler = toHttpHandler(route);
            ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
            
            // 创建服务器
            HttpServer httpServer = HttpServer.create();
            httpServer.handle(adapter).bindNow();
        }
    ```
    ``` 
    // 调用
        public static void main(String[] args) throws IOException {
            Server server = new Server();
            server.createReactorServer();
            System.out.println("enter to exit...");
            System.in.read();
        }
    ```
***
### Spring Security
#### 基本概念
1. Spring家族中一个安全管理框架
2. 认证：验证当前访问的用户是不是本系统中的用户。并确定是哪一个用户
3. 鉴权：经过认证，判断当前登录用户是否有权限执行某个操作 
4. SpringSecurity通过一些过滤器、拦截器实现登录鉴权流程
   * UsernamePasswordAuthenticactionFilter：处理用户名和密码是否正确的过滤器
   * ExceptionTranslationFilter：处理前面过滤器中抛出的异常
   * FilterSecurityInterceptor：进行权限校验的拦截器
5. 单点登录：在一个多应用系统中，在其中一个系统上登录之后，不需要在其它系统上登录也可以访问其内容
#### JWT
1. JWT：JSON Web Token，由头部、载荷与签名三部分构成，无状态，不需要服务器端缓存session
   * 头部(Header)：描述关于该JWT的最基本信息，如类型及其签名所用的算法等，如HMAC、SHA256、RSA，使用Base64编码组成
   * 载荷(Payload)：存放有效信息，不放用户敏感的信息，如密码，同样使用Base64编码
   * 签名(Signature)：需要使用编码后的header和payload加上我们提供的一个密钥，使用header中指定的签名算法(HS256)进行签名。签名的作用是保证JWT没有被篡改过
2. 特点：可以被看到，但是不能篡改，因为第三部分用了密钥。JWT常用于设计用户认证、授权系统、web的单点登录
``` 
long l = System.currentTimeMillis();
Date date = new Date(l+10000); //过期时间10s

JwtBuilder jwtBuilder = Jwts.builder()
       .setId("2023") //id
       .setSubject("testJwt") //主题
       .setIssuedAt(new Date()) //签发日期
       .setExpiration(date) // 过期时间
       .claim("userId",123)
       .signWith(SignatureAlgorithm.HS256, "haha23haha");
String jwt = jwtBuilder.compact();

Claims haha = Jwts.parser().setSigningKey("haha23haha").parseClaimsJws(jwt).getBody(); //解析
```
#### 密码加密存储
1. BCryptPasswordEncoder：自动加盐。只需把BCryptPasswordEncoder对象注入Spring容器中，SpringSecurity就会使用PasswordEncoder来进行密码校验
2. 报错：java.lang.ClassNotFoundException: javax.xml.bind.DatatypeConverter
   * 原因：jdk版本太高，javax.xml.bind在jdk8中有，但是在更高版本没有
   * 解决：在maven中添加依赖
   ``` 
     <dependency>
         <groupId>javax.xml.bind</groupId>
         <artifactId>jaxb-api</artifactId>
     </dependency> 
   ```
#### 认证过滤器
1. 获取token 
2. 解析token 
3. 获取userid 
4. 封装Authentication 
5. 存入SecurityContextHolder
#### 授权
1. 在SpringSecurity中，会使用默认的FilterSecurityInterceptor来进行权限校验
2. 在FilterSecurityInterceptor中会从SecurityContextHolder获取其中的Authentication，然后获取其中的权限信息
3. 步骤
   * UserDetailServiceImpl的loadUserByUsername 查询权限信息
   * JwtAuthenticationTokenFilter中放入权限信息
4. 实现：SpringSecurity为提供了基于注解的权限控制方案
   * 配置类中开启相关配置
   ``` 
    @EnableGlobalMethodSecurity(prePostEnabled = true)
   ```
   * 使用对应的注解：@PreAuthorize
   ``` 
    @GetMapping("/hello")
       @PreAuthorize("hasAnyAuthority('hello')")
       public String hello() {
           return "hello";
       } 
   ```
#### 自定义失败处理
1. ExceptionTranslationFilter捕获异常，判断是认证失败或授权失败
   * 认证失败：封装AuthenticationException，然后调用AuthenticationEntryPoint的commence方法处理
   * 授权失败：封装AccessDeniedException，然后调用AccessDeniedHandler的handle方法处理
2. 思路：自定义这两个类的异常处理机制的实现类，配置到SpringSecurity
   * 自定义实现类
   ``` 
   @Component
   public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
       @Override
       public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
           //给前端ResponseResult 的json
           ResponseResult responseResult = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "登陆认证失败了，请重新登陆！");
           String json = JSON.toJSONString(responseResult);
           WebUtils.renderString(response,json);
       }
   } 
   ```
   ``` 
   @Component
   public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
       @Override
       public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
           //给前端ResponseResult 的json
           ResponseResult responseResult = new ResponseResult(HttpStatus.FORBIDDEN.value(), "您权限不足！");
           String json = JSON.toJSONString(responseResult);
           WebUtils.renderString(response,json);
       }
   } 
   ```
   * 配置给SpringSecurity：先注入对应的处理器，再使用HttpSecurity对象的方法去配置
#### 跨域
1. 跨域：浏览器的同源策略(相同的协议、主机、端口号)，导致不能向其他域名发送异步请求
2. CORS解决跨域：controller加上@CrossOrigin注解
*** 
### Spring Data Jpa
#### 简介
1. Spring Data：提供了一套统一的数据访问API
2. Spring Dta Jpa：Spring Data对JPA封装之后的产物，目的在于简化JPA的数据访问技术
#### pojo类
1. 主键生成策略
   ``` 
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY) //主键生成策略
   ```
  * TABLE：使用一个特定的数据库表来保存主键 
  * SEQUENCE：使用特定的序列来生成主键 
  * IDENTITY：由数据库生成（主要用于自增主键） 
  * AUTO：由程序控制主键生成
2. @Column：设置实体类属性与数据表字段对应，若二者相同，可省略
3. JPA中保存和修改都用save，若id存在，则是修改。save方法修改时是全字段修改
#### 方法命名规则查询
1. Spring Data提供了一套方法命名规范，只要按照这个规范去定义方法名称，就能创建查询。Spring Data Jpa在执行时会解析方法名，自动生成查询语句进行查询
2. 命名规则：以findBy开头，涉及条件查询时，将对应的属性用关键字连接，属性首字母大写
``` 
List<Article> findByTitle(String title); 
List<Article> findByTitleContains(String title);
List<Article> findByTitleAndContentContains(String title, String content);
List<Article> findByIdBetween(Integer startId, Integer endId);
Page<Article> findByIdBetween(Integer startId, Integer endId, Pageable pageable);
```
#### JPQL查询
1. JPQL(Java Persistence Query Language)：JPA中定义的查询语言，类似于sql，但JPQL将表名和列名换成实体类的类名和属性名
2. 复杂的操作，可以使用@Query注解，结合JPQL完成查询
3. 使用 ?index 占位符，用来取出指定索引的参数，索引从1开始
4. 使用 :参数名称，结合@Param注解就可以为参数命名
``` 
    @Query("from Article ")
    List<Article> selectAll();

    @Query("from Article where id > ?1 and title like %?2%")
    List<Article> selectByCondition(Integer id, String title);

    @Query("from Article where id > :id and title like %:title%")
    List<Article> selectByCondition2(@Param("id") Integer id, @Param("title") String title); 
    
    @Query("from Article where id > ?#{[0].id} and title like %?#{[0].title}%")
    List<Article> selectByEntity(Article article);
    
    @Query("from Article where id > :#{#article.id} and title like %:#{#article.title}%")
    List<Article> selectByEntity2(Article article);
```
#### 本地SQL查询
``` 
@Query(value = "select * from article where id :#{#article.id}", nativeQuery = true)
List<Article> selectByNativeSql(Article article);
```
