import constant.PathConstant;
import domain.Sentence;
import textSemanticAnalysis.pretreament.handleData.HandleExcelData;
import textSemanticAnalysis.pretreament.handleData.HandleTxtData;
import textSemanticAnalysis.pretreament.POI.POIExcel;
import textSemanticAnalysis.pretreament.participle.Participle;
import textSemanticAnalysis.pretreament.stopWordFilter.StopWordFilter;
import textSemanticAnalysis.sogouR.HandleSogouR;
import textSemanticAnalysis.synonymy.HandleSynonymy;
import util.Print;

import java.io.File;
import java.util.List;

/**
 * Created by 51157 on 2017/7/17.
 */
public class TextSemanticAnalysis {

    List<Sentence> sentences = null;

    public void start() {
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
        sentences = new HandleExcelData(excelData).getSentences();
        Print.print("处理EXCEL数据完毕");
        //System.out.println(sentences.size());
        /*
        将Excel数据写到txt中
        */
        if (!new File(PathConstant.TXT_PATH).exists()) {
            Print.print("将EXCEL数据写入TXT中");
            new HandleTxtData(sentences).writeSentencesToTxt();
            Print.print("EXCEL数据写入TXT中完毕");
        }
        /*
        对文本内容进行分词，并将分词内容写入txt中
        如果已经进行了分词，直接读取分词txt文件
        */
//        Print.print("对文本内容进行分词");
        if (!new File(PathConstant.PARTICIPLE_PATH).exists()) {
            new Participle(sentences).participle();
        } else {
            new File(PathConstant.PARTICIPLE_PATH).delete();
            new Participle(sentences).participle();
        }
        Print.print("对文本内容进行分词完毕");
//        Print.printList(sentences.get(1).getWords());
        /*
        分词完毕后，对分词后的句子进行停用词过滤
         */
        Print.print("开始停用词过滤");
        new StopWordFilter(sentences).stopWordFilter();
        Print.print("停用词过滤完毕");
//        Print.printList(sentences.get(1).getWords());
        /*
        判断分词结果是否为《同义词词林》未登录词
        将结果记录下来
        */
        Print.print("判断分词结果集中的词语是否为《同义词词林》的未登录词");
        HandleSynonymy handleSynonymy = new HandleSynonymy(sentences);
        handleSynonymy.judgeIsRegister();
        Print.print("判断分词结果集中的词语是否为《同义词词林》的未登录词完毕");
//        Print.printList(sentences.get(1).getRegisteredSynonyms());
//        Print.printList(sentences.get(1).getUnregisteredSynonyms());

        /*
        抽取短文本搭配关系
        得到同义词词林的多义词词典，和得到每个sentence中的多义词向量
         */
        Print.print("抽取短文本搭配关系");
        boolean isHandledSogouR = false;
        HandleSogouR handleSogouR = null;
        if (!(isHandledSogouR = new File(PathConstant.SHORTTEXTSET_COLLOCATION_PATH).exists())) {
            handleSynonymy.getSentencePolysemousWords();
            handleSogouR = new HandleSogouR(sentences, isHandledSogouR);
            handleSogouR.extractCollocation();
        } else {
            handleSogouR = new HandleSogouR(sentences, isHandledSogouR);
        }
//        Print.print(handleSogouR.getSogouRWords().get(20000));
        Print.print("抽取短文本搭配关系完毕");



        /*
         生成义项判别矩阵
         2.获取多义词词典
         3.遍历SogouR
         */
//        HandleSogouR handleSogouR = new HandleSogouR(sentences);
//        handleSogouR.setPolysemousWords(handleSynonymy.getPolysemousWordsMap());
//        handleSogouR.ergodicSogouR();

    }

    public static void main(String[] args) {
        new TextSemanticAnalysis().start();
    }
}
