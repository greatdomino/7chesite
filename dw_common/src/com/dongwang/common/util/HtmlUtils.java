package com.dongwang.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlUtils {
	// È¡HTMLÔ´´úÂë
	public static String getHtmlSource(String surl){
	URL url;
    BufferedReader reader;
    String s, str = null;
    try {
        url = new URL(surl);
        HttpURLConnection cont = (HttpURLConnection) url.openConnection();
        cont.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");            
        cont.connect();
        reader = new BufferedReader(new InputStreamReader(cont.getInputStream()));
        
        StringBuffer temp = new StringBuffer();
        while((s=reader.readLine())!=null) {
        	temp.append(new String(s.getBytes(), "UTF-8"));
        	str = new String(temp);
            
        }        
        
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
	return str;
	}
}
