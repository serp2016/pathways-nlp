<?php
class checkSource
{
	public function connect_database()
	{
		// Create the database connection
		$dbhost = "uoa25ublaow4obx5.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306";
		$dbuser = "hqz5agko3d4eeipj";
		$dbpass = "cf71jiom6phqghi2";
		$dbname = "sg9c0pcxq9ylrlms";
		$connection = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname);
		if(mysqli_errno($connection))
		{
			die("Database connection failed; ".
			mysqli_error($connection).
				"(".mysqli_errno($connection).")");
		}
		return $connection;
	}
	
	public function fetch_question_id($questionsContent) 
	{
		// Return the question_id from questions table using content of the question
		$questionID = (int) 0;
		$questionCT = $this->connect_database()->real_escape_string($questionsContent);
		$query = "SELECT questions_id FROM questions WHERE questions_content = '{$questionCT}'";
	    $stmt = $this->connect_database()->query($query);
		if(!$stmt->num_rows)
		{
			echo "Can't find any matched question_id.";
			return false;
		}
		while($row = $stmt->fetch_assoc())
        {
            $questionID = $row["questions_id"];
//			echo "The questionID of (". $questionsContent .") is : ". $questionID;
//			echo "<hr />";
        }
		$stmt->close;
		$this->connect_database()->close();
		return $questionID;
	}
	
	public function fetch_sentence_id($questionID) 
	{
		// Return the sentence_id from sentence_questions table using questions_id
		$sentenceID = (int) 0;
		if(!is_int($questionID)) $questionID = (int) $questionID;
		$query = "SELECT sentences_id FROM sentence_questions WHERE questions_id = '{$questionID}'";
	    $stmt = $this->connect_database()->query($query);
		if(!$stmt->num_rows)
		{
			echo "Can't find any matched sentence_id.";
			return false;
		}
		while($row = $stmt->fetch_assoc())
        {
            $sentenceID = $row["sentences_id"];
//			echo "The sentenceID of question ID: (". $questionID .") is : ". $sentenceID;
//			echo "<hr />";
			break;
        }
		$stmt->close;
		$this->connect_database()->close();
		return $sentenceID;
	}

	public function fetch_guideline_filename($guidelineID) 
	{
		// Return the filename from guidelines table using guideline_id
		if(!is_int($guidelineID)) $guidelineID = (int) $guidelineID;
		$fileName = "";
		$query = "SELECT filename FROM guidelines WHERE guideline_id = {$guidelineID}";
	    $stmt = $this->connect_database()->query($query);
		if(!$stmt->num_rows)
		{
			echo "Can't find any matched filename.";
			return false;
		}
		while($row = $stmt->fetch_assoc())
        {
            $fileName = $row["filename"];
//			echo "The filename of guideline ID: " . $guidelineID .";is: (". $fileName .")";
//			echo "<hr />";
			break;
        }
		$stmt->close;
		$this->connect_database()->close();
		return $fileName;
	}
	
	public function fetch_guideline_fullpath($guidelineID) 
	{
		// Return the full file path from guidelines table using guideline_id
		if(!is_int($guidelineID)) $guidelineID = (int) $guidelineID;
		$filePath = "";
		$query = "SELECT filepath FROM guidelines WHERE guideline_id = {$guidelineID}";
	    $stmt = $this->connect_database()->query($query);
		if(!$stmt->num_rows)
		{
			echo "Can't find any matched filepath.";
			return false;
		}
		while($row = $stmt->fetch_assoc())
        {
            $filePath = $row["filepath"];
//			echo "The fullpath of guideline ID: " . $guidelineID .";is: (". $filePath .")";
//			echo "<hr />";
			break;
        }
		$filePath = $filePath ."/". $this->fetch_guideline_filename($guidelineID);
		$stmt->close;
		$this->connect_database()->close();
		return $filePath;
	}
	
    public function source_display($questionContent)
    {
        // Check Source button will trigger this method to find target sentence from the PC of user
		$sentenceID = (int) 0;
		$pageNUM = (int) 0;
		$filePath = "";
		$fileName = "";
		$sentenceCT = "";
        
        $questionCT = $this->connect_database()->real_escape_string($questionsContent);
        $questionID = (int) $this->fetch_question_id($questionContent);
        $sentenceID = (int) $this->fetch_sentence_id($questionID);
        if($sentenceID != 0)
        {
            $query = "SELECT sentence_content,page_number,guideline_id FROM sentences WHERE sentences_id = {$sentenceID}";
			$stmt = $this->connect_database()->query($query);
			if(!$stmt->num_rows)
			{
				echo "Can't find any sentence.";
				return false;
			}
			while($row = $stmt->fetch_assoc())
        	{
				$pageNUM = $row["page_number"];
				$guidelineID = $row["guideline_id"];
				$sentenceCT = $row["sentence_content"];
				$filePath = $this->fetch_guideline_fullpath($guidelineID);
				$fileName = $this->fetch_guideline_filename($guidelineID);
//				echo "Sentence found; the source guidline stores in '". $filePath ."', on page: ". $pageNUM;
//				echo "<hr />";
				break;
        	}
			
        }
		$stmt->close;
		$this->connect_database()->close();
		return array($pageNUM,$sentenceCT,$filePath);
    }
}

?>
