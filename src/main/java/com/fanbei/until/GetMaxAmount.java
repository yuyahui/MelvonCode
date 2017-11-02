package com.fanbei.until;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;

//获取可用信用额度
public class GetMaxAmount {
	
	static String baseUrl = PropertiesHandle.readValue("baseUrl");
	static String testUrl = "borrowCash/getBowCashLogInInfo";
	
	
	public static double getAmount(String userName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException, NoSuchAlgorithmException{
		
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
		String max=data.getString("maxAmount");
		double maxAmount=Double.valueOf(data.getString("maxAmount"));
		
		return maxAmount;		
	}
	
	  public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException, NoSuchAlgorithmException {
		  
		 String userName=PropertiesHandle.readValue("userName");
		  double maxAmount= GetMaxAmount.getAmount(userName);
		  System.out.print(maxAmount);
			 
		    }
	

}
