package com.goalanalysis.inter.web;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.script.ScriptException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goalanalysis.inter.entity.MatchScore;
import com.goalanalysis.inter.entity.MatchScoreDetail;
import com.goalanalysis.inter.entity.ProxyIP;
import com.goalanalysis.inter.mapper.MatchScoreMapper;
import com.goalanalysis.inter.mapper.ProxyIPMapper;
import com.goalanalysis.inter.analysis.ScoreAnalysis;
import com.goalanalysis.inter.analysis.ScoreGoalOddAnalysis;

@RestController

public class HtmlController {
	@Autowired
	ScoreGoalOddAnalysis scoreGoalOddAnalysis;
	
	@RequestMapping("/test")
    @Scheduled(cron="0 0 * * * ?")
   public  String test()  throws IOException, ScriptException, ParseException {
		String advise=scoreGoalOddAnalysis.getGoalOddAdvise();
		return advise;
   }
  
    
}
