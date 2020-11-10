package cn.com.sdcsoft.webapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.devicesetting")
public class DtuSettingMongoConfig extends  AbstractMongoConfig{

    @Override
    @Bean(name = "dtuSettingMongoTemplate")
    public MongoTemplate getMongoTemplate() throws Exception {
        return new MongoTemplate(mongoDatabaseFactory());
    }
}
