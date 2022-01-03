import java.sql.*;
import javax.swing.*;

public class DatabaseConnection {
	
	Connection con = null;
	
	public static Connection netflixCon() {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			Connection con =  DriverManager.getConnection("jdbc:sqlite:netflix.db");
			return con;
			
		}catch(Exception e){
			System.out.println("Baglanti hatasi.");
			return null;
		}
		
	}

}
