package textSemanticAnalysis.FeatureVector;

import domain.Sentence;
import org.junit.Test;
import textSemanticAnalysis.pretreament.POI.POIExcel;
import textSemanticAnalysis.pretreament.handleData.HandleExcelData;
import util.Print;

import java.util.List;

/**
 * Created by 51157 on 2017/8/7.
 */
public class ReadExpansionFeatureTest {
    @Test
    public void readTxtTest() {
        Print.print("开始读取EXCEL数据");
        /*
        先读取Excel数据
         */
        String[][] excelData = new POIExcel().readExcel();
        Print.print("读取EXCEL数据完毕");
        /*
        *将得到的Excel数据进行处理
        *使标签合在一起
        *得到句子集合
        */
        Print.print("处理EXCEL数据");
        List<Sentence> sentences = new HandleExcelData(excelData).getSentences();
        Print.print("处理EXCEL数据完毕");

        new ReadExpansionFeature(sentences).readTxt();

        Print.print(sentences.get(3).getG().get(1));
    }
}
