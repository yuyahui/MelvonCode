package com.fanbei.borrowCash;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fanbei.until.GetBorrowId;
import com.fanbei.until.GetBorrowStatus;
import com.fanbei.until.HTTPPost;
import com.fanbei.until.JSonBuilt;
import com.fanbei.until.MD5Util;
import com.fanbei.until.PropertiesHandle;
import com.fanbei.until.TestJSonResult;
import com.fanbei.until.hello;
import com.google.gson.Gson;

//续借确认支付
public class ConfirmRenewalPayTest {

	String baseUrl = PropertiesHandle.readValue("baseUrl");
	String testUrl = "borrowCash/confirmRenewalPay";
	String userName = PropertiesHandle.readValue("userName");
	String password = PropertiesHandle.readValue("payPassword");

	@DataProvider(name = "ex")
	public Object[][] Parameter() throws ClassNotFoundException,InstantiationException, IllegalAccessException,ClientProtocolException, NoSuchAlgorithmException, SQLException,IOException {
		return new Object[][] {
		 {"",GetBorrowId.getBorrowId(userName),"0","0",MD5Util.getMD5("123456"),"-2","1005","用户不存在"},
		 {userName,"","0","0",MD5Util.getMD5(password),"-2","2002","借钱信息不存在"},
		 {userName,GetBorrowId.getBorrowId(userName),"0","0",MD5Util.getMD5("1234567"),"-2","1120","您的支付密码不正确或者尚未设置,请点击\"忘记密码\"找回或者重置"},
		 //cardId不对不应该提示成功{userName,GetBorrowId.getBorrowId(userName),"0","0",MD5Util.getMD5("123456"),"-3","1000","成功"},
		 { userName, GetBorrowId.getBorrowId(userName), "0", "0",MD5Util.getMD5(password), "-2", "1000", "成功" },

		};
	}

	@Test(dataProvider = "ex")
	public void confirmRenewalPay(String userName, String borrowId,String jfbAmount, String rebateAmount, String payPwd,String cardId, String exCode, String exMess)throws ClassNotFoundException, InstantiationException,	IllegalAccessException, SQLException, ClientProtocolException,IOException, NoSuchAlgorithmException, InterruptedException {

		String url = baseUrl + testUrl;
		url = url.replaceAll("\\\\", "");
		
		JSONObject jsonParam = JSonBuilt.json_built();
		jsonParam.put("borrowId", borrowId);
		jsonParam.put("jfbAmount", jfbAmount);
		jsonParam.put("rebateAmount", rebateAmount);
		jsonParam.put("payPwd", payPwd);
		jsonParam.put("cardId", cardId);

		HTTPPost httpPost = new HTTPPost();

		if (exCode.equals("1000") && exMess.equals("成功")) {
			for (int i = 0; i < 10; i++) {
				if (GetBorrowStatus.getBorrowStatus().equals("TRANSED")) {
					
					System.out.println("借款审核已通过，开始续借");

					String result = httpPost.httpPost(url, jsonParam, userName);
					System.out.println(result);

					Gson gs = new Gson();
					TestJSonResult final_res = gs.fromJson(result,TestJSonResult.class);
					JSONObject dataResult = final_res.result;

					Assert.assertEquals(String.valueOf(dataResult.getInt("code")), exCode);
					Assert.assertEquals(dataResult.get("msg"), exMess);

					hello.dataResult = true;				
					break;

				} else {
					System.out.println("借款仍在审核中，请稍后");
					Thread.sleep(1000);
					hello.dataResult = false;
				}
			}

		} else {
			
			String result = httpPost.httpPost(url, jsonParam, userName);
			System.out.println(result);

			Gson gs = new Gson();
			TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
			JSONObject dataResult = final_res.result;

			Assert.assertEquals(String.valueOf(dataResult.getInt("code")), exCode);
			Assert.assertEquals(dataResult.get("msg"), exMess);

		}

	}
	
	  @AfterClass
		public void endClass(){
			System.out.println("确认续借完成");
		}


}
