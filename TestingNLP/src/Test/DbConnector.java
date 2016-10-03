package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;


public class DbConnector 
{
	private int guidelineID = 0;	
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
	
	public static void guidelineTableInserter(String fileName,String filePath) throws SQLException
	{
		// this method is to insert initial guideline, the default guideline name will be set as file name
		String sql = "INSERT into guidelines" + "(filename,filepath,guidelineName)"+"values(?,?,?)";
		java.sql.Connection myConn = DbConnector.getConnection();
		PreparedStatement pstmt = myConn.prepareStatement(sql);
    	pstmt.setString(1, fileName);
		pstmt.setString(2, filePath);
		pstmt.setString(3, fileName);
		pstmt.executeUpdate();
		myConn.close();
	}
	
	public static void guidelineNameUpdate (String fileName, String newName) throws SQLException
	{
		// this method is to rename the guideline with the guidelineName
		String sql = "UPDATE guidelines SET guidelineName = ? " + "WHERE filename = ?";
		java.sql.Connection myConn = DbConnector.getConnection();
		PreparedStatement pstmt = myConn.prepareStatement(sql);
		pstmt.setString(1, newName);
		pstmt.setString(2, fileName);
		pstmt.executeUpdate();
		myConn.close();
	}
	
	public static void sentenceTableInserter(String content, int pageNumber, int guideline_id) throws SQLException
	{
		// this method is to insert initial  sentence include the content of the sentence, the page number of the sentence and the guideline ID that the sentence from
		String sql = "INSERT into sentences" + "(sentence_content,page_number,guideline_id)"+"values(?,?,?)";
		java.sql.Connection myConn = DbConnector.getConnection();
		PreparedStatement pstmt = myConn.prepareStatement(sql);
    	pstmt.setString(1, content);
		pstmt.setInt(2, pageNumber);
		pstmt.setInt(3, guideline_id);
		pstmt.executeUpdate();
		myConn.close();
	}
	
	public static void clearDatabase(String databaseName) throws SQLException
	{
		// this method is to clear a Database
	    String sql = "TRUNCATE " + databaseName;
		java.sql.Connection myConn = DbConnector.getConnection();
		PreparedStatement pstmt = myConn.prepareStatement(sql);
		pstmt.executeUpdate(sql);
		System.out.println("Table " + databaseName + " cleaned");
		myConn.close();
	}
	
	public static int guidelineID(String keyInfo) throws SQLException 
	{
		// return the guideline_id using the guidelineName or fileName
		int guidelineID = 0;
		String sql = "SELECT guideline_id FROM guidelines WHERE guidelineName = '" + keyInfo + "'";
		java.sql.Connection myConn = DbConnector.getConnection();
		java.sql.Statement stmt = myConn.createStatement();
		ResultSet result = stmt.executeQuery(sql);
		while(result.next())
		{
			guidelineID  = result.getInt("guideline_id");
			System.out.println("The guidelineID of " + keyInfo + " is : " +  guidelineID );
		}
		if(guidelineID == 0)
		{
			sql = "SELECT guideline_id FROM guidelines WHERE fileName = '" + keyInfo + "'";
			stmt = myConn.createStatement();
			result = stmt.executeQuery(sql);
			while(result.next())
			{
				guidelineID  = result.getInt("guideline_id");
			}
		}	
		myConn.close();		
		return guidelineID;
	}
	
	public static void test()
	{
		
	}
	
	
}
