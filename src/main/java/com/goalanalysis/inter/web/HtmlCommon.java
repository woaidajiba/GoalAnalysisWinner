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

import com.goalanalysis.inter.common.ProxyIPCheck;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.script.ScriptException;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
@Service
@Component
  public class HtmlCommon extends ProxyIPCheck {
	@Autowired 
	ProxyIPMapper proxyIPMapper;
 	@Autowired
	MatchOddDocumentMapper matchOddDocumentMapper;
	@Autowired
	MatchAnalysisDocumentMapper matchAnalysisDocumentMapper;
	@Autowired
	ProxyIPCheck proxyIPCheck;
	private static List<ProxyIP> ipList=new ArrayList<ProxyIP>();
	public   Document getDocument(String url) throws IOException, ScriptException, ParseException {
		if (ipList.isEmpty()) {
	             ipList=proxyIPCheck.checkip();
		}
		try {
			String ip=ipList.get(0).getIp();
			int port= new BigDecimal(ipList.get(0).getPort()).intValue();
		 
		 Document document = Jsoup.connect(url).proxy(ip, port).ignoreContentType(true).ignoreHttpErrors(true).timeout(5000).get();	    
		return document;
		}
		catch(Exception e) {
			 Document document = Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true).timeout(5000).get();	    
			 return document;
		}
	 	}
	
	public  String getDocumentText(String url) throws IOException, ScriptException, ParseException
	 {
		    Document document=getDocument(url);
		    String documentText = document.text();
	     return documentText;
	 }  
	public  Document getScoreTable() throws IOException, ScriptException, ParseException
	 {
		  long date=System.currentTimeMillis();
	 	  date = (date/1000-1)*1000; 	  
	 	  System.out.println(date);
	 	  String url ="http://score.nowscore.com/data/bf.js?"+date;		    
	 	 Document document = getDocument(url);
	     return document;
	 	 
	 }
	public Document getMatchAnalysis(int matchId) throws MongoException,IOException, ScriptException, ParseException {
	     	Document document =null;
 			 long date=System.currentTimeMillis();
		 	  date = (date/1000-1)*1000;
		  String url="http://score.nowscore.com/analysis/"+matchId+".html";	
		  List<MatchAnalysisDocument> matchAnalysisDocumentList=matchAnalysisDocumentMapper.findAllByMatchId(matchId);
		  if (!matchAnalysisDocumentList.isEmpty()) {
		  if (matchAnalysisDocumentList.size()>0&matchAnalysisDocumentList.get(0).getDocument().toString().length()>400) {
			   document =  Jsoup.parse(matchAnalysisDocumentList.get(0).getDocument());
		  }
		  else {
			  document =getDocument(url);
			  MatchAnalysisDocument matchAnalysisDocument=new MatchAnalysisDocument();
			  matchAnalysisDocument.setDateNum(Long.toString(date));
			  matchAnalysisDocument.setDocument(document.toString());
			  matchAnalysisDocument.setMatchId(matchId);
			  matchAnalysisDocumentMapper.insert(matchAnalysisDocument);
			  }
		  }
		  else {
		  document =getDocument(url);
		  MatchAnalysisDocument matchAnalysisDocument=new MatchAnalysisDocument();
		  matchAnalysisDocument.setDateNum(Long.toString(date));
		  matchAnalysisDocument.setDocument(document.toString());
		  matchAnalysisDocument.setMatchId(matchId);
		  matchAnalysisDocumentMapper.insert(matchAnalysisDocument);
		  }
		return document;
	}
	public Document getMatchOdd(int matchId) throws IOException, ScriptException, ParseException {
		 Document document =null;
 		 long date=System.currentTimeMillis();
	 	  date = (date/1000-1)*1000;
		  List<MatchOddDocument> matchOddDocumentList=matchOddDocumentMapper.findAllByMatchId(matchId);
		  if (!matchOddDocumentList.isEmpty()) {
		  if (matchOddDocumentList.size()>0&matchOddDocumentList.get(0).getDocument().toString().length()>400) {
			  document =  Jsoup.parse(matchOddDocumentList.get(0).getDocument());
		  }
		  else {
			 
			 
				  try {	
					  if (ipList.isEmpty()) {
				             ipList=proxyIPCheck.checkip();
					}
					  String ip=ipList.get(0).getIp();
						int port= new BigDecimal(ipList.get(0).getPort()).intValue();
					  
				  					
			String url="http://score.nowscore.com/odds/3in1Odds.aspx?companyid=3&id="+matchId+"&t=0";		
			    Connection connect = Jsoup.connect("http://score.nowscore.com/odds/3in1Odds.aspx?companyid=8&id="+matchId+"&t=0");
			    Map<String, String> header = new HashMap<String, String>();
		        header.put("Host", "http://score.nowscore.com/odds/3in1Odds.aspx");
		        header.put("User-Agent", "  Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0");
		        header.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		        header.put("Accept-Language", "zh-cn,zh;q=0.5");
		        header.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
		        header.put("Connection", "keep-alive");
		        Connection data = connect.headers(header);
		         document = data.timeout(5000).ignoreHttpErrors(true).proxy(ip,port).get();
		          MatchOddDocument matchOddDocument=new MatchOddDocument();
		          matchOddDocument.setDateNum(Long.toString(date));
		          matchOddDocument.setDocument(document.toString());
		          matchOddDocument.setMatchId(matchId);
		          matchOddDocumentMapper.insert(matchOddDocument);
				   }
				   catch(Exception e) {
					   String url="http://data.nowscore.com/odds/3in1Odds.aspx?companyid=3&id="+matchId+"&t=0";		
					    Connection connect = Jsoup.connect("http://data.nowscore.com/odds/3in1Odds.aspx?companyid=8&id="+matchId+"&t=0");
					    Map<String, String> header = new HashMap<String, String>();
				        header.put("Host", "http://data.nowscore.com/odds/3in1Odds.aspx");
				        header.put("User-Agent", "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Trident/4.0;SE2.XMetaSr1.0;SE2.XMetaSr1.0;.NETCLR2.0.50727;SE2.XMetaSr1.0)");
				        header.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				        header.put("Accept-Language", "zh-cn,zh;q=0.5");
				        header.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
				        header.put("Connection", "keep-alive");
				        Connection data = connect.headers(header);
				         document = data.timeout(5000).ignoreHttpErrors(true).get();
				         MatchOddDocument matchOddDocument=new MatchOddDocument();
				          matchOddDocument.setDateNum(Long.toString(date));
				          matchOddDocument.setDocument(document.toString());
				          matchOddDocument.setMatchId(matchId);
				          matchOddDocumentMapper.insert(matchOddDocument);			   }
			  }
		    }
		   
		  else {
			 
			  try {	
				  if (ipList.isEmpty()) {
			             ipList=proxyIPCheck.checkip();
				}
				  String ip=ipList.get(0).getIp();
					int port= new BigDecimal(ipList.get(0).getPort()).intValue();
				  
			  					
		String url="http://score.nowscore.com/odds/3in1Odds.aspx?companyid=3&id="+matchId+"&t=0";		
		    Connection connect = Jsoup.connect("http://score.nowscore.com/odds/3in1Odds.aspx?companyid=8&id="+matchId+"&t=0");
		    Map<String, String> header = new HashMap<String, String>();
	        header.put("Host", "http://score.nowscore.com/odds/3in1Odds.aspx");
	        header.put("User-Agent", "  Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0");
	        header.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        header.put("Accept-Language", "zh-cn,zh;q=0.5");
	        header.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
	        header.put("Connection", "keep-alive");
	        Connection data = connect.headers(header);
	         document = data.timeout(5000).ignoreHttpErrors(true).proxy(ip,port).get();
	          MatchOddDocument matchOddDocument=new MatchOddDocument();
	          matchOddDocument.setDateNum(Long.toString(date));
	          matchOddDocument.setDocument(document.toString());
	          matchOddDocument.setMatchId(matchId);
	          matchOddDocumentMapper.insert(matchOddDocument);
			   }
			   catch(Exception e) {
				   String url="http://data.nowscore.com/odds/3in1Odds.aspx?companyid=3&id="+matchId+"&t=0";		
				    Connection connect = Jsoup.connect("http://data.nowscore.com/odds/3in1Odds.aspx?companyid=8&id="+matchId+"&t=0");
				    Map<String, String> header = new HashMap<String, String>();
			        header.put("Host", "http://data.nowscore.com/odds/3in1Odds.aspx");
			        header.put("User-Agent", "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Trident/4.0;SE2.XMetaSr1.0;SE2.XMetaSr1.0;.NETCLR2.0.50727;SE2.XMetaSr1.0)");
			        header.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			        header.put("Accept-Language", "zh-cn,zh;q=0.5");
			        header.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
			        header.put("Connection", "keep-alive");
			        Connection data = connect.headers(header);
			         document = data.timeout(5000).ignoreHttpErrors(true).get();
			         MatchOddDocument matchOddDocument=new MatchOddDocument();
			          matchOddDocument.setDateNum(Long.toString(date));
			          matchOddDocument.setDocument(document.toString());
			          matchOddDocument.setMatchId(matchId);
			          matchOddDocumentMapper.insert(matchOddDocument);			   }
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
