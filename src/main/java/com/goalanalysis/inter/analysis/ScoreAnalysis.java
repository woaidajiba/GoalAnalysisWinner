package com.goalanalysis.inter.analysis;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
@Service
public class ScoreAnalysis {
      public BigDecimal ScoreDocumentReader(Document doc) throws ScriptException, ParseException {
    	  ScriptEngineManager manager = new ScriptEngineManager();   
       	ScriptEngine engine = manager.getEngineByName("javascript");   
     	  Element els=doc.getElementById("teammain");
     	//  els=els.getElementById("table_v");
     	  String js=els.getElementsByIndexEquals(1).toString();
     	  String recentDataBegin="var v_data";
     	  String homeDataBegin="var h_data";
     	  String awayDataBegin="var a_data";
     	  String awayDataEnd="var ScoreAll";
     	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
     	  ParsePosition pos = new ParsePosition(1);
     	  String jsRecentData=js.substring(js.indexOf(recentDataBegin,0),js.indexOf(homeDataBegin,0)-2);
     	  engine.eval(jsRecentData);
          ScriptObjectMirror obj=(ScriptObjectMirror) engine.get("v_data");
          List<Object> listObject=new ArrayList<Object>();
          for (Entry<String, Object> entry:obj.entrySet()) {
          	    listObject.add(entry.getValue());
          }
          int recentGame=0;
          long recentGameGoal=0;
          BigDecimal recentGameCalcGoal=new BigDecimal("0");
          for (int i=0;i<listObject.size()&i<2;i++) {
        	  if( listObject.get(i) != null ) {
        	  Map<String,Object> map=(Map<String, Object>) listObject.get(i);
        	  String dataString=(String) map.get("0");
        	  Date   gameDate=formatter.parse(dataString);
        	  long  gameGoal=(int)map.get("8")+(int)map.get("9");   	  
        	  Date   currentTime=new Date();
        	  long day=(currentTime.getTime()-gameDate.getTime())/(24*60*60*1000);
        	  if (day>700) {
        		  gameGoal=0;
        	  }
        	  recentGameGoal=recentGameGoal+gameGoal;
        	  recentGame=recentGame+1;
        	  }
          }
          if (recentGame != 0) {
        	  recentGameCalcGoal= recentGameCalcGoal.add(new BigDecimal(Double.toString(recentGameGoal))).divide(new BigDecimal(recentGame),4,BigDecimal.ROUND_HALF_UP);
          }
          
     	  String jsHomeData=js.substring(js.indexOf(homeDataBegin,0),js.indexOf(awayDataBegin,0)-2);
     	  formatter = new SimpleDateFormat("yy-MM-dd");
     	 engine.eval(jsHomeData);
         obj=(ScriptObjectMirror) engine.get("h_data");
         listObject=new ArrayList<Object>();
         for (Entry<String, Object> entry:obj.entrySet()) {
         	    listObject.add(entry.getValue());
         }
         int homeGame=0;
         long homeGameGoal=0;
         BigDecimal homeGameCalcGoal=new BigDecimal("0");
         for (int i=0;i<listObject.size()&i<5;i++) {
       	  if( listObject.get(i) != null ) {
       	  Map<String,Object> map=(Map<String, Object>) listObject.get(i);
       	  String dataString=(String) map.get("0");
       	  Date   gameDate=formatter.parse(dataString);
       	  long  gameGoal=(int)map.get("8")+(int)map.get("9");   	  
       	  Date   currentTime=new Date();
       	  long day=(currentTime.getTime()-gameDate.getTime())/(24*60*60*1000);
       	  if (day>700) {
       		  gameGoal=0;
       	  }
       	  homeGameGoal=homeGameGoal+gameGoal;
       	  homeGame=homeGame+1;
       	  }
         }
         if (homeGame != 0) {
        	 homeGameCalcGoal= homeGameCalcGoal.add(new BigDecimal(Double.toString(homeGameGoal))).divide(new BigDecimal(homeGame),4,BigDecimal.ROUND_HALF_UP);
         }
     	  String jsAwayData=js.substring(js.indexOf(awayDataBegin,0),js.indexOf(awayDataEnd,0)-2);
     	 engine.eval(jsAwayData);
         obj=(ScriptObjectMirror) engine.get("a_data");
         listObject=new ArrayList<Object>();
         for (Entry<String, Object> entry:obj.entrySet()) {
         	    listObject.add(entry.getValue());
         }
         int awayGame=0;
         long awayGameGoal=0;
         BigDecimal awayGameCalcGoal=new BigDecimal("0");
         for (int i=0;i<listObject.size()&i<5;i++) {
       	  if( listObject.get(i) != null ) {
       	  Map<String,Object> map=(Map<String, Object>) listObject.get(i);
       	  String dataString=(String) map.get("0");
       	  Date   gameDate=formatter.parse(dataString);
       	  long  gameGoal=(int)map.get("8")+(int)map.get("9");   	  
       	  Date   currentTime=new Date();
       	  long day=(currentTime.getTime()-gameDate.getTime())/(24*60*60*1000);
       	  if (day>700) {
       		  gameGoal=0;
       	  }
       	  awayGameGoal=awayGameGoal+gameGoal;
        	awayGame=awayGame+1;
       	  }
         }
         if (awayGame != 0) {
        	 awayGameCalcGoal=awayGameCalcGoal.add(new BigDecimal(Double.toString(awayGameGoal))).divide(new BigDecimal(awayGame),4,BigDecimal.ROUND_HALF_UP);
         }
         BigDecimal finalGoal=new BigDecimal("0");
         
         if (awayGameCalcGoal.subtract(new BigDecimal("0")).doubleValue()>0.1&homeGameCalcGoal.subtract(new BigDecimal("0")).doubleValue()>0.1) {
            if (recentGameCalcGoal.subtract(new BigDecimal("0")).doubleValue()<0.1) {
             finalGoal=awayGameCalcGoal.add(homeGameCalcGoal).divide(new BigDecimal(2),4,BigDecimal.ROUND_HALF_UP);
             }
            else {  
              finalGoal=awayGameCalcGoal.add(homeGameCalcGoal).divide(new BigDecimal(2),4,BigDecimal.ROUND_HALF_UP).add(recentGameCalcGoal).divide(new BigDecimal(2),4,BigDecimal.ROUND_HALF_UP);
             }
         }
          return finalGoal;      
      }
}
