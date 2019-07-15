package cn.com.sdcsoft.webapi.wechat.config;

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
@MapperScan(basePackages = "cn.com.sdcsoft.webapi.wechat.mapper",sqlSessionFactoryRef = "wxManageSqlSessionFactory")
public class WxManageMybatisConfig {
    @Bean(name = "wxManageDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.wxmanage")
    public DataSource wxManageDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "wxManageSqlSessionFactory")
    public SqlSessionFactory wxManageSqlSessionFactory(@Qualifier("wxManageDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //读取mybatis小配置文件
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "wxManageTransactionManager")
    public DataSourceTransactionManager wxManageTransactionManager(@Qualifier("wxManageDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "wxManageSqlSessionTemplate")
    public SqlSessionTemplate wxManageSqlSessionTemplate(@Qualifier("wxManageSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
