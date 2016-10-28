
<?php 
if ($_SERVER["REQUEST_METHOD"] == "POST"){
    if(isset($_POST['submit']))
{
        
        echo $str = 'java -jar "/Applications/XAMPP/xamppfiles/htdocs/IIESWebsite/IIESProcesser.jar" /Applications/XAMPP/xamppfiles/htdocs/IIESWebsite/uploads';
        //Change '/Applications/XAMPP/xamppfiles/htdocs/IIESWebsite/uploads' this path to your local path where the 'uploads' folder is.
        exec($str); 
}
}

?>
<html>
<body>

<form action="" method="post">
<input type="submit" name="submit" value="Submit">
</form>

</body>
</html>