package com.fanbei.until;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class SetToken {
	
	 public static void setTokenValue(String key, String value) {
	        Properties prop = new Properties();
	        OutputStream w = null;
	        try {
	            prop = new Properties();
	            prop.load(new FileInputStream("config.properties"));
	            w = new FileOutputStream("config.properties");
	            prop.setProperty(key, value);
	            prop.store(w, key);
	            w.flush();
	        }
	        catch(IOException e) {
	            e.printStackTrace();
	        }
	        finally {
	            try {
	                if(null != w) {
	                    w.close();
	                }
	            }
	            catch(IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }


}
