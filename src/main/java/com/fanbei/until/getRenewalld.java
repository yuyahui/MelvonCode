package com.fanbei.until;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import com.google.gson.Gson;

//获取续借id
public class getRenewalld {
	
	static String baseUrl = PropertiesHandle.readValue("baseUrl");
	static String testUrl = "borrowCash/getRenewalList";		
	public static String getRenewalList(String userName,String borrowId) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException, NoSuchAlgorithmException{
		
		
		String url = baseUrl+ testUrl;
		url = url.replaceAll("\\\\", "");

		JSONObject jsonParam=JSonBuilt.json_built(); 
		jsonParam.put("borrowId", borrowId);
		
		HTTPPost httpPost = new HTTPPost();
		String result=httpPost.httpPost(url, jsonParam, userName);
		System.out.println(result);
		
		Gson gs=new Gson();		
		TestJSonResult final_res=gs.fromJson(result, TestJSonResult.class);
		JSONObject dataResult = final_res.result;
		
		JSONObject data=(JSONObject) dataResult.get("data");
		JSONArray renewalList = data.getJSONArray("renewalList");
		JSONObject cashDetail=renewalList.getJSONObject(0);
		int rid=cashDetail.getInt("rid");
		return String.valueOf(rid);
				
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException, NoSuchAlgorithmException {
		  
		  String  borrowId= GetBorrowId.getBorrowId("17612158083");
		  String  renewalId= getRenewalld.getRenewalList("17612158083",borrowId);
		  System.out.println(renewalId);
		  
	}
	

}
