package com.goalanalysis.inter.web;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import org.jsoup.Connection;
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
import com.goalanalysis.inter.common.ProxyIPCheck;

@RestController
public class HtmlController {
	@Autowired
	ScoreGoalOddAnalysis scoreGoalOddAnalysis;
	@Autowired
	ProxyIPCheck proxyIPCheck;
	@RequestMapping("/test")
	 @Scheduled(cron = "0 0 13 * * *")
   public  String test()  throws IOException, ScriptException, ParseException {
     	String advise=scoreGoalOddAnalysis.getGoalOddAdvise();
		return advise;
   }
    @RequestMapping("/testip")
    public String testip() throws IOException, ScriptException, ParseException {
    	 System.setProperty("http.proxyHost", "116.213.98.6");
    	 System.setProperty("http.proxyPort", "8080");  
    	 Connection connect = Jsoup.connect("http://api.xicidaili.com/free2016.txt");
		    Map<String, String> header = new HashMap<String, String>();
	        header.put("Host", "http://api.xicidaili.com");
	        header.put("User-Agent", "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Trident/4.0;SE2.XMetaSr1.0;SE2.XMetaSr1.0;.NETCLR2.0.50727;SE2.XMetaSr1.0)");
	        header.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        header.put("Accept-Language", "zh-cn,zh;q=0.5");
	        header.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
	        header.put("Connection", "keep-alive");
	        Connection data = connect.headers(header);
	        Document  document = data.timeout(5000).ignoreHttpErrors(true).get();
            return document.toString();
    }
    @RequestMapping("/checkip")
    public   List<ProxyIP>  checkip() throws IOException, ScriptException, ParseException {
    	    Connection connect = Jsoup.connect("http://www.xicidaili.com/wt");
		    Map<String, String> header = new HashMap<String, String>();
	        header.put("Host", "http://www.xicidaili.com");
	        header.put("User-Agent", "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Trident/4.0;SE2.XMetaSr1.0;SE2.XMetaSr1.0;.NETCLR2.0.50727;SE2.XMetaSr1.0)");
	        header.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        header.put("Accept-Language", "zh-cn,zh;q=0.5");
	        header.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
	        header.put("Connection", "keep-alive");
	        Connection data = connect.headers(header);
	        Document  document = data.timeout(5000).ignoreHttpErrors(true).get();
            Element body=document.body();   
            Element els=body.getElementById("ip_list");
            Elements ipels=els.getElementsByTag("tr");
            List<ProxyIP> ipList=new ArrayList<ProxyIP>(); 
            int i=0;
            for (Element ip:ipels) {
            	if (ip==ipels.get(0)) {continue;}
            	if (i>4) {break;}
             	ProxyIP proxyIP=new ProxyIP();
            	proxyIP.setIp(ip.childNode(3).childNode(0).toString());
            	proxyIP.setPort(ip.childNode(5).childNode(0).toString());
            	proxyIP.setNo(i);
            	if (proxyIPCheck.createIPAddress(proxyIP.getIp(),Double.valueOf(proxyIP.getPort()).intValue())) {
            		ipList.add(proxyIP);
            		i++;
            	};
            }
            System.out.println(ipList.toString());
		return ipList;
    }
    
}
