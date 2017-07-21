import constant.PathConstant;
import domain.Sentence;
import pretreatment.handleData.HandleExcelData;
import pretreatment.handleData.HandleTxtData;
import pretreatment.POI.POIExcel;
import pretreatment.participle.Participle;

import java.io.File;
import java.util.List;

/**
 * Created by 51157 on 2017/7/17.
 */
public class TextSemanticAnalysis {
    List<Sentence> sentences = null;

    public void start() {
        //先读取Excel数据
        String[][] excelData = new POIExcel().readExcel();
        /*
        *将得到的Excel数据进行处理
        *使标签合在一起
        *得到句子集合
        */
        sentences = new HandleExcelData(excelData).getSentences();
        System.out.print(sentences.size());
        //将Excel数据写到txt中
        if (!new File(PathConstant.TXT_PATH).exists())
            new HandleTxtData(sentences).writeSentencesToTxt();
        //对文本内容进行分词
        new Participle(sentences).participle();
    }


    public static void main(String[] args) {
        new TextSemanticAnalysis().start();
    }
}
