package cn.com.sdcsoft.webapi;

import cn.com.sdcsoft.webapi.config.db_config.DeviceSettingMongoConfig;
import cn.com.sdcsoft.webapi.config.db_config.PrimaryMongoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableEurekaClient
@EnableFeignClients
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableCaching
@SpringBootApplication(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableConfigurationProperties({PrimaryMongoConfig.class,DeviceSettingMongoConfig.class})
public class WebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }

}
