package com.fanbei.until;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class GetMysql {
	
	    private static String sql_add = PropertiesHandle.readValue("sql_add");
	    private static String sql_user = PropertiesHandle.readValue("sql_user");
	    private static String sql_pwd = PropertiesHandle.readValue("sql_pwd");

	    public static String getUsername(String userName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
	        Connection con = null; 
	        Class.forName("com.mysql.jdbc.Driver").newInstance(); 
	        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); 
	        Statement stmt; 
	        stmt = con.createStatement();
	        String selectSql = "SELECT id from af_user WHERE user_name='"+userName+"'";
	        ResultSet selectRes = stmt.executeQuery(selectSql);
	        String ID = null;
	        while (selectRes.next()) {         
	        	ID = selectRes.getString("id");
	        }
	        return ID;
	    }
	    
	    public static String getBorrowNO(String user_id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
	        Connection con = null; 
	        Class.forName("com.mysql.jdbc.Driver").newInstance(); 
	        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); 
	        Statement stmt; 
	        stmt = con.createStatement();
	        String selectSql = "SELECT id FROM af_borrow_cash WHERE user_id='"+user_id+"' ORDER BY gmt_create DESC LIMIT 0,1";
	        System.out.println(selectSql);
	        ResultSet selectRes = stmt.executeQuery(selectSql);      
	        String ID = null;
	        while (selectRes.next()) {         
	        	ID = selectRes.getString("id");
	        }
	        return ID;
	    }
	    
	    public static void updateBorrowCashStaue(String id) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
	        Connection con = null; 
	        Class.forName("com.mysql.jdbc.Driver").newInstance(); 
	        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); 
	        Statement stmt; 
	        stmt = con.createStatement();
	        String selectSql = "UPDATE af_borrow_cash SET status='TRANSED' where id='"+id+"'";
	        System.out.println(selectSql);
	        int num = stmt.executeUpdate(selectSql);
	    }
	    
	    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
	    	String userId=GetMysql.getUsername("13656648524");
	        String id=GetMysql.getBorrowNO(userId);
	        GetMysql.updateBorrowCashStaue(id);
	    		     		        
	     }

}
