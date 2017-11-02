package com.fanbei.until;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;

//获取返现余额
public class GetRebateAmount {
	
	static String baseUrl = PropertiesHandle.readValue("baseUrl");
	static String testUrl = "user/getMineInfo";	
	
	
	public static String getRebateAmount(String userName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException, NoSuchAlgorithmException{
		
		String url = baseUrl+ testUrl;
		url = url.replaceAll("\\\\", "");

		JSONObject jsonParam=JSonBuilt.json_built(); 
		
		HTTPPost httpPost = new HTTPPost();
		String result=httpPost.httpPost(url, jsonParam, userName);
		//System.out.println(result);
		
		Gson gs=new Gson();		
		TestJSonResult final_res=gs.fromJson(result, TestJSonResult.class);
		JSONObject dataResult = final_res.result;		
		JSONObject data=(JSONObject) dataResult.get("data");	
		String  rebateAmount=data.getString("rebateAmount");
		//System.out.println(rebateAmount);
		
		return String.valueOf(rebateAmount);
				
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException, NoSuchAlgorithmException {
		  
		GetRebateAmount.getRebateAmount("13656648524");
		
	}
	

}
