package com.fanbei.until;

public class  UrlAdd {
	
	static String password=PropertiesHandle.readValue("password");
	static String osType=PropertiesHandle.readValue("osType");
	static String phoneType=PropertiesHandle.readValue("phoneType");
	static String uuid=PropertiesHandle.readValue("uuid");
	static String blackBox=PropertiesHandle.readValue("blackBox");

    
    public static String UrlAdd(){
    	
    	//String url="?deviceType="+deviceType+"&systemVersion="+systemVersion+"&appVersion="+appVersion+"&clientIP="+clientIP+"&deviceNumber="+deviceNumber+"&token="+token+"&sessionId="+sessionId+"&shopId="+shopId;
    	    	
    	String url="?password="+password+"&osType="+osType+"&phoneType="+phoneType+"&uuid="+uuid+"&blackBox="+blackBox;
    	    			
    	return url;
    }
    

    
}
