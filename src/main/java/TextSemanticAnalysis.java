import constant.PathConstant;
import domain.Sentence;
import pretreatment.handleData.HandleExcelData;
import pretreatment.handleData.HandleTxtData;
import pretreatment.POI.POIExcel;
import pretreatment.participle.Participle;
import pretreatment.stopWordFilter.StopWordFilter;
import pretreatment.synonymy.HandleSynonymy;
import util.Print;

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
//        System.out.println(sentences.size());
        //将Excel数据写到txt中
        if (!new File(PathConstant.TXT_PATH).exists())
            new HandleTxtData(sentences).writeSentencesToTxt();
        //对文本内容进行分词，并将分词内容写入txt中
        //如果已经进行了分词，直接读取分词txt文件
        if (!new File(PathConstant.PARTICIPLE_PATH).exists()) {
            new Participle(sentences).participle();
        } else {

        }
//        Print.printList(sentences.get(1).getWords());
        //分词完毕后，对分词后的句子进行停用词过滤
        new StopWordFilter(sentences).stopWordFilter();
//        Print.printList(sentences.get(1).getWords());
        //判断分词结果是否为《同义词词林》未登录词
        new HandleSynonymy(sentences).judgeIsRegister();
        Print.printList(sentences.get(1).getRegisteredSynonyms());
        Print.printList(sentences.get(1).getUnregisteredSynonyms());

    }

    public static void main(String[] args) {
        new TextSemanticAnalysis().start();
    }
}
