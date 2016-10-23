<?php

$search_term =  "";
$search_results = false;

if(isset($_POST['search']))
{
    if(empty($_POST['search']))
    {
        echo "Please enter a search term!";
    }
    else
    {
        require_once(dirname(__FILE__).'/class-search.php');

        $search = new search();
        $search_term = $_POST['search'];

        $search_results = $search->search($search_term);
    }
}

//elseif(isset($_POST['b']))
//{
//    require_once(dirname(__FILE__).'/checkSource.php');
//    
//}

?>



<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>IIES</title>

    <!-- Bootstrap Core CSS -->
    <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css'>

    <!-- Custom CSS -->
    <style>
    body 
    {
        
        padding-top: 70px;
        /* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
    }
    div.result {
        background-color: #EFEFEF;
        margin: 10px 0 10px 0;
        padding: 10px;
        overflow:hidden ;
    } 
    li.dropdown {
        display: inline-block;
    }

    .dropdown-content {
        display: none;
        position: absolute;
        background-color: #212121;
        min-width: 160px;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    }

    .dropdown-content a {
        color: white;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
        text-align: left;
    }

    .dropdown-content a:hover {background-color: #353535}
    
    .dropdown:hover .dropdown-content {
        display: block;
    }
    </style>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">IIES</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="about">About</a>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropbtn">Functions</a>
                        <div class="dropdown-content">
                            <a href="#" >Guidelines Loader</a>
                        </div>
                    </li>
                    <li>
                        <a href="contact">Contact</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Page Content -->
    <div class="container">

        <div class="row">
            <div class="col-lg-6">
            <div class="search-form">
            <form action="" method="post">
            <div class="input-group">
            
            <input id="search-input" name="search" type="search" class="form-control" placeholder="Search for..." size="200" value="<?php echo $search_term; ?>">
              <span class="input-group-btn">
                <button class="btn btn-primary" type="submit">Search</button>
                <label class="btn btn-info">
                    Browse&hellip; <input type="file" name="browse" style="display: none;" webkitdirectory="" directory="">
                </label>
                <button class="btn btn-success" type="submit">Upload</button>
              </span>
           
            </div><!-- /input-group -->
            </form>
            </div>
            <?php if ($search_results) : ?>
            <div class="results-count">
                <p><?php echo $search_results['count']; ?> results found</p>
            </div>
            <div class="results-table" >
                <?php foreach ($search_results['results'] as $search_result) : ?>
                <form action="displayPDF.php" method="post">
                <div class="result" style="clean: left;">
                    <?php echo $search_result->questions_content; ?>
                    <button class="btn btn-primary btn-sm" type="submit" value="<?php echo $search_result->questions_content; ?>" name="checksource" style="float: right;">Check Source</button>
                   
                </div>
                </form>
                <?php endforeach; ?>
            </div>    
<!--
            <div class="search-raw">
                <pre>
                    <?php print_r($search_results); ?>
                </pre>
            </div>
-->
            <?php endif; ?>
        </div><!-- /.col-lg-6 -->
        </div><!-- /.row -->
    </div>
    <!-- /.container -->

    <!-- jQuery Version 1.11.1 -->
    <script src="js/jquery.js"></script>
    <script src="http://listjs.com/no-cdn/list.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    
</body>

</html>
