package com.fanbei.until;

import net.sf.json.JSONObject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * 此函数的作用是输入一个url和head会返回一个json格式
 */

public class HTTPPost {	
	   
	public static String httpPost(String url,JSONObject jsonParam,String userName)  throws ClientProtocolException, IOException, NoSuchAlgorithmException {
		  
	    String appVersion=PropertiesHandle.readValue("appVersion");
		String netType=PropertiesHandle.readValue("netType");		
		String id=PropertiesHandle.readValue("id");
		String time=Long.toString(System.currentTimeMillis());
				
		EncryptString en= new EncryptString();
		String sign=en.getEnercy(jsonParam,userName,time);
		      
    	HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httppost = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);	        	    	 	        			
		
        httppost.setHeader("userName", userName);
        httppost.setHeader("appVersion", appVersion);
        httppost.setHeader("netType", netType);
        httppost.setHeader("sign", sign);
        httppost.setHeader("time", time);
        httppost.setHeader("id", id);        
        /*
        for (Header header : httppost.getAllHeaders()) {
        	System.out.println(header.getName()+" "+header.getValue());			
		}
		*/	        
        HttpResponse httpResponse;
        httpResponse = closeableHttpClient.execute(httppost);
        HttpEntity httpEntity = httpResponse.getEntity();
        String result = null;
        result = EntityUtils.toString(httpEntity, "UTF-8");
        closeableHttpClient.close();
        return result;
    } 
	
	public static String httpPostNoToken(String url,JSONObject jsonParam,String userName)  throws ClientProtocolException, IOException, NoSuchAlgorithmException {
		  
	    String appVersion=PropertiesHandle.readValue("appVersion");
		String netType=PropertiesHandle.readValue("netType");
		String time=Long.toString(System.currentTimeMillis());
		String id=PropertiesHandle.readValue("id");
		
		EncryptString en= new EncryptString();
		String sign=en.getEnercyNoToken(jsonParam,userName,time);
		System.out.println(sign);		
        
    	HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httppost = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);	        	    	 	        			
		
        httppost.setHeader("userName", userName);
        httppost.setHeader("appVersion", appVersion);
        httppost.setHeader("netType", netType);
        httppost.setHeader("sign", sign);
        httppost.setHeader("time", time);
        httppost.setHeader("id", id);        
        /*
        for (Header header : httppost.getAllHeaders()) {
        	System.out.println(header.getName()+" "+header.getValue());			
		}
		*/	        
        HttpResponse httpResponse;
        httpResponse = closeableHttpClient.execute(httppost);
        HttpEntity httpEntity = httpResponse.getEntity();
        String result = null;
        result = EntityUtils.toString(httpEntity, "UTF-8");
        closeableHttpClient.close();
        return result;
    }
    
}
