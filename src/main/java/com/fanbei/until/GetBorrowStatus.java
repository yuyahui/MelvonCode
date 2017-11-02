package com.fanbei.until;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;

import com.google.gson.Gson;

public class GetBorrowStatus {
	
	static String baseUrl = PropertiesHandle.readValue("baseUrl");
	static String testUrl = "borrowCash/getBowCashLogInInfo";	
	static String userName = PropertiesHandle.readValue("userName");
	
   public static String getBorrowStatus() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException, NoSuchAlgorithmException{
		
		String url = baseUrl+ testUrl;
		url = url.replaceAll("\\\\", "");
		
		JSONObject jsonParam=JSonBuilt.json_built(); 		
		HTTPPost httpPost = new HTTPPost();
		String result=httpPost.httpPost(url, jsonParam, userName);
		System.out.println(result);
		
		Gson gs=new Gson();		
		TestJSonResult final_res=gs.fromJson(result, TestJSonResult.class);
		JSONObject dataResult = final_res.result;
		JSONObject data=dataResult.getJSONObject("data");
		String status=data.getString("status");
		return status;			
	}
   
   public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, ClientProtocolException, NoSuchAlgorithmException, IOException {
	   
	   GetBorrowStatus.getBorrowStatus();	      
   		     		        
    }

}
