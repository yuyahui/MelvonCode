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

import com.fanbei.until.GetMaxAmount;
import com.fanbei.until.HTTPPost;
import com.fanbei.until.JSonBuilt;
import com.fanbei.until.PropertiesHandle;
import com.fanbei.until.TestJSonResult;
import com.google.gson.Gson;

//借钱确认页面信息
public class GetConfirmBorrowInfoTest {

	String baseUrl = PropertiesHandle.readValue("baseUrl");
	String testUrl = "borrowCash/getConfirmBorrowInfo";	
	String userName = PropertiesHandle.readValue("userName");

	@DataProvider(name = "ex")
	public Object[][] Parameter() throws ClassNotFoundException,InstantiationException, IllegalAccessException,ClientProtocolException, SQLException, IOException, NoSuchAlgorithmException {
		return new Object[][] {
        	
		{userName, String.valueOf(GetMaxAmount.getAmount(userName)+1), "7", "", "2008", "借款金额超过可借金额，请下拉刷新后重新提交" },// 已认证(申请金额>可借余额)
		{userName, "1", "7", "", "1117", "申请的金额无效" },// 已认证(申请金额=1)
		{userName, "0", "7", "", "1117", "申请的金额无效" },// 已认证(申请金额=0)
		{userName, String.valueOf(GetMaxAmount.getAmount(userName)), "", "", "2000", "借钱金额或者时间有误" },// 已认证(借款天数传值为空)			
		{"13656648521","500","7","","1000","成功"},//未认证过的账号	
		{"13656648511","500","7","","1005","用户不存在"},//不存在的账号
	    {"","500","7","","1005","用户不存在"}, //账号传值为空
		
		{userName, String.valueOf(GetMaxAmount.getAmount(userName)-1), "7", "", "1000", "成功"},//已认证(申请金额<可借余额)
		{userName, String.valueOf(GetMaxAmount.getAmount(userName)), "7", "", "1000", "成功" },// 已认证(申请金额=可借余额)
		{userName, "500", "14", "", "1000", "成功"},//已认证(借款天数=14)	
		{userName, "500", "7", "", "1000", "成功" },// 已认证(申请金额=500)		
		};
	}

	@Test(dataProvider = "ex")
	public void getConfirmBorrowInfo(String userName, String amount,String type, String couponId,String exCode, String exMess)throws ClassNotFoundException, InstantiationException,IllegalAccessException, SQLException, ClientProtocolException,IOException, NoSuchAlgorithmException {

		String url = baseUrl + testUrl;
		url = url.replaceAll("\\\\", "");
		
		JSONObject jsonParam = JSonBuilt.json_built();
		jsonParam.put("amount", amount);
		jsonParam.put("type", type);
		jsonParam.put("couponId", couponId);

		HTTPPost httpPost = new HTTPPost();
		String result = httpPost.httpPost(url, jsonParam, userName);
		System.out.println(result);

		Gson gs = new Gson();
		TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
		JSONObject dataResult = final_res.result;
		dataResult.get("code");
		
		Assert.assertEquals(String.valueOf(dataResult.getInt("code")), exCode);
		Assert.assertEquals(dataResult.get("msg"), exMess);			
	}
	
	@AfterClass
	public void endClass(){
		System.out.println("借钱确认页面完成");
	}

}
