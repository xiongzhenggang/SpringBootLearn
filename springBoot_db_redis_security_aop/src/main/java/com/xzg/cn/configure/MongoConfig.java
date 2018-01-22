package com.xzg.cn.configure;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

@Configuration
@EnableMongoRepositories(basePackages = "com.xzg.cn.repository",
                        repositoryImplementationPostfix = "Handler")//启用mongodb的repositry的作用，
public class MongoConfig  extends AbstractMongoConfiguration{//继承AbstractMongoConfiguration，会隐式的创建
    //mongodbTemplate
    @Autowired
    private Environment env;
    @Override
    public MongoClient mongoClient() {
        //配置mongodb服务器的访问凭证
        MongoCredential credential =
                MongoCredential.createCredential(
                        env.getProperty("mongo.username"),
                        "OrdersDB",
                        env.getProperty("mongo.password").toCharArray()
                );
        return new MongoClient(
                new ServerAddress("192.168.1.105",27017),
                Arrays.asList(credential));
    }

    @Override
    protected String getDatabaseName() {
        return "OrdersDB";//数据库名
    }
}
