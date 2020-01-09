package cn.com.sdcsoft.webapi.config.db_config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "cn.com.sdcsoft.webapi.mapper.Customer_DB", sqlSessionFactoryRef = "customerDBSqlSessionFactory")
public class Customer_DB_MybatisConfig {
    @Bean(name = "customerDBDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.customer-db")
    @Primary
    public DataSource customerDBDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "customerDBSqlSessionFactory")
    @Primary
    public SqlSessionFactory customerDBSqlSessionFactory(@Qualifier("customerDBDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //读取mybatis小配置文件
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "customerDBTransactionManager")
    @Primary
    public DataSourceTransactionManager customerDBTransactionManager(@Qualifier("customerDBDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "customerDBSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate customerDBSqlSessionTemplate(@Qualifier("customerDBSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
