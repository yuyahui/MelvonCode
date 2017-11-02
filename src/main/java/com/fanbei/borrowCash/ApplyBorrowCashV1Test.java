package com.fanbei.borrowCash;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import net.sf.json.JSONObject;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fanbei.until.ConfirmRepay;
import com.fanbei.until.GetBorrowId;
import com.fanbei.until.GetMaxAmount;
import com.fanbei.until.GetMysql;
import com.fanbei.until.HTTPPost;
import com.fanbei.until.JSonBuilt;
import com.fanbei.until.MD5Util;
import com.fanbei.until.PropertiesHandle;
import com.fanbei.until.TestJSonResult;
import com.google.gson.Gson;

//借款申请
public class ApplyBorrowCashV1Test {
	
	String baseUrl = PropertiesHandle.readValue("baseUrl");
	String testUrl = "borrowCash/applyBorrowCashV1";
	
	String blackBox = PropertiesHandle.readValue("blackBox"); 
	String userName = PropertiesHandle.readValue("userName"); 
	String payPassword=PropertiesHandle.readValue("payPassword");
	@DataProvider(name="ex")
	public Object[][] Parameter() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClientProtocolException, SQLException, IOException, NoSuchAlgorithmException{
		return new Object[][]{
				
				{"","500", "7",MD5Util.MD5(payPassword),"30.213563","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"1005","用户不存在"},//userName传值为空
				{userName,"0", "7",MD5Util.MD5(payPassword),"30.213563","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"1117","申请的金额无效"},//申请金额=0
			    {userName,"449", "7",MD5Util.MD5(payPassword),"30.213563","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"1117","申请的金额无效"},//申请金额<500
			    
				{userName,String.valueOf(GetMaxAmount.getAmount(userName)+1), "7",MD5Util.MD5(payPassword),"30.213563","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"2008","借款金额超过可借金额，请下拉刷新后重新提交"},//申请金额>额度金额
				{userName,"500", "",MD5Util.MD5(payPassword),"30.213563","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"1002","请求参数缺失"},//type参数值未传
				{userName,"500", "0",MD5Util.MD5(payPassword),"30.213563","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"1002","请求参数缺失"},//type参数值=0
				{userName,"500", "1",MD5Util.MD5(payPassword),"30.213563","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"1002","请求参数缺失"},//type参数值=1
				{userName,"500", "7","","30.213563","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"1002","请求参数缺失"},//pwd参数值为空
				{userName,"500", "7",payPassword,"30.213563","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"1120","您的支付密码不正确或者尚未设置,请点击\"忘记密码\"找回或者重置"},//pwd参数值错误
				{userName,"500", "7",MD5Util.MD5(payPassword),"","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"1002","请求参数缺失"},//latitude参数值为空
				{userName,"500", "7",MD5Util.MD5(payPassword),"123456","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"9999","服务器操作错误"},//latitude参数值错误
				{userName,"500", "7",MD5Util.MD5(payPassword),"30.213563","","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"1002","请求参数缺失"},//longitude参数值为空
				{userName,"500", "7",MD5Util.MD5(payPassword),"30.213563","123456","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"9999","服务器操作错误"},//longitude参数值错误
				//*{userName,"500", "7",MD5Util.MD5(payPassword),"30.213563","120.213831","","zhejiangsheng","binjiangqujianglinglu",blackBox,"1002","请求参数缺失"},//province参数值为空  
				//*{userName,"500", "7",MD5Util.MD5(payPassword),"30.213563","120.213831","zhejiangsheng","","binjiangqujianglinglu",blackBox,"1002","请求参数缺失"},//city参数值为空  				
				
				{userName,"500", "7",MD5Util.MD5("123456"),"30.213563","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"1000","成功"},//申请金额=500				
				//{userName,String.valueOf(GetMaxAmount.getAmount(userName)), "14",MD5Util.MD5("123456"),"30.213563","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"1000","成功"},//申请全部金额				
				{userName,String.valueOf(GetMaxAmount.getAmount(userName)-500), "7",MD5Util.MD5("123456"),"30.213563","120.213831","zhejiangsheng","hangzhoushi","binjiangqujianglinglu",blackBox,"2001","您有一笔未结清账单"}//申请成功之后，再次申请借款
		
		};
		
	}
	
    @Test(dataProvider="ex")
	public void applyBorrowCashV1(String userName, String amount,String type, String pwd, String latitude, String longitude, String province, String city, String address, String blackBox, String exCode, String exMess) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, IOException, NoSuchAlgorithmException, InterruptedException{
		
		String url = baseUrl+ testUrl;
		url = url.replaceAll("\\\\", "");
		
		JSONObject jsonParam=JSonBuilt.json_built(); 
		jsonParam.put("amount", amount);
		jsonParam.put("type", type);
		jsonParam.put("pwd", pwd);
		jsonParam.put("latitude",latitude);
		jsonParam.put("longitude", longitude);
		jsonParam.put("province", province);
		jsonParam.put("city", city);
		jsonParam.put("address", address);
		jsonParam.put("blackBox", blackBox);
		
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
		System.out.println("借款申请完成");
	}

}
