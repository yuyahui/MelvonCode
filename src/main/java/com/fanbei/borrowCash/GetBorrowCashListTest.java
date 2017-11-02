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

//借款记录列表
public class GetBorrowCashListTest {
	
	String baseUrl = PropertiesHandle.readValue("baseUrl");
	String testUrl = "borrowCash/getBorrowCashList";
	
	String userName = PropertiesHandle.readValue("userName");	
	
	@DataProvider(name="ex")
	public Object[][] Parameter(){
		return new Object[][]{
				{userName,"1","1000","成功"},//用户有借款记录
				//提示不正确{"13656648521","1","1000","成功"},//用户无借款记录
				{"13656648577","1","1005","用户不存在"}//用户不存在				
		};	
	}
	
	@Test(dataProvider="ex")
	public void getBorrowCashList(String userName, String pageNo, String exCode, String exMess) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException, NoSuchAlgorithmException{
		
		String url = baseUrl+ testUrl;
		url = url.replaceAll("\\\\", "");
		
		JSONObject jsonParam=JSonBuilt.json_built(); 
		jsonParam.put("pageNo", pageNo);
		
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
			System.out.println("借款列表查看完成");
		}


}
