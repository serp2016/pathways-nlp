<?php
    //Get file attributes
    $name = $_FILES['file']['name'];
    $extension = strtolower(substr($name,strpos($name,'.')+1));
    $type = $_FILES['file']['type'];
    $tmp_name = $_FILES['file']['tmp_name'];
    $size = $_FILES['file']['size'];
    $max_size = 1048576;
//    $error = $_FILES['file']['error'];

    //Upload file
    if(isset($name))
    {
        if(!empty($name))
        {
            if(($extension=='pdf')&&$type=='application/pdf'&&$size<=$max_size)
            {
                
            
                $location = 'uploads/';
            
                if(move_uploaded_file($tmp_name, $location.$name))
                {
                    header("Location: /search.php");
                    echo "Extracted Questions Successfully!";
                    exit;
                    $str = 'java -jar "/Applications/XAMPP/xamppfiles/htdocs/IIESWebsite/IIESProcesser.jar" /Applications/XAMPP/xamppfiles/htdocs/IIESWebsite/uploads';
                    //You probably need to change these two paths if you want to run the processor, change these paths based on the file paths in your local computer
                    exec($str,$return);
                    if(!$return)
                    {
                        header("Location: /search.php");
                        echo "Extracted Questions Successfully!";
                        exit;
                    }
                    else
                    {
                        echo "Processing Failed!";
                    }
                }
                else
                {
                    echo 'There was an error.';
                }
            }
            else
            {
                
                echo "File must be pdf file and must be 1MB or less!";
            }
        }
        else
        {
            echo 'Please choose a file';
        }
    }
?>
