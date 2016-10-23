<?php
class search
{
    private $mysqli;
    
    public function __construct()
    {
        $this->connect();
    }
    
    private function connect()
    {
        $this->mysqli =  new mysqli('uoa25ublaow4obx5.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306', 'hqz5agko3d4eeipj', 'cf71jiom6phqghi2', 'sg9c0pcxq9ylrlms');
    }
    
    public function search($search_term)
    {
        $sanitized = $this->mysqli->real_escape_string($search_term);
        
        $query = $this->mysqli->query("SELECT * FROM questions where questions_content LIKE '%{$sanitized}%'");
        
        if(!$query->num_rows)
        {
            return false;
        }
        
        while($row = $query->fetch_object())
        {
            $rows[] = $row;
        }
        
        $search_results = array('count'=>$query->num_rows, 'results'=>$rows,
        );
        
        return $search_results;
    }
}


?>