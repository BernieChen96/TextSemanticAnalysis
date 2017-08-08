package textSemanticAnalysis.pretreament.wordMeaningDiscriminationTest;

import constant.IOConstant;
import constant.PathConstant;
import domain.Sentence;
import org.junit.Test;
import textSemanticAnalysis.pretreament.POI.POIExcel;
import textSemanticAnalysis.pretreament.handleData.HandleExcelData;
import textSemanticAnalysis.sogouR.HandleSogouR;
import textSemanticAnalysis.synonymy.HandleSynonymy;
import textSemanticAnalysis.wordMeaningDiscrimination.ReadWordMeaningDiscrition;
import util.IO;
import util.Print;

import java.io.File;
import java.util.List;

/**
 * Created by 51157 on 2017/8/6.
 */
public class ReadWordMeaningDiscritionTest {

    @Test
    public void readTxtTest2() {
        List<String> contentList = IO.readLineForTxt(PathConstant.WORDMEANINGDISCRIMATION_PATH,IOConstant.UTF8);
        Print.printList(contentList);
    }
}
