package com.goalanalysis.inter.web.match;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.xmlunit.util.Nodes;

import com.goalanalysis.inter.entity.MatchOddDetail;

public class MatchOddDetailTransfer {
    public List<MatchOddDetail> getMatchOddDetail(Document doc,Date inputDate) throws ParseException {
    	try {
    
    	Element totalEls=doc.getElementById("oddsmain");
    	
    	if (totalEls.getElementsByClass("gts").hasClass("gts")==false) {
    		return null;
    		}
    	 
    	Elements oddEls=totalEls.getElementsByClass("gts");
    	
    	//大小球
    	Element oddDetailEls=oddEls.get(1);
    	Elements oddDetailTableEls1=oddDetailEls.getElementsByClass("gt1");
    	Elements oddDetailTableEls2=oddDetailEls.getElementsByClass("gt2");
    	List<MatchOddDetail> matchOddDetail=new ArrayList<MatchOddDetail>();
   	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    	for(Element els:oddDetailTableEls1) {
    		MatchOddDetail matchOddObject=new MatchOddDetail();
    		//获取盘口
    		Node odd=els.childNode(7);
    		String goalOddString=odd.childNode(0).toString();
    		if (goalOddString.equals("封")) {
    			matchOddObject.setGoalOdd(-1);
    		}
    		else {
    			  BigDecimal goalOdd=new BigDecimal("0");
    			  if (goalOddString.indexOf("/", 0)>0) {
    				  BigDecimal goalOddChg1=new BigDecimal(goalOddString.substring(0, goalOddString.indexOf("/", 0)));
    				  BigDecimal goalOddChg2=new BigDecimal(goalOddString.substring( goalOddString.indexOf("/", 0)+1,goalOddString.length()));
                      if (goalOddChg1.subtract(goalOddChg1.setScale(0, BigDecimal.ROUND_FLOOR)).doubleValue()>0.1) {
                    	  goalOdd=goalOdd.add(goalOddChg1.setScale(0, BigDecimal.ROUND_FLOOR)).add(new BigDecimal("0.75"));
                      }
                      else {
                    	  goalOdd=goalOdd.add(goalOddChg1.setScale(0, BigDecimal.ROUND_FLOOR)).add(new BigDecimal("0.25"));

                      }
    			  }
    			  else {
    				  goalOdd=new BigDecimal(goalOddString);
    			  }

    		matchOddObject.setGoalOdd(goalOdd.doubleValue());
    		}
    	   //获取时间
    		Node time=els.childNode(11);
     		String timeString=inputDate.getYear()-100+2000+"-"+time.childNode(0).toString() +" "+time.childNode(2).toString()+":"+"30";
    		Date date=formatter.parse(timeString);
    		matchOddObject.setDate(date);
    		//获取上盘水位
    		Node homeGoalOddValue=els.childNode(5);
     		if (homeGoalOddValue.childNodeSize()==0) {
    			matchOddObject.setHomeGoalOddValue(-1);
    		}
    		else {
    		matchOddObject.setHomeGoalOddValue(Double.valueOf(homeGoalOddValue.childNode(0).toString()));
    		}
    		//获取下盘水位
    		Node awayGoalOddValue=els.childNode(9);
     		if (awayGoalOddValue.childNodeSize()==0) {
    			matchOddObject.setAwayGoalOddValue(-1);
    		}
    		else {
    		matchOddObject.setAwayGoalOddValue(Double.valueOf(awayGoalOddValue.childNode(0).toString()));
    		}
     		Node oddStatus=els.childNode(13);
     		if (oddStatus.childNodeSize()==0) {
     			matchOddObject.setOddStatus(0);
     		}
     		else {
     			if (oddStatus.childNode(0).toString().equals("滚")) {
     			matchOddObject.setOddStatus(1);
     			}
     			else {
         			matchOddObject.setOddStatus(0);
     			}	
     		}
    		matchOddDetail.add(matchOddObject);
    	}
        for(Element els:oddDetailTableEls2) {
        	MatchOddDetail matchOddObject=new MatchOddDetail();
    		//获取盘口
    		Node odd=els.childNode(7);
    		String goalOddString=odd.childNode(0).toString();
       		if (goalOddString.equals("封")) {
    			matchOddObject.setGoalOdd(-1);
    		}
    		else {
     			  BigDecimal goalOdd=new BigDecimal("0");
    			  if (goalOddString.indexOf("/", 0)>0) {
    				  BigDecimal goalOddChg1=new BigDecimal(goalOddString.substring(0, goalOddString.indexOf("/", 0)));
    				  BigDecimal goalOddChg2=new BigDecimal(goalOddString.substring( goalOddString.indexOf("/", 0)+1,goalOddString.length()));
                      if (goalOddChg1.subtract(goalOddChg1.setScale(0, BigDecimal.ROUND_FLOOR)).doubleValue()>0.1) {
                    	  goalOdd=goalOdd.add(goalOddChg1.setScale(0, BigDecimal.ROUND_FLOOR)).add(new BigDecimal("0.75"));
                      }
                      else {
                    	  goalOdd=goalOdd.add(goalOddChg1.setScale(0, BigDecimal.ROUND_FLOOR)).add(new BigDecimal("0.25"));

                      }
    			  }
    			  else {
    				  goalOdd=new BigDecimal(goalOddString);
    			  }
    		matchOddObject.setGoalOdd(goalOdd.doubleValue());
    		}
    	   //获取时间
     		Node time=els.childNode(11);
     		String timeString=inputDate.getYear()-100+2000+"-"+time.childNode(0).toString() +" "+time.childNode(2).toString()+":"+"30";
    		Date date=formatter.parse(timeString);
    		matchOddObject.setDate(date);
    		//获取上盘水位
    		Node homeGoalOddValue=els.childNode(5);
     		if (homeGoalOddValue.childNodeSize()==0) {
    			matchOddObject.setHomeGoalOddValue(-1);
    		}
    		else {
    		matchOddObject.setHomeGoalOddValue(Double.valueOf(homeGoalOddValue.childNode(0).toString()));
    		}
    		//获取下盘水位
    		Node awayGoalOddValue=els.childNode(9);
    		if (awayGoalOddValue.childNodeSize()==0) {
    			matchOddObject.setAwayGoalOddValue(-1);
    		}
    		else {
    		matchOddObject.setAwayGoalOddValue(Double.valueOf(awayGoalOddValue.childNode(0).toString()));
    		}
    		Node oddStatus=els.childNode(13);
     		if (oddStatus.childNodeSize()==0) {
     			matchOddObject.setOddStatus(0);
     		}
     		else {
     			if (oddStatus.childNode(0).toString().equals("滚")) {
     			matchOddObject.setOddStatus(1);
     			}
     			else {
         			matchOddObject.setOddStatus(0);
     			}	
     		}
    		matchOddDetail.add(matchOddObject);
    	 
        ListSort(matchOddDetail);
    	}
    	return matchOddDetail;
    	}
    	catch(Exception e) {
    		return null;
    	}
    	
    }
    public void ListSort(List<MatchOddDetail> matchOddDetail){
		Collections.sort(matchOddDetail,new Comparator<MatchOddDetail>(){
			@Override
			public int compare(MatchOddDetail odd1,MatchOddDetail odd2){
				int flag = odd1.getDate().compareTo(odd2.getDate());
				return flag;
			}
		});
	}	
}
