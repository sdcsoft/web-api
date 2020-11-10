package cn.com.sdcsoft.webapi.config;


import com.mongodb.MongoClientURI;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;


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
    public MongoDbFactory mongoDatabaseFactory() throws Exception {
        MongoClientURI mongoclienturi = new MongoClientURI(uri);
        return new SimpleMongoDbFactory(mongoclienturi);
    }
}