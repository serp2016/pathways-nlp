package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnector 
{	
	public static Connection getConnection() throws SQLException 
	{
		// connecting the online database
		String dbURL="jdbc:mysql://uoa25ublaow4obx5.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/sg9c0pcxq9ylrlms";
		String Username = "hqz5agko3d4eeipj";
		String Password = "cf71jiom6phqghi2";
		Connection lConnection = DriverManager.getConnection(dbURL, Username, Password);
		return lConnection;
	}
	
	public static void questionTableInserter(String extractedText, String originalSentence, int guideline_id) throws SQLException 
	{
		String sql = "insert into questions " + "(questions_content,guideline_id)"+"values(?,?)";
		java.sql.Connection myConn = DbConnector.getConnection();
		PreparedStatement pstmt = myConn.prepareStatement(sql);
    	pstmt.setString(1, extractedText);
		pstmt.setInt(2, guideline_id);
		pstmt.executeUpdate();
		sentenceQuestionsInserter(sentenceID(originalSentence), questionID(extractedText));
		myConn.close();
	}
	
	public static void sentencesTableLoader() throws SQLException
	{
//		Test.SentenceTable tread = new SentenceTable();
//		ArrayList<String> inputSet = new ArrayList<String>();
 		java.sql.Connection myConn = DbConnector.getConnection();
		String sql = "SELECT sentence_content FROM sentences";

		PreparedStatement rowsNumpstmt = myConn.prepareStatement(sql);
	    ResultSet rs = rowsNumpstmt.executeQuery(sql);
	    while(rs.next())
	    {
	    	Test.TextProcesser.processer(rs.getString("sentence_content"));
	    }
	    myConn.close();    
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
	    myConn.close();
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
		// return the guideline_id from guidelines table using the guidelineName or fileName
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
	
	public static String guidelineFilenameTrace(int guidelineID) throws SQLException
	{
		//return the filename from guidelines table using guideline_id
		String guidelineFilename = "";
		String sql = "SELECT filename FROM guidelines WHERE guideline_id = '" + guidelineID + "'";
		java.sql.Connection myConn = DbConnector.getConnection();
		java.sql.Statement stmt = myConn.createStatement();
		ResultSet result = stmt.executeQuery(sql);
		while(result.next())
		{
			guidelineFilename  = result.getString("filename");
		}	
		myConn.close();			
		return guidelineFilename;
	}
	
	public static String guidelinePathTrace(int guidelineID) throws SQLException 
	{
		// return the full file path from guidelines table using guideline_id
		String guidelinePath = "";
		String sql = "SELECT filepath FROM guidelines WHERE guideline_id = '" + guidelineID + "'";
		java.sql.Connection myConn = DbConnector.getConnection();
		java.sql.Statement stmt = myConn.createStatement();
		ResultSet result = stmt.executeQuery(sql);
		while(result.next())
		{
			guidelinePath  = result.getString("filepath");
		}	
		myConn.close();	
		guidelinePath += guidelineFilenameTrace(guidelineID);
		System.out.println("The full path of guidelineID : '" + guidelineID + "' is : '" +  guidelinePath + "'." );
		return guidelinePath;
	}
	
	public static void sentenceQuestionsInserter(int sentences_id, int questions_id) throws SQLException
	{
		// this method is to insert questions_id and according sentences_id
		String sql = "INSERT into sentence_questions" + "(sentences_id,questions_id)"+"values(?,?)";
		java.sql.Connection myConn = DbConnector.getConnection();
		PreparedStatement pstmt = myConn.prepareStatement(sql);
		pstmt.setInt(1, sentences_id);
		pstmt.setInt(2, questions_id);
		pstmt.executeUpdate();
		myConn.close();		
	}
	
	public static int sentenceIDtrace(int questions_id) throws SQLException 
	{
		// return the sentence_id from sentence_questions table using questions_id
		int sentenceID = 0;
		String sql = "SELECT sentences_id FROM sentence_questions WHERE questions_id = '" + questions_id + "'";
		java.sql.Connection myConn = DbConnector.getConnection();
		java.sql.Statement stmt = myConn.createStatement();
		ResultSet result = stmt.executeQuery(sql);
		while(result.next())
		{
			sentenceID  = result.getInt("sentences_id");
			System.out.println("The sentenceID is : " +  sentenceID );
		}	
		myConn.close();		
		return sentenceID;
	}

	public static int sentenceID(String sentenceContent) throws SQLException 
	{
		// return the sentence_id from sentences table using content of the sentence
		int sentenceID = 0;
		String sql = "SELECT sentences_id FROM sentences WHERE sentence_content = '" + sentenceContent + "'";
		java.sql.Connection myConn = DbConnector.getConnection();
		java.sql.Statement stmt = myConn.createStatement();
		ResultSet result = stmt.executeQuery(sql);
		while(result.next())
		{
			sentenceID  = result.getInt("sentences_id");
			System.out.println("The sentenceID of '" + sentenceContent+ "' is : " +  sentenceID );
		}	
		myConn.close();		
		return sentenceID;
	}
	
	public static int questionID(String questionsContent) throws SQLException 
	{
		// return the question_id from questions table using content of the question
		int questionID = 0;
		String sql = "SELECT questions_id FROM questions WHERE questions_content = '" + questionsContent + "'";
		java.sql.Connection myConn = DbConnector.getConnection();
		java.sql.Statement stmt = myConn.createStatement();
		ResultSet result = stmt.executeQuery(sql);
		while(result.next())
		{
			questionID  = result.getInt("questions_id");
			System.out.println("The questionID of " + questionsContent + " is : " +  questionID );
		}	
		myConn.close();		
		return questionID;
	}
	
	public static int guidelineIDfromSen(String sentenceContent) throws SQLException 
	{
		// return the guideline_id from sentences table using content of the sentence
		int guidelineID = 0;
		String sql = "SELECT guideline_id FROM sentences WHERE sentence_content = '" + sentenceContent + "'";
		java.sql.Connection myConn = DbConnector.getConnection();
		java.sql.Statement stmt = myConn.createStatement();
		ResultSet result = stmt.executeQuery(sql);
		while(result.next())
		{
			guidelineID = result.getInt("guideline_id");
			System.out.println("The guidelineID of " + sentenceContent + " is : " + guidelineID );
		}	
		myConn.close();		
		return guidelineID;
	}
	
	public static void checkSource(String questionContent) throws SQLException
	{
		// the Check Source button will trigger this method to find target sentence from the PC of user
		int sentence_id = 0;
		int page_num = 0;
		String file_path = "";
		String file_name = "";
		int question_id = questionID(questionContent);
		sentence_id = sentenceIDtrace(question_id);
		if(sentence_id != 0)
		{
			
			String sql = "SELECT page_number,guideline_id FROM sentences WHERE sentences_id = '" + sentence_id + "'";
			java.sql.Connection myConn = DbConnector.getConnection();
			java.sql.Statement stmt = myConn.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next())
			{
				page_num = result.getInt("page_number");
				file_path = guidelinePathTrace(result.getInt("guideline_id"));
				file_name = guidelineFilenameTrace(result.getInt("guideline_id"));
			}
			myConn.close();
			System.out.println("Sentence found; the source guidline stores in '" + file_path + "', on page: " + page_num);
			//TODO use the variables to display the source sentence
		}
	}
	
	public static void test()
	{
		
	}
	
	
}
