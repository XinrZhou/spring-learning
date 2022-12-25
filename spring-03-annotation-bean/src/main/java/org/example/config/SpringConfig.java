package org.example.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@ComponentScan("org.example")
//@PropertySource("jdbc.properties")
@Import({JdbcConfig.class})
public class SpringConfig {

}
