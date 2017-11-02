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

import com.fanbei.until.GetBorrowId;
import com.fanbei.until.HTTPPost;
import com.fanbei.until.JSonBuilt;
import com.fanbei.until.PropertiesHandle;
import com.fanbei.until.TestJSonResult;
import com.fanbei.until.getRenewalld;
import com.fanbei.until.hello;
import com.google.gson.Gson;

//续借详情信息
public class GetRenewalDetailTest {

	String baseUrl = PropertiesHandle.readValue("baseUrl");
	String testUrl = "borrowCash/getRenewalDetail";
	String userName = PropertiesHandle.readValue("userName");

	@DataProvider(name = "ex")
	public Object[][] Parameter() throws ClassNotFoundException,InstantiationException, IllegalAccessException,ClientProtocolException, NoSuchAlgorithmException, SQLException,IOException {
		return new Object[][] { 
				{userName,getRenewalld.getRenewalList(userName,GetBorrowId.getBorrowId(userName)), "1000", "成功" },
		       // {userName, "","9999","服务器操作错误",},
		       // 账号未传{"",getRenewalld.getRenewalList(userName,GetBorrowId.getBorrowId(userName)),"1000","成功"},
		       // {"12345","9999","服务器操作错误"},
		       // {"","9999","服务器操作错误"}
		};
	}

	@Test(dataProvider = "ex")
	public void getRenewalDetail(String userName, String renewalId,String exCode, String exMess) throws ClassNotFoundException,InstantiationException, IllegalAccessException, SQLException,ClientProtocolException, IOException, NoSuchAlgorithmException {
		
		String url = baseUrl + testUrl;
		url = url.replaceAll("\\\\", "");

		JSONObject jsonParam = JSonBuilt.json_built();
		jsonParam.put("renewalId", renewalId);

		HTTPPost httpPost = new HTTPPost();
		String result = httpPost.httpPost(url, jsonParam, userName);
		System.out.println(result);

		Gson gs = new Gson();
		TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
		JSONObject dataResult = final_res.result;

		Assert.assertEquals(String.valueOf(dataResult.getInt("code")), exCode);
		Assert.assertEquals(dataResult.get("msg"), exMess);

	}
	
	  @AfterClass
		public void endClass(){
			System.out.println("续借详情查看完成");
		}


}
