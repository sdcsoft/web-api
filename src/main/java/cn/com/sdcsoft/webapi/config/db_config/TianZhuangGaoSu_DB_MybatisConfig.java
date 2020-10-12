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
@MapperScan(basePackages = {"cn.com.sdcsoft.webapi.TianZhuangGaoSu.mapper"}, sqlSessionFactoryRef = "TianZhuangGaoSu_DB_SqlSessionFactory")
public class TianZhuangGaoSu_DB_MybatisConfig {
    @Bean(name = "TianZhuangGaoSu_DB_DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.tianzhuanggaosu-db")
    public DataSource TianZhuangGaoSu_DB_DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "TianZhuangGaoSu_DB_SqlSessionFactory")
    public SqlSessionFactory TianZhuangGaoSu_DB_SqlSessionFactory(@Qualifier("TianZhuangGaoSu_DB_DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //读取mybatis小配置文件
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "TianZhuangGaoSu_DB_TransactionManager")
    public DataSourceTransactionManager TianZhuangGaoSu_DB_TransactionManager(@Qualifier("TianZhuangGaoSu_DB_DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "TianZhuangGaoSu_DB_SqlSessionTemplate")
    public SqlSessionTemplate TianZhuangGaoSu_DB_SqlSessionTemplate(@Qualifier("TianZhuangGaoSu_DB_SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
