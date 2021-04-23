package java_ncs_exam.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.Properties;

public class JdbcConn {
	
	//properties 형식 파일은  =기준으로 LeftValue는 key  RightValue는 value로 인식한다
	
	public static Connection getConnection() {
		String propertiesPath = "db.properties";
		Connection con = null;
		try (InputStream in = ClassLoader.getSystemResourceAsStream(propertiesPath)){
			Properties prop = new Properties();
			prop.load(in);
			
			con = DriverManager.getConnection(prop.getProperty("url"),prop);
			
			/*
			System.out.println("prop> "+prop);
			
			for(Entry<Object, Object> e:  prop.entrySet()) {
				System.out.printf("%s >> %s%n",e.getKey(), e.getValue());
			}
			for(Object key : prop.keySet()) {
				System.out.print(key +">>");
				System.out.println(prop.get(key));
			}
			
			for (Object value : prop.values()) {
				System.out.println(value);
			}
			*/
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
}
