package com.goalanalysis.inter;

 
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MongoConfig {
	 //@Value("${mongo.host}")  
	   @Value("mongodb://"+"localhost:27017"+"/"+"goal")  
	   private String MONGO_URI;  
	  
	  
	   @Bean  
	   public MongoMappingContext mongoMappingContext() {  
	      MongoMappingContext mappingContext = new MongoMappingContext();  
	      return mappingContext;  
	   }  
	  
	   // ==================== 连接到 mongodb1 服务器 ======================================  
	  
	   @Bean //使用自定义的typeMapper去除写入mongodb时的“_class”字段  
	   public MappingMongoConverter mappingMongoConverter() throws Exception {  
	      DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(this.mongoDbFactory());  
	      MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, this.mongoMappingContext());  
	      converter.setTypeMapper(new DefaultMongoTypeMapper(null));  
	      return converter;  
	   }  
	  
	   @Bean  
	   public MongoDbFactory mongoDbFactory() throws UnknownHostException {  
	      return new SimpleMongoDbFactory(new MongoClientURI(MONGO_URI));  
	   }  
 	   @Bean  
	   public MongoTemplate mongoTemplate() throws Exception {  
	      return new MongoTemplate(this.mongoDbFactory(), this.mappingMongoConverter());  
	   }  
}