package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DbConnector {
	
	
	public static Connection getConnection() throws SQLException 
	{
		// TODO Auto-generated method stub
		String dbURL="jdbc:mysql://uoa25ublaow4obx5.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/sg9c0pcxq9ylrlms";
		String Username = "hqz5agko3d4eeipj";
		String Password = "cf71jiom6phqghi2";
		Connection lConnection = DriverManager.getConnection(dbURL, Username, Password);
		return lConnection;
	}
	
	public static void questionTableInserter(String extractedText,String guideline_id) throws SQLException 
	{
		String sql = "insert into questions " + "(questions_content,guideline_id)"+"values(?,?)";
		java.sql.Connection myConn = DbConnector.getConnection();
		PreparedStatement pstmt = myConn.prepareStatement(sql);
    	pstmt.setString(1, extractedText);
		pstmt.setString(2, guideline_id);
		pstmt.executeUpdate();
	}
	
	public static ArrayList<String> sentencesTableLoader() throws SQLException
	{
		ArrayList<String> inputSet = new ArrayList<String>();
		ArrayList<String> guideline_idSet = new ArrayList<String>();
 		java.sql.Connection myConn = DbConnector.getConnection();
		String sql1 = "SELECT sentence_content,guideline_id FROM sentences";

		PreparedStatement rowsNumpstmt = myConn.prepareStatement(sql1);
	    ResultSet rs = rowsNumpstmt.executeQuery(sql1);
	    while(rs.next())
	    {
	    		inputSet.add(rs.getString("sentence_content"));
	    		inputSet.add(rs.getString("guideline_id"));
	    }
	    return inputSet;
	    
	}
	
	public static int sentencesTableRowCounter() throws SQLException
	{
		int totalNum = 0;
		java.sql.Connection myConn = DbConnector.getConnection();
		String sql2 = "SELECT COUNT(sentence_content) AS total FROM sentences ";
		PreparedStatement pstmt = myConn.prepareStatement(sql2);
	    ResultSet rs2 = pstmt.executeQuery(sql2);
	    while(rs2.next())
	    {
	        totalNum = rs2.getInt("total");
	    }
	    System.out.print(totalNum);
		return totalNum;
	}
	
	
}
