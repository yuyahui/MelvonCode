package com.fanbei.until;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import net.sf.json.JSONObject;

public class EncryptString {
	
	//需要上传token的接口
	public  String getEnercy(JSONObject jsonParam,String username, String timen) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		
		 String appVersion="appVersion="+PropertiesHandle.readValue("appVersion");
		 String netType="netType="+PropertiesHandle.readValue("netType");		 
		 String id="id="+PropertiesHandle.readValue("id");
		 String userName="userName="+username;
		 String time="time="+timen;
		 
		 String token=PropertiesHandle.readValue("token");		
		/*
		加密前字符串= appVersion +netType + time +userName + token +参数按照字母排序 
		*/
		String[] head = {appVersion,netType,time,userName};
		//String[] body = {"password=bdf1160935b49080e43195dbacc75b42","phoneType=vivo X7","osType=android5.1.1","blackBox=eyJvcyI6ImFuZHJvaWQiLCJ2ZXJzaW9uIjoiMy4wLjUiLCJwYWNrYWdlcyI6ImNvbS5hbGZsLnd3d18zLjcuNiIsInByb2ZpbGVfdGltZSI6MTM4LCJpbnRlcnZhbF90aW1lIjo3OTgwMCwidG9rZW5faWQiOiJCWmR1djkzNldXbjVXZXNZTjBPOXZNOXJ1emNYejh2QzJRYmdTOHZINlZBSnh6NHlCdlMyT29PSEhZTlFLbVBYck9JSE01aE4rcG1vTktmUVB5ZFN2Zz09In0=","uuid=863389038473537"};
		//Arrays.sort(jsonParam,JSONObject.CASE_INSENSITIVE_ORDER);
		String sign= "";
		for(int i= 0;i<head.length;i++){
			if(i==head.length-1){
				sign = sign+head[i];
			}else{
			sign = sign+head[i]+"&";
			}
		}		
		sign=sign+token;
		
        JSONObject json=JsonUtils.getSortJson(jsonParam);////对传进来的json值根据key来进行排序
        String signs=JsonUtils.getAndJson(json, sign);  //对head值和body的值进行拼接      
       		
		Encrypt en= new Encrypt();
		String nSign = en.getSHA256Str(signs);//进行加密

		return nSign;		
	}
	
	//不需要上传token的接口
	public  String getEnercyNoToken(JSONObject jsonParam,String username, String timen) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		
		 String appVersion="appVersion="+PropertiesHandle.readValue("appVersion");
		 String netType="netType="+PropertiesHandle.readValue("netType");		
		 String id="id="+PropertiesHandle.readValue("id");
		 String userName="userName="+username;
		 String time="time="+timen;	
		
		 String[] head = {appVersion,netType,time,userName};
		 String sign= "";
		 for(int i= 0;i<head.length;i++){
			if(i==head.length-1){
				sign = sign+head[i];
			}else{
			sign = sign+head[i]+"&";
			}
		 }		
		
        JSONObject json=JsonUtils.getSortJson(jsonParam);
        String signs=JsonUtils.getAndJson(json, sign);        
      		
		Encrypt en= new Encrypt();
		String nSign = en.getSHA256Str(signs);
		
		return nSign;		
	}

}
