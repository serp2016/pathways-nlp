<?php
if(isset($_POST['checksource']))
{
    require_once(dirname(__FILE__).'/checkSource.php');

    
    $question_content = $_POST['checksource'];
	$check = new checkSource();
	$connection = $check->connect_database();
	$result = $check->source_display($question_content);
    
    $file_path = $result[2];
    $page_num = $result[0];
    
    if(!empty($page_num)&&!empty($file_path))
    {     
        ob_start();
        require_once('/Applications/XAMPP/xamppfiles/htdocs/IIESWebsite/FPDI-1.6.1/fpdf.php');
        require_once('/Applications/XAMPP/xamppfiles/htdocs/IIESWebsite/FPDI-1.6.1/fpdi.php');
        $pdf = new FPDI();


        $pageCount = $pdf->setSourceFile($file_path);
        $tplIdx = $pdf->importPage((int)$page_num, '/MediaBox');

        $pdf->addPage();
        $pdf->useTemplate($tplIdx, 0, 0, 200);

        $pdf->Output();
        ob_end_flush();
    }
    else
    {
        echo "Didn;t pass num and path into DPFD!!!!!!!!!";
    }
}
?>