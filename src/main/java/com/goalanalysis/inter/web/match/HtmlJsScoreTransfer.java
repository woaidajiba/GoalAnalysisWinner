package com.goalanalysis.inter.web.match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.assertj.core.util.Lists;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
 
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalanalysis.inter.entity.MatchScoreDetail;

public class HtmlJsScoreTransfer {
    public List<MatchScoreDetail> getMatchScoreDetail(String js) throws IOException, ScriptException {
    	ScriptEngineManager manager = new ScriptEngineManager();   
    	MatchScoreDetailTransfer matchScoreDetailTransfer=new MatchScoreDetailTransfer();
    	ScriptEngine engine = manager.getEngineByName("javascript");   
    	String replaceString ="ShowBf();";
     	String StringToAdd ="";    	
        js=js.replace(replaceString, StringToAdd);
       replaceString ="A[0]";
     	StringToAdd =";A[0]";
        js=js.replace(replaceString, StringToAdd);
        engine.eval(js);
        //比赛详情
        ScriptObjectMirror obj=(ScriptObjectMirror) engine.get("A");
         //联赛详情
        ScriptObjectMirror objLeague=(ScriptObjectMirror) engine.get("B");
          List<Object> listObject=new ArrayList<Object>();
          List<Object> listObjectLeague=new ArrayList<Object>();
        for (Entry<String, Object> entry:obj.entrySet()) {
        	    listObject.add(entry.getValue());
        }
        for (Entry<String, Object> entry:objLeague.entrySet()) {
    	    listObjectLeague.add(entry.getValue());
        }
         List<MatchScoreDetail> mtchScoreDetail=new ArrayList<MatchScoreDetail>();
         for(int i=0;i<listObject.size();i++) {
        	 Map<String,Object> map=(Map<String, Object>) listObject.get(i);
        	 MatchScoreDetail match= matchScoreDetailTransfer.getMatchScoreDetail(map);
        	 //设置联赛名
        	 int j=(int)match.getLeague();
        	 Map<String,Object> mapLeague=(Map<String, Object>) listObjectLeague.get(j);
        	 if (mapLeague.get("1")!=null) {
        		 match.setLeagueName(mapLeague.get("1").toString());
        	 }
        	 mtchScoreDetail.add(match);
         }
     	//MatchScoreDetail mtchScoreDetail=(MatchScoreDetail) engine.eval(js);	
    	return mtchScoreDetail;
}
  
}
