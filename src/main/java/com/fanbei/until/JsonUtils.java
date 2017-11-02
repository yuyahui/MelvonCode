package com.fanbei.until;

import java.util.SortedMap;
import java.util.TreeMap;
import net.sf.json.JSONObject;

public class JsonUtils {
	//对传进来的json值根据key来进行排序
	public static JSONObject getSortJson(JSONObject jsonParam){
		java.util.Iterator keys = jsonParam.keys();  
        SortedMap map = new TreeMap();    
        while (keys.hasNext()) {    
             String key = keys.next().toString();    
             String vlaue = jsonParam.optString(key);    
             map.put(key, vlaue);    
             }    
        JSONObject json = JSONObject.fromObject(map);  
        return json;  
        }
	
	//对json的值进行拼接
	public static String getAndJson(JSONObject jsonParam, String sign){
		
		java.util.Iterator keys = jsonParam.keys();  
		while(keys.hasNext()){                                  
            String key = keys.next().toString();
            String value = jsonParam.optString(key); 
            sign=sign+"&"+key+"="+value;
        }
		return sign;		
	}
	        
	public static void main(String[] args){  
         JSONObject json = new JSONObject();  
         json.put("cc", "cc");  
         json.put("bb", "bb");  
         json.put("ee", "ee");  
         json.put("aa", "aa");  
         json.put("ba", "ba");  
         json.put("bd", "bd");  
         System.out.println(getSortJson(json).toString());  
         
     }  
 }
     
