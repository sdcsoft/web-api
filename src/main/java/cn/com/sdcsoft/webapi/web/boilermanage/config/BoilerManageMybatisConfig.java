package cn.com.sdcsoft.webapi.web.boilermanage.config;

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
@MapperScan(basePackages = "cn.com.sdcsoft.webapi.web.boiler.mapper",sqlSessionFactoryRef = "boilerManageSqlSessionFactory")
public class BoilerManageMybatisConfig {
    @Bean(name = "boilerManageDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.boiler")
    @Primary
    public DataSource boilerManageDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "boilerManageSqlSessionFactory")
    @Primary
    public SqlSessionFactory boilerManageSqlSessionFactory(@Qualifier("boilerManageDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //读取mybatis小配置文件
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "boilerManageTransactionManager")
    @Primary
    public DataSourceTransactionManager boilerManageTransactionManager(@Qualifier("boilerManageDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "boilerManageSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate boilerManageSqlSessionTemplate(@Qualifier("boilerManageSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
