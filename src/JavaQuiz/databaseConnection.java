package JavaQuiz;
import java.sql.*;
import javax.swing.*;
	
public class databaseConnection {
	Connection connect=null;
	public static Connection connector(){
		try{
			Class.forName("org.sqlite.JDBC");
			Connection connect= DriverManager.getConnection("jdbc:sqlite:C:\\Users\\aelay\\workspace\\Stack_ImplementationGUI\\StacksData.sqlite");
			JOptionPane.showMessageDialog(null, "Connection Successful");
			return connect;
		}
		catch(Exception exception){
			JOptionPane.showMessageDialog(null, exception);
			return null;
		}
	}
}
