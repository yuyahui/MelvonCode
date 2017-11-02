package com.fanbei.repayCash;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fanbei.until.GetBorrowId;
import com.fanbei.until.GetRebateAmount;
import com.fanbei.until.GetrRpaymentAmount;
import com.fanbei.until.HTTPPost;
import com.fanbei.until.JSonBuilt;
import com.fanbei.until.MD5Util;
import com.fanbei.until.PropertiesHandle;
import com.fanbei.until.TestJSonResult;
import com.fanbei.until.hello;
import com.google.gson.Gson;

public class GetConfirmRepayInfoTest {

	static String baseUrl = PropertiesHandle.readValue("baseUrl");
	static String testUrl = "repayCash/getConfirmRepayInfo";
	String userName = PropertiesHandle.readValue("userName");

	@DataProvider(name = "ex")
	public Object[][] Parameter() throws ClassNotFoundException,InstantiationException, IllegalAccessException,ClientProtocolException, SQLException, IOException,NoSuchAlgorithmException {
		return new Object[][] { 
				{ "",GetrRpaymentAmount.getrRpaymentAmount(userName), "0",GetRebateAmount.getRebateAmount(userName),MD5Util.MD5("123456"), "-2", "0",GetBorrowId.getBorrowId(userName), "1005", "用户不存在" },// 还款失败
				//使用返现金额支付，支付密码错误不可以还款成功{ userName,GetrRpaymentAmount.getrRpaymentAmount(userName), "0",GetRebateAmount.getRebateAmount(userName),"123456", "-2", "0",GetBorrowId.getBorrowId(userName), "1005", "用户不存在" },// 还款失败
				{ userName,"100", "0",GetRebateAmount.getRebateAmount(userName),MD5Util.MD5("123456"), "-2", "0",GetBorrowId.getBorrowId(userName), "1000", "成功" },// 还款100成功
				{ userName,GetrRpaymentAmount.getrRpaymentAmount(userName), "0",GetRebateAmount.getRebateAmount(userName),MD5Util.MD5("123456"), "-2", "0",GetBorrowId.getBorrowId(userName), "1000", "成功" },				
		};
	}
	
	@Test(dataProvider = "ex")
	public static void confirmRepay(String userName, String repaymentAmount,String couponId, String rebateAmount, String payPwd, String cardId,String actualAmount, String borrowId, String exCode, String exMess)throws ClassNotFoundException, InstantiationException,IllegalAccessException, SQLException, ClientProtocolException,	NoSuchAlgorithmException, IOException, InterruptedException {
     	
		String url = baseUrl + testUrl;
		url = url.replaceAll("\\\\", "");

		OK:
			if (Double.valueOf(repaymentAmount) > Double.valueOf(rebateAmount)) {

			System.out.println("返现余额不足，无法支付");
			break OK;

		} else {

			Thread.sleep(2000);
			JSONObject jsonParam = JSonBuilt.json_built();
			jsonParam.put("repaymentAmount", repaymentAmount);// 还款金额
			jsonParam.put("couponId", couponId);// 优惠券id
			jsonParam.put("rebateAmount", repaymentAmount);// 账户余额
			jsonParam.put("payPwd", MD5Util.MD5("123456"));
			jsonParam.put("cardId", "-2");
			jsonParam.put("actualAmount", "0");// 实付金额
			jsonParam.put("borrowId", borrowId);

			HTTPPost httpPost = new HTTPPost();
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
			System.out.println("还款功能完成");
		}


}
