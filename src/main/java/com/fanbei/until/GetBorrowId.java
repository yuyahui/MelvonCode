package com.fanbei.until;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;

import com.google.gson.Gson;

public class GetBorrowId {
	
	static String baseUrl = PropertiesHandle.readValue("baseUrl");
	static String testUrl = "borrowCash/getBorrowCashList";
	                       		
	public static String getBorrowId(String userName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException, NoSuchAlgorithmException{
		
		String url = baseUrl+ testUrl;
		url = url.replaceAll("\\\\", "");
		
		JSONObject jsonParam=JSonBuilt.json_built(); 
		
		HTTPPost httpPost = new HTTPPost();
		String result=httpPost.httpPost(url, jsonParam, userName);
		
		Gson gs=new Gson();		
		TestJSonResult final_res=gs.fromJson(result, TestJSonResult.class);
		JSONObject dataResult = final_res.result;
		
		//获取返回值里的信息
		JSONObject data=(JSONObject) dataResult.get("data");
		JSONArray cashList = data.getJSONArray("cashList");
		JSONObject cashDetail=cashList.getJSONObject(0);
		int borrowId=cashDetail.getInt("rid");
		
		return String.valueOf(borrowId);	
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException, NoSuchAlgorithmException {
		 
		  String  borrowId= GetBorrowId.getBorrowId("13656648524");
		  System.out.print(borrowId);
		  
	}
			 
    

}
