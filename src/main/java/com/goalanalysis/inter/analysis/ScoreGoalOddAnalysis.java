package com.goalanalysis.inter.analysis;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goalanalysis.inter.entity.MatchOddDetail;
import com.goalanalysis.inter.entity.MatchScoreDetail;
import com.goalanalysis.inter.web.HtmlCommon;
import com.goalanalysis.inter.web.match.HtmlJsScoreTransfer;
import com.goalanalysis.inter.web.match.MatchOddDetailTransfer;
@Service
public class ScoreGoalOddAnalysis {
	@Autowired
	HtmlCommon htmlcommon;
      public String getGoalOddAdvise() throws IOException, ScriptException, ParseException {
     	   ScoreAnalysis scoreAnalysis=new ScoreAnalysis();
    	   MatchOddDetailTransfer matchOddDetailTransfer=new MatchOddDetailTransfer();
    	  HtmlJsScoreTransfer htmlJsScoreTransfer= new HtmlJsScoreTransfer();
     	  Document doc=null;	  
    	  Elements els=null;
    	  String text="text";
    	  List<MatchScoreDetail> matchScoreDetail=new ArrayList<MatchScoreDetail>();
    	  try {
    	  if (text.indexOf("var", 0)<0) {
    		  doc=htmlcommon.getScoreTable();
    		  els=doc.getElementsByTag("body");
        	  text=els.text();	  
    	      matchScoreDetail=htmlJsScoreTransfer.getMatchScoreDetail(text);
    	  }
    	  }
    	  catch(Exception e ) {
    		  doc=htmlcommon.getScoreTable();
    		  els=doc.getElementsByTag("body");
        	  text=els.text();	  
    	      matchScoreDetail=htmlJsScoreTransfer.getMatchScoreDetail(text);
    	  }
    	 
    	   List<String> matchAnalysis=new ArrayList<String>();
   	   String returnText="";
   	    for(int i=0;i<matchScoreDetail.size();i++) {
   	    	 if ( matchScoreDetail.get(i).getGameStatus()==-1) {
   	    		 break;
   	    	 }
   	    	 else {
   	    		 BigDecimal calcGoal=null;
   	    	 doc=htmlcommon.getMatchAnalysis(matchScoreDetail.get(i).getMatchId());
   	    	 try {
   	    	  calcGoal=scoreAnalysis.ScoreDocumentReader(doc);
   	    	 }
   	    	 catch(Exception e) {
   	    		 System.out.println(doc.toString());
   	    	 }
   	    	 calcGoal=calcGoal.setScale(2,BigDecimal.ROUND_HALF_UP);
    	    	 Double calc=calcGoal.setScale(2,BigDecimal.ROUND_HALF_UP).subtract(calcGoal.setScale(0,BigDecimal.ROUND_FLOOR)).doubleValue();
    	    	 if (calc>0.8) {
    	    		calcGoal=calcGoal.setScale(0,BigDecimal.ROUND_HALF_UP);
    	    	 }
    	    	 else if(calc>0.6) {
    	    		calcGoal=calcGoal.setScale(0,BigDecimal.ROUND_FLOOR).add(new BigDecimal("0.75"));
    	    	 }
    	    	 else if(calc>0.4) {
     	    		calcGoal=calcGoal.setScale(0,BigDecimal.ROUND_FLOOR).add(new BigDecimal("0.5"));
    	    	 }
    	    	 else if(calc>0.1) {
      	    		calcGoal=calcGoal.setScale(0,BigDecimal.ROUND_FLOOR).add(new BigDecimal("0.25"));
    	    	 }
    	    	 else {
       	    		calcGoal=calcGoal.setScale(0,BigDecimal.ROUND_FLOOR);
    	    	 }
    	    	 Document oddsDoc=htmlcommon.getMatchOdd(matchScoreDetail.get(i).getMatchId());
        	   	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss");

        	      Date inputDate=formatter.parse(matchScoreDetail.get(i).getTimeString());
        	      formatter =new SimpleDateFormat("yyyy-MM-dd");
        	      String inputDateString =formatter.format(inputDate);
        	      inputDate=formatter.parse(inputDateString);
        	      if (inputDate.getMonth()!=12) {
        	      inputDate.setMonth(inputDate.getMonth()+1);
        	      }
        	      //
        	      else   {
        	    	  inputDate.setYear(inputDate.getYear()+1);
        	    	  inputDate.setMonth(1);
        	      };
      	    	 int goalFlag=0;
         	     List<MatchOddDetail> matchOddDetail=matchOddDetailTransfer.getMatchOddDetail(oddsDoc,inputDate);
         	     if (matchOddDetail==null) {}
         	     else {
         	     if (matchOddDetail.size()!=0) {
         	    	 int flag=0;
         	    	 double goalOddTemp=matchOddDetail.get(0).getGoalOdd();
         	    	 int currentNum=0;
         	    	 for (int k=0;k<matchOddDetail.size();k++) {
         	    		 if (goalOddTemp>matchOddDetail.get(k).getGoalOdd()&matchOddDetail.get(k).getOddStatus()==0) {
     	    				 goalFlag=1;
     	    			 }
         	    		 if (matchOddDetail.get(k).getOddStatus()==1) {
         	    			 flag=1;
         	    			
         	    			 if (k>1) {
         	    			 currentNum=k-1;
         	    			 }
         	    			 else {
         	    				 currentNum=0;
         	    				 }
         	    			 break;
         	    		 }
         	    	 }
         	    	 
         	    matchScoreDetail.get(i).setGoalOdd(matchOddDetail.get(currentNum).getGoalOdd());
         	     }
         	    
    	    	 if (calcGoal.doubleValue()<matchScoreDetail.get(i).getGoalOdd()&matchScoreDetail.get(i).getGoalOdd()!=0
    	    			 &matchScoreDetail.get(i).getGameStatus()!=-1&goalFlag==0
    	    			 &(matchScoreDetail.get(i).getAwayTeamGoal()+matchScoreDetail.get(i).getHomeTeamGoal())<matchScoreDetail.get(i).getGoalOdd()
    	    			 ) {
    	    		returnText=returnText+matchScoreDetail.get(i).getLeagueName()+"--"+matchScoreDetail.get(i).getStartTime()+"--"+matchScoreDetail.get(i).getHomeTeamSname()+"----"+matchScoreDetail.get(i).getAwayTeamSname()+":计算得出大小球为"+calcGoal+"盘口开出："+matchScoreDetail.get(i).getGoalOdd();
    	    	    returnText=returnText+"\n";
        	        System.out.println(matchScoreDetail.get(i).getLeagueName()+"--"+matchScoreDetail.get(i).getStartTime()+"--"+matchScoreDetail.get(i).getHomeTeamSname()+"----"+matchScoreDetail.get(i).getAwayTeamSname()+":计算得出大小球为"+calcGoal+"盘口开出："+matchScoreDetail.get(i).getGoalOdd());

    	    	 }
    	   } 
   	    	 }
   	    }
          return returnText;         
      }
}
