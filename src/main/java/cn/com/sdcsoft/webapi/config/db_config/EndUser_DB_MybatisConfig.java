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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "cn.com.sdcsoft.webapi.mapper.EndUser_DB",sqlSessionFactoryRef = "EndUser_DB_SqlSessionFactory")
public class EndUser_DB_MybatisConfig {
    @Bean(name = "EndUser_DB_DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.enduser-db")
    public DataSource EndUser_DB_DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "EndUser_DB_SqlSessionFactory")
    public SqlSessionFactory EndUser_DB_SqlSessionFactory(@Qualifier("EndUser_DB_DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //读取mybatis小配置文件
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "EndUser_DB_TransactionManager")
    public DataSourceTransactionManager EndUser_DB_TransactionManager(@Qualifier("EndUser_DB_DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "EndUser_DB_SqlSessionTemplate")
    public SqlSessionTemplate EndUser_DB_SqlSessionTemplate(@Qualifier("EndUser_DB_SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
