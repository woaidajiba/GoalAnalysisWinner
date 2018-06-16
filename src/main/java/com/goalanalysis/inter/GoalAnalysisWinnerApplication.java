package com.goalanalysis.inter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@EnableConfigurationProperties
 @SpringBootApplication
 @EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) 
public class GoalAnalysisWinnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoalAnalysisWinnerApplication.class, args);
		 
	}
}
