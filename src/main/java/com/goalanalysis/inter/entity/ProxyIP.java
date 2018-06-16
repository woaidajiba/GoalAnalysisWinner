package com.goalanalysis.inter.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;
@Data
 @Document
  public class ProxyIP {
	 
	 private int   no;
     private String ip;
     private String port;
     private String type;
     private Date   catchTime;
}
