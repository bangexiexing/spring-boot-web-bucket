package bucket.configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

//@Configuration
@PropertySource("classpath:datasource.properties")
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(){
        return DruidDataSourceBuilder.create().build();
    }
}
