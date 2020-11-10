package com.aichaowei.dtu.newsetting.config;

import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

public abstract class AbstractMongoConfig {
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    //连接MongoDB地址
    protected String uri;

    /**
     * 获取mongoDBTtemplate对象
     */
    public abstract MongoTemplate getMongoTemplate() throws Exception;

    /**
     * 创建mongoDb工厂
     */
    public MongoDatabaseFactory mongoDatabaseFactory() throws Exception {
        return new SimpleMongoClientDatabaseFactory(uri);
    }
}