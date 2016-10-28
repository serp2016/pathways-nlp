<?php
session_start();
if(isset($_SESSION['question_content']))
{
    //Include checkSource.php
    require_once(dirname(__FILE__).'/checkSource.php');
    
    //Get question content from session
    $question_content = $_SESSION['question_content'];
	$check = new checkSource();
    
    //Get connection
	$connection = $check->connect_database();
    
	$result = $check->source_display($question_content);
    
    //Get page number, sentence content, file path 
	list($pageNumber, $sentenceContent, $fullFilePath) = $check->source_display($question_content);
    
    //Get page number from session
    $page_num = $_SESSION['page_num'];
    
    //Display the highlighted PDF page
    if(!empty($page_num))
    {     
        ob_start();
        //include fpdf.php and fpdi.php
        require_once('/Applications/XAMPP/xamppfiles/htdocs/IIESWebsite/FPDI-1.6.1/fpdf.php');
        require_once('/Applications/XAMPP/xamppfiles/htdocs/IIESWebsite/FPDI-1.6.1/fpdi.php');
        $pdf = new FPDI();

        //The directory where the highlighted PDF page is stored
        //You probabley need to change the file path below if you run IIES on localhost
        $pageCount = $pdf->setSourceFile("/Applications/XAMPP/xamppfiles/htdocs/IIESWebsite/extractor-search-phrases.pdf");
        $tplIdx = $pdf->importPage((int)$page_num, '/MediaBox');
        $pdf->addPage();
        $pdf->useTemplate($tplIdx, 0, 0, 200);
        //Show PDF page in the Browser
        $pdf->Output();
        ob_end_flush();
    }
    else
    {
        echo "Didn't pass any num or path into DPFD!!!!!!!!!";
    }
}
else
{
    echo "Didn't pass anything!";
}
?>