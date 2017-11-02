package com.fanbei.until;

import net.sf.json.JSONObject;

import java.sql.SQLException;



/**
 * 此函数的作用是构建一个包含baserequest的json格式文件
 */
public class JSonBuilt {
	
	static String password=PropertiesHandle.readValue("password");
	static String osType=PropertiesHandle.readValue("osType");
	static String phoneType=PropertiesHandle.readValue("phoneType");
	static String uuid=PropertiesHandle.readValue("uuid");
	static String blackBox=PropertiesHandle.readValue("blackBox");

    public static JSONObject json_built() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
       
        JSONObject jsonParam = new JSONObject();//新建一个json的格式对象
        //jsonParam.put("password", password);
        //jsonParam.put("osType", osType);
        //jsonParam.put("phoneType", phoneType);
        //jsonParam.put("uuid", uuid);
        //jsonParam.put("blackBox", blackBox);   
        return jsonParam;
    }
    
    public static JSONObject json_built_NO() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //SetGetToken();
        JSONObject jsonParam = new JSONObject();//新建一个json的格式对象
    
        return jsonParam;
    }
   
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        System.out.println(json_built());
    }
}
