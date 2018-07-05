package com.goalanalysis.inter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableConfigurationProperties
 @SpringBootApplication
 @EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) 
@EnableScheduling
public class GoalAnalysisWinnerApplication {
 	public static void main(String[] args) {
 		SpringApplication.run(GoalAnalysisWinnerApplication.class, args);
 		 
	}
 	@Bean      
 	 public ErrorPageFilter errorPageFilter() {     
 	      return new ErrorPageFilter();     
 	 }      
 	@Bean  
 	public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {          
 	      FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();             
 	      filterRegistrationBean.setFilter(filter);        
 	      filterRegistrationBean.setEnabled(false);        
 	      return filterRegistrationBean;    
 	}  
}
