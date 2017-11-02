package com.fanbei.until;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;

//获取需还款金额
public class GetrRpaymentAmount {
	
	static String baseUrl = PropertiesHandle.readValue("baseUrl");
	static String testUrl = "borrowCash/getBowCashLogInInfo";	
	
	
	public static String getrRpaymentAmount(String userName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException, NoSuchAlgorithmException{
				
		String url = baseUrl+ testUrl;
		url = url.replaceAll("\\\\", "");

		JSONObject jsonParam=JSonBuilt.json_built(); 
		HTTPPost httpPost = new HTTPPost();
		String result=httpPost.httpPost(url, jsonParam, userName);
		
		Gson gs=new Gson();		
		TestJSonResult final_res=gs.fromJson(result, TestJSonResult.class);
		JSONObject dataResult = final_res.result;		
		JSONObject data=(JSONObject) dataResult.get("data");	
		String  returnAmount=data.getString("returnAmount");
		return String.valueOf(returnAmount);				
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException, NoSuchAlgorithmException {
		  
		GetrRpaymentAmount.getrRpaymentAmount("13656648524");
		
	}
	

}
