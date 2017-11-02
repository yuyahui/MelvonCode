package com.fanbei.borrowCash;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fanbei.until.HTTPPost;
import com.fanbei.until.JSonBuilt;
import com.fanbei.until.PropertiesHandle;
import com.fanbei.until.TestJSonResult;
import com.google.gson.Gson;

//登录后，借钱首页
public class GetBowCashLogInInfoTest {
	
	String baseUrl = PropertiesHandle.readValue("baseUrl");
	String testUrl = "borrowCash/getBowCashLogInInfo";
	
	String userName = PropertiesHandle.readValue("userName");	
	
	@DataProvider(name="ex")
	public Object[][] Parameter(){
		return new Object[][]{
				{userName,"1000","成功"},//账号已认证
				{"13656648521","1000","成功"},//账号未认证
				{"13656648511","1005","用户不存在"},//账号不存在
				{"","1005","用户不存在"}//userName传值为空
		};
		
	}
	
	@Test(dataProvider="ex")
	public void GetBowCashLogInInfo(String userName, String exCode, String exMess) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException, NoSuchAlgorithmException{
		
		String url = baseUrl+ testUrl;
		url = url.replaceAll("\\\\", "");
		
		JSONObject jsonParam=JSonBuilt.json_built(); 
		
		HTTPPost httpPost = new HTTPPost();
		String result=httpPost.httpPost(url, jsonParam, userName);
		System.out.println(result);
		
		Gson gs=new Gson();		
		TestJSonResult final_res=gs.fromJson(result, TestJSonResult.class);
		JSONObject dataResult = final_res.result;
		
		Assert.assertEquals(dataResult.get("code").toString().substring(0, 4), exCode);
		Assert.assertEquals(dataResult.get("msg"), exMess);	
		
	}
	@AfterClass
	public void endClass(){
		System.out.println("登录后，借钱首页完成");
	}

}
