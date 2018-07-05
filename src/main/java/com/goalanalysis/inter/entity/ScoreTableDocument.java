package com.goalanalysis.inter.entity;

 import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document
public class ScoreTableDocument{
	 
	private String document; 
	private String dateNum;
}
