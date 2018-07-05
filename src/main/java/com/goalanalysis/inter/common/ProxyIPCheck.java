package com.goalanalysis.inter.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import org.apache.http.client.HttpClient;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.goalanalysis.inter.entity.ProxyIP;
@Service
public class ProxyIPCheck {
	public   List<ProxyIP>  checkip() throws IOException, ScriptException, ParseException {
	    Connection connect = Jsoup.connect("http://www.xicidaili.com/wt");
	    Map<String, String> header = new HashMap<String, String>();
        header.put("Host", "http://www.xicidaili.com");
        header.put("User-Agent", "Mozilla/4.0(compatible;MSIE7.0;WindowsNT5.1;Trident/4.0;SE2.XMetaSr1.0;SE2.XMetaSr1.0;.NETCLR2.0.50727;SE2.XMetaSr1.0)");
        header.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        header.put("Accept-Language", "zh-cn,zh;q=0.5");
        header.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
        Connection data = connect.headers(header);
        Document  document = data.timeout(0).ignoreHttpErrors(true).get();
        Element body=document.body();   
        Element els=body.getElementById("ip_list");
        Elements ipels=els.getElementsByTag("tr");
        List<ProxyIP> ipList=new ArrayList<ProxyIP>(); 
        int i=0;
        System.out.println(ipels.toString());
        for (Element ip:ipels) {
        	if (ip==ipels.get(0)) {continue;}
        	if (i>4||(ipels.indexOf(ip)>20)) {break;}
         	ProxyIP proxyIP=new ProxyIP();
        	proxyIP.setIp(ip.childNode(3).childNode(0).toString());
        	proxyIP.setPort(ip.childNode(5).childNode(0).toString());
        	proxyIP.setNo(i);
        	if (createIPAddress(proxyIP.getIp(),Double.valueOf(proxyIP.getPort()).intValue())) {
        		ipList.add(proxyIP);
        		i++;
        		System.out.println(proxyIP.toString()+"is OK");
        	}
        	else  {
        		System.out.println(proxyIP.toString()+"is BAD");

        	};
        }
        System.out.println(ipList.toString());
	return ipList;
}
	public Boolean createIPAddress(String ip,int port) {
		try {
            //http://1212.ip138.com/ic.asp 可以换成任何比较快的网页
           Document doc= Jsoup.connect("http://www.baidu.com")
                    .timeout(5000)
                    .proxy(ip, port)
                    .get();
            if (doc.getElementsContainingText("baidu").toString().length()>0) {
            return true;
            }
            else {return false;}
        } catch (Exception e) {
            return false;
        }
        }
    
}
