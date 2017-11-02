package com.fanbei.until;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.apache.http.client.ClientProtocolException;

import net.sf.json.JSONObject;

//确认还款
public class ConfirmRepay {
	
	static String baseUrl = PropertiesHandle.readValue("baseUrl");
	static String testUrl = "repayCash/getConfirmRepayInfo";	
	static String userName = PropertiesHandle.readValue("userName"); 
	
	public static void confirmRepay() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ClientProtocolException, NoSuchAlgorithmException, IOException{
		
		String url = baseUrl+ testUrl;
		url = url.replaceAll("\\\\", "");
		
		String couponId="0";
		String repaymentAmount=GetrRpaymentAmount.getrRpaymentAmount(userName);
		String rebateAmount=GetRebateAmount.getRebateAmount(userName);
		String borrowId=GetBorrowId.getBorrowId(userName);
		
		JSONObject jsonParam=JSonBuilt.json_built();	
		jsonParam.put("repaymentAmount", repaymentAmount);//还款金额
		jsonParam.put("couponId", couponId);//优惠券id
		jsonParam.put("rebateAmount", repaymentAmount);//账户余额
		jsonParam.put("payPwd",MD5Util.MD5("123456"));
		jsonParam.put("cardId", "-2");
		jsonParam.put("actualAmount", "0");//实付金额
		jsonParam.put("borrowId", borrowId);
		
        HTTPPost httpPost = new HTTPPost();		
		String result=httpPost.httpPost(url, jsonParam, userName);
		System.out.println(result);		
	}
	
	  public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, ClientProtocolException, NoSuchAlgorithmException, IOException {
		   
		    ConfirmRepay.confirmRepay();	      
	    		     		        
	     }
	

}
