package com.goalanalysis.inter.web;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goalanalysis.inter.entity.MatchAnalysisDocument;
import com.goalanalysis.inter.entity.MatchOddDocument;
import com.goalanalysis.inter.entity.ProxyIP;
import com.goalanalysis.inter.mapper.MatchAnalysisDocumentMapper;
import com.goalanalysis.inter.mapper.MatchOddDocumentMapper;
import com.goalanalysis.inter.mapper.ProxyIPMapper;
import com.mongodb.MongoException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
@Service
@Component

 public class HtmlCommon {
	@Autowired 
	ProxyIPMapper proxyIPMapper;
 	@Autowired
	MatchOddDocumentMapper matchOddDocumentMapper;
	@Autowired
	MatchAnalysisDocumentMapper matchAnalysisDocumentMapper;
	
	public   Document getDocument(String url) throws IOException {	 
		  
		 Document document = Jsoup.connect(url).ignoreContentType(true).timeout(5000).get();	    
		return document;
	 	}
	
	public  String getDocumentText(String url) throws IOException
	 {
		    Document document=getDocument(url);
		    String documentText = document.text();
	     return documentText;
	 }  
	public  Document getScoreTable() throws IOException
	 {
		  long date=System.currentTimeMillis();
	 	  date = (date/1000-1)*1000;
	 	  
	 	  System.out.println(date);
	 	  String url ="http://data.nowscore.com/data/bf.js?"+date;		    
	 	 Document document = getDocument(url);
	     return document;
	 }
	public Document getMatchAnalysis(int matchId) throws MongoException,IOException {
	     	Document document =null;
 			 long date=System.currentTimeMillis();
		 	  date = (date/1000-1)*1000;
		  String url="http://data.nowscore.com/analysis/"+matchId+".html";		
		  File f = new File("analysis"+matchId+"html");
		  if (f.exists()) {
			   document = Jsoup.parse(f, "UTF-8"); 
		  }
		  else {
		  document =getDocument(url);
		 
		  write("analysis"+matchId+"html",document.toString()); 
		  }
		return document;
	}
	public Document getMatchOdd(int matchId) throws IOException {
		 Document document =null;
 		 long date=System.currentTimeMillis();
	 	  date = (date/1000-1)*1000;
	 	  File f = new File("odd"+matchId+"html");
		  if (f.exists()) {
			 document = Jsoup.parse(f, "UTF-8"); 
		  }
		  else {
		String url="http://data.nowscore.com/odds/3in1Odds.aspx?companyid=3&id="+matchId+"&t=0";		
		    Connection connect = Jsoup.connect("http://data.nowscore.com/odds/3in1Odds.aspx?companyid=8&id="+matchId+"&t=0");
		    Map<String, String> header = new HashMap<String, String>();
	        header.put("Host", "http://data.nowscore.com/odds/3in1Odds.aspx");
	        header.put("User-Agent", "  Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0");
	        header.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        header.put("Accept-Language", "zh-cn,zh;q=0.5");
	        header.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
	        header.put("Connection", "keep-alive");
	        Connection data = connect.headers(header);
	         document = data.timeout(5000).get();
	       
			  write("odd"+matchId+"html",document.toString()); 
		  }
		return document;
	}
	 
 
 	public static boolean write(String path, String content) {  
        String s = new String();  
        String s1 = new String();  
        BufferedWriter output = null;  
        try {  
            File f = new File(path);  
            if (f.exists()) {  
            } else {  
                
              f.createNewFile() ;
            }  
            BufferedReader input = new BufferedReader(new FileReader(f));  
            while ((s = input.readLine()) != null) {  
                s1 += s + "\n";  
            }  
            System.out.println(s1);  
            input.close();  
            s1 += content;  
            output = new BufferedWriter(new FileWriter(f));  
            output.write(s1);  
            output.flush();  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        } finally {  
            if (output != null) {  
                try {  
                    output.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }   
}
