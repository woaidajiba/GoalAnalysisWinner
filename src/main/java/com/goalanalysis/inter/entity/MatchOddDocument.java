package com.goalanalysis.inter.entity;

import org.jsoup.nodes.Document;
import org.springframework.data.annotation.Id;

import lombok.Data;
@Data
public class MatchOddDocument {
 
	private int matchId;
	private Document document; 
	private String dateNum;
}
