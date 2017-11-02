package com.fanbei.login;

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

//获取用户相关资料
public class GetUserInfoTest {
	
	String baseUrl = PropertiesHandle.readValue("baseUrl");
	String testUrl = "user/getUserInfo";
	String userName = PropertiesHandle.readValue("userName");	
	
	@DataProvider(name="ex")
	public Object[][] Parameter(){
		return new Object[][]{
				{userName,"1000","成功"},
				{"13656648511","1005","用户不存在"},
				{"","1005","用户不存在"}	
		};		
	}
	
	@Test(dataProvider="ex")
	public void GetUserInfo(String userName, String exCode, String exMess) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException, NoSuchAlgorithmException{
		
		String url = baseUrl+ testUrl;
		url = url.replaceAll("\\\\", "");
		//System.out.println("url="+url);
		
		JSONObject jsonParam=JSonBuilt.json_built(); 
		
		HTTPPost httpPost = new HTTPPost();
		String result=httpPost.httpPost(url, jsonParam, userName);
		System.out.println(result);
		
		Gson gs=new Gson();		
		TestJSonResult final_res=gs.fromJson(result, TestJSonResult.class);
		JSONObject dataResult = final_res.result;
		
		Assert.assertEquals(String.valueOf(dataResult.getInt("code")), exCode);
		Assert.assertEquals(dataResult.get("msg"), exMess);			 		
	}
	
	@AfterClass
	public void endClass(){
		System.out.println("获取用户相关资料完成");
	}

}
