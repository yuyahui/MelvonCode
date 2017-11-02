package com.fanbei.borrowCash;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fanbei.until.GetBorrowId;
import com.fanbei.until.HTTPPost;
import com.fanbei.until.JSonBuilt;
import com.fanbei.until.PropertiesHandle;
import com.fanbei.until.TestJSonResult;
import com.google.gson.Gson;

//申请续借
public class ApplyRenewalTest {
	
	static String baseUrl = PropertiesHandle.readValue("baseUrl");
	static String testUrl = "borrowCash/applyRenewal";	
	String userName =PropertiesHandle.readValue("userName");
	
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClientProtocolException, NoSuchAlgorithmException, SQLException, IOException{
		return new Object[][]{
				{GetBorrowId.getBorrowId(userName),"1000","成功"},//正确的借款id
				{GetBorrowId.getBorrowId(userName)+"a","9999","服务器操作错误"},//不存在的借款id
				{"","9999","服务器操作错误"}//借款id为空
		};		
	}
	
	@Test(dataProvider="ex")
	public void applyRenewal(String borrowId, String exCode, String exMess) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException, NoSuchAlgorithmException{
		
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
		
		Assert.assertEquals(String.valueOf(dataResult.getInt("code")), exCode);
		Assert.assertEquals(dataResult.get("msg"), exMess);	
		
	}

}
