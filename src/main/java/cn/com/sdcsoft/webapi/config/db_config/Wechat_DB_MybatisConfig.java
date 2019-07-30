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
@MapperScan(basePackages = "cn.com.sdcsoft.webapi.mapper.Wechat_DB",sqlSessionFactoryRef = "Wechat_DB_SqlSessionFactory")
public class Wechat_DB_MybatisConfig {
    @Bean(name = "Wechat_DB_DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.wechat-db")
    public DataSource Wechat_DB_DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "Wechat_DB_SqlSessionFactory")
    public SqlSessionFactory Wechat_DB_SqlSessionFactory(@Qualifier("Wechat_DB_DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //读取mybatis小配置文件
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "Wechat_DB_TransactionManager")
    public DataSourceTransactionManager Wechat_DB_TransactionManager(@Qualifier("Wechat_DB_DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "Wechat_DB_SqlSessionTemplate")
    public SqlSessionTemplate Wechat_DB_SqlSessionTemplate(@Qualifier("Wechat_DB_SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
