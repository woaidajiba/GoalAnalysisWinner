package com.goalanalysis.inter.entity;

 import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document
public class MatchAnalysisDocument {
 
	private String document; 
	private int matchId;
	private String dateNum;
}
