package cn.com.sdcsoft.webapi.web.weixinmanage.config;

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
@MapperScan(basePackages = "cn.com.sdcsoft.webapi.web.weixinmanage.mapper",sqlSessionFactoryRef = "wxManageApiSqlSessionFactory")
public class WeiWinManage_MybatisConfig {
    @Bean(name = "wxManageApiDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.wxmanage")
    public DataSource wxManageApiDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "wxManageApiSqlSessionFactory")
    public SqlSessionFactory wxManageApiSqlSessionFactory(@Qualifier("wxManageApiDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //读取mybatis小配置文件
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "wxManageApiTransactionManager")
    public DataSourceTransactionManager wxManageApiTransactionManager(@Qualifier("wxManageApiDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "wxManageApiSqlSessionTemplate")
    public SqlSessionTemplate wxManageApiSqlSessionTemplate(@Qualifier("wxManageApiSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
