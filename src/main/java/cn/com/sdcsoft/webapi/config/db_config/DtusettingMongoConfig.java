package cn.com.sdcsoft.webapi.config.db_config;

import cn.com.sdcsoft.webapi.config.AbstractMongoConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.secondary")
public class DtusettingMongoConfig extends AbstractMongoConfig {

    @Override
    @Bean(name = "secondaryMongoTemplate")
    public MongoTemplate getMongoTemplate() throws Exception {
        return new MongoTemplate(mongoDatabaseFactory());
    }
}
