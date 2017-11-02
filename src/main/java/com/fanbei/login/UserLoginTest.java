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
import com.fanbei.until.MD5Util;
import com.fanbei.until.PropertiesHandle;
import com.fanbei.until.SetToken;
import com.fanbei.until.TestJSonResult;
import com.google.gson.Gson;

public class UserLoginTest {
	
	String baseUrl = PropertiesHandle.readValue("baseUrl");
	String testUrl = "user/login";
	
	static String userName = PropertiesHandle.readValue("userName");
	static String password=PropertiesHandle.readValue("password");
	static String osType = PropertiesHandle.readValue("osType");
	static String phoneType = PropertiesHandle.readValue("phoneType");
	static String uuid = PropertiesHandle.readValue("uuid");
	static String blackBox = PropertiesHandle.readValue("blackBox");
	
	@DataProvider(name="ex")
    public Object[][] Parameter(){
		return new Object[][]{
				
				//正常登录
				{userName,MD5Util.getMD5(password), osType, phoneType, uuid, blackBox, "1000", "成功"},	//正确的账号和密码												
				//账号判断
				{"13656648511",MD5Util.getMD5(password), osType, phoneType, uuid, blackBox, "1105", "用户不存在"},//不存在的账号	
				{"11013656648",MD5Util.getMD5(password), osType, phoneType, uuid, blackBox, "1105", "用户不存在"},//不符合规格的账号		
				{"1365664852",MD5Util.getMD5(password), osType, phoneType, uuid, blackBox, "1105", "用户不存在"},//不符合规格的账号
				{"",MD5Util.getMD5(password), osType, phoneType, uuid, blackBox, "1005", "用户不存在"},//账号为空							
			/*	//密码判断
				{"13656648521",MD5Util.getMD5(password), osType, phoneType, uuid, blackBox, "1131", "密码输入有误,剩余次数(5)"},//密码错误
				{"13656648521",MD5Util.getMD5(password), osType, phoneType, uuid, blackBox, "1132", "密码输入有误,剩余次数(4)"},
				{"13656648521",MD5Util.getMD5(password), osType, phoneType, uuid, blackBox, "1133", "密码输入有误,剩余次数(3)"},
				{"13656648521",MD5Util.getMD5(password), osType, phoneType, uuid, blackBox, "1134", "密码输入有误,剩余次数(2)"},
				{"13656648521",MD5Util.getMD5(password), osType, phoneType, uuid, blackBox, "1135", "密码输入有误,剩余次数(1)"},
				{"13656648521",MD5Util.getMD5(password), osType, phoneType, uuid, blackBox, "1104", "密码错误次数超过限制锁定2小时"},//密码输入错误超过5次
				{"13656648521",MD5Util.getMD5(password), osType, phoneType, uuid, blackBox, "1104", "密码错误次数超过限制锁定2小时"},//密码输入错误超过5次,再输入正确的密码
			*/		
		};
		
	}
	
	@Test(dataProvider="ex")
	public void UserLogin(String userName,String password, String osType, String phoneType, String uuid, String blackBox,String exCode, String exMess) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException, InterruptedException, NoSuchAlgorithmException{	
	    
		String url=baseUrl+testUrl;
		url=url.replace("\\\\", "");
		//System.out.println("url="+url);
		
		JSONObject jsonParam = JSonBuilt.json_built();			
	    jsonParam.put("password", password);
	    jsonParam.put("osType", osType);
	    jsonParam.put("phoneType", phoneType);
	    jsonParam.put("uuid", uuid);
	    jsonParam.put("blackBox", blackBox);
		
		HTTPPost httpPost=new HTTPPost();
		String result=httpPost.httpPostNoToken(url, jsonParam, userName);
		System.out.println(result);
		
		Gson gs=new Gson();		
		TestJSonResult final_res=gs.fromJson(result, TestJSonResult.class);
		JSONObject dataResult = final_res.result;
		
		//在config.properties里写入token值
		if(dataResult.get("msg").equals("成功")){
			JSONObject data= (JSONObject) dataResult.get("data");
			String token=data.getString("token");	
		    SetToken.setTokenValue("token", token);			
		}		
		 
		Assert.assertEquals(String.valueOf(dataResult.getInt("code")), exCode);
		Assert.assertEquals(dataResult.get("msg"), exMess);				
	}
	
	@AfterClass
	public void endClass(){
		System.out.println("登录接口完成");
	}
}
