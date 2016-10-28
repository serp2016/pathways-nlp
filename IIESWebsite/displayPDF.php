<?php
session_start();
if(isset($_POST['checksource']))
{
    //Include checkSource.php and Autoload.php
    require_once(dirname(__FILE__).'/checkSource.php');
    require_once('/Applications/XAMPP/xamppfiles/htdocs/IIESWebsite/library/SetaPDF/Autoload.php');
    
    //Get question content
    $question_content = $_POST['checksource'];
    
    //Store question content into session
    $_SESSION['question_content'] = $question_content;
    
	$check = new checkSource();
    
    //Get connection from database
	$connection = $check->connect_database();
    
    //Get pageNumber, sentence conetent, file path from the output of checkSource.php
	list($pageNumber, $sentenceContent, $fullFilePath) = $check->source_display($question_content);
    
    //Get file path
    $file_path = $fullFilePath;
    
    //Get page number
    $page_num = $pageNumber;
    
    //Store page num into session
    $_SESSION['page_num'] = $page_num;
    
    //Highlight sentence and create highlighted pdf page based on the sentence content
    if(!empty($page_num)&&!empty($file_path))
    {     
        //ob_start();
        $filename = $fullFilePath;
        $page = $pageNumber;
        $phrase = $sentenceContent;
        $markOption = 'rectangle';
		
        // or if you use composer require_once('vendor/autoload.php');
        // split the phrase into words
        $searchFor = SetaPDF_Extractor_Strategy_Word::getWords($phrase);

        // prepare the result variable
        $result = array();

        // load the document
        $document = SetaPDF_Core_Document::loadByFilename($filename);

        // set a writer
        $document->setWriter(new SetaPDF_Core_Writer_File('extractor-search-phrases.pdf'));

        // get pages instance
        $pages = $document->getCatalog()->getPages();

        // ensure a valid page number
        $currentPageNo = min(max($page, 1), $pages->count());

        if (count($searchFor) > 0) 
        {
            // create an extractor instance
            $extractor = new SetaPDF_Extractor($document);

            // create the word strategy
            $strategy = new SetaPDF_Extractor_Strategy_Word();
            // set the flex line sorter
            $strategy->setSorter(new SetaPDF_Extractor_Sorter_FlexLine());
            // pass the strategy  to the extractor instance
            $extractor->setStrategy($strategy);

            // extract all words of the page
            $allWords = $extractor->getResultByPageNumber($currentPageNo);

            // prepare some vars
            $stack = array();
            // do the phrase search
            foreach ($allWords AS $word) 
            {
                if (count($stack) === count($searchFor)) 
                {
                    $result[] = new SetaPDF_Extractor_Result_Segment($stack);
                    $stack = array();
                }

                $stack[] = $word;

                foreach ($stack AS $key => $_word) 
                {
                    $string = $_word->getString();
                    if ($string !== $searchFor[$key]) 
                    {
                        $stack = array();
                        if ($string === $searchFor[0]) 
                        {
                            $stack[] = $word;
                        }
                        break;
                    }
                }
            }
        }

        // get the current page instance
        $currentPage = $pages->getPage($currentPageNo);

        echo 'Phrase "' . $phrase . '" found: ' . count($result) . (count($result) == 1 ? ' time' : ' times');
        echo ' on page ' . $currentPageNo . '.';

        if (count($result) > 0) {
            // get access to the pages annotations instance
            $annotations = $currentPage->getAnnotations();

            if ($markOption == 'rectangle') {
                // ensure a clean transformation matrix
                $currentPage->getContents()->encapsulateExistingContentInGraphicState();
                // get canvas object for the current page
                $canvas = $currentPage->getCanvas();
                // get access to the path instance
                $path = $canvas->path();
                $path->setLineWidth(2);
            }

            // iterate over all phrases
            foreach ($result AS $i => $phrase) {
                // get the bounds of the phrase
                $allBounds = $phrase->getBounds();
                foreach ($allBounds as $bounds) {
                    // draw the bounds through the pages canvas object.
                    if ($markOption == 'rectangle') {
                        $canvas->setStrokingColor(array(1, 0, 0));
                        $path->moveTo($bounds->getUr()->getX(), $bounds->getUr()->getY())
                            ->lineTo($bounds->getUl()->getX(), $bounds->getUl()->getY())
                            ->lineTo($bounds->getLl()->getX(), $bounds->getLl()->getY())
                            ->lineTo($bounds->getLr()->getX(), $bounds->getLr()->getY())
                            ->closeAndStroke();
                        // otherwise add highlight annotations
                    } else {
                        $rect = SetaPDF_Core_DataStructure_Rectangle::byRectangle($bounds->getRectangle());

                        $annotation = new SetaPDF_Core_Document_Page_Annotation_Highlight($rect);
                        $annotation->setQuadPoints(
                            $bounds->getUl()->getX(), $bounds->getUl()->getY(),
                            $bounds->getUr()->getX(), $bounds->getUr()->getY(),
                            $bounds->getLl()->getX(), $bounds->getLl()->getY(),
                            $bounds->getLr()->getX(), $bounds->getLr()->getY()
                        );
                        $annotation->setColor(array(1, 0, 0));
                        $annotations->add($annotation);
                    }
                }
            }
        }

        // jump to marked page on open the document
        $document->getCatalog()->setOpenAction(
            new SetaPDF_Core_Document_Action_GoTo(SetaPDF_Core_Document_Destination::createByPage($currentPage))
        );

        // save the resulting document
        $document->save()->finish();
		
        //        require_once('/Users/Charles/Desktop/pathways-nlp--47-UI-PDF-display-function/IIES Front End/FPDI-1.6.1/fpdf.php');
        //        require_once('/Users/Charles/Desktop/pathways-nlp--47-UI-PDF-display-function/IIES Front End/FPDI-1.6.1/fpdi.php');
        //        $pdf = new FPDI();
        //
        //
        //        $pageCount = $pdf->setSourceFile($file_path);
        //        $tplIdx = $pdf->importPage((int)$page_num, '/MediaBox');
        //
        //        $pdf->addPage();
        //        $document->useTemplate($tplIdx, 0, 0, 200);
        //
        //        $document->Output();
        //        ob_end_flush();
        header("Location: ./displayRealPDF.php");
            exit;
    }
    else
    {
        echo "Didn;t pass num and path into DPFD!!!!!!!!!";
    }
    
}
?>
