<?php
    $name = $_FILES['file']['name'];
    $extension = 
    $tmp_name = $_FILES['file']['tmp_name'];
    $size = $_FILES['file']['size'];
//    $error = $_FILES['file']['error'];

    if(isset($name))
    {
        if(!empty($name))
        {
            $location = 'uploads/';
            
            if(move_uploaded_file($tmp_name, $location.$name))
            {
                echo 'uploaded!!!!!';
            }
            else
            {
                echo 'There was an error.';
            }
        }
        else
        {
            echo 'Please choose a file';
        }
    }
?>
<html>
<body>

<form action="upload_file.php" method="post" enctype="multipart/form-data">
<label for="file">Filename:</label>
<input type="file" name="file" id="file"><br>
<input type="submit" name="submit" value="Submit" >
</form>

</body>
</html>