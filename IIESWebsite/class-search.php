<?php
class search
{
    private $mysqli;
    
    //Initialize constructor
    public function __construct()
    {
        $this->connect();
    }
    
    //Connect Database
    private function connect()
    {
        $this->mysqli =  new mysqli('uoa25ublaow4obx5.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306', 'hqz5agko3d4eeipj', 'cf71jiom6phqghi2', 'sg9c0pcxq9ylrlms');
    }
    
    //Search function
    public function search($search_term)
    {
        //Sanitize the search input
        $sanitized = $this->mysqli->real_escape_string($search_term);
        
        //Search user input from database
        $query = $this->mysqli->query("SELECT * FROM questions where questions_content LIKE '%{$sanitized}%'");
        
        if(!$query->num_rows)
        {
            return false;
        }
        
        
        while($row = $query->fetch_object())
        {
            $rows[] = $row;
        }
        
        //Get search results
        $search_results = array('count'=>$query->num_rows, 'results'=>$rows,
        );
        
        return $search_results;
    }
}


?>