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
import com.fanbei.until.hello;
import com.google.gson.Gson;

//续借记录
public class GetRenewalListTest {
	String baseUrl = PropertiesHandle.readValue("baseUrl");
	String testUrl = "borrowCash/getRenewalList";
	String userName = PropertiesHandle.readValue("userName");

	@DataProvider(name = "ex")
	public Object[][] Parameter() throws ClassNotFoundException,InstantiationException, IllegalAccessException,ClientProtocolException, NoSuchAlgorithmException, SQLException,IOException {
		return new Object[][] {
				{ userName, GetBorrowId.getBorrowId(userName), "1", "1000","成功" },
				{ "", GetBorrowId.getBorrowId(userName), "1", "1005", "用户不存在" },
				{ "13656648577", GetBorrowId.getBorrowId(userName), "1","1005", "用户不存在" },
		        // 该用户不存在续借账单{"13656648521",{GetBorrowId.getBorrowId(userName),"1","1000","成功"},
		        // 用户未传borrowId{userName, "","1","1000","成功"},
		        // 错误的borrowId{userName, "123","1","1000","成功"},
		};
	}

	@Test(dataProvider = "ex")
	public void getRenewalList(String userName, String borrowId, String pageNo,	String exCode, String exMess) throws ClassNotFoundException,	InstantiationException, IllegalAccessException, SQLException,ClientProtocolException, IOException, NoSuchAlgorithmException {
        
			String url = baseUrl + testUrl;
			url = url.replaceAll("\\\\", "");
			JSONObject jsonParam = JSonBuilt.json_built();
			jsonParam.put("borrowId", borrowId);
			jsonParam.put("pageNo", pageNo);
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
			System.out.println("续借列表查看完成");
		}

	}


