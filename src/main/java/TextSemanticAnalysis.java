import constant.PathConstant;
import domain.Sentence;
import javafx.scene.shape.Path;
import textSemanticAnalysis.FeatureVector.*;
import textSemanticAnalysis.pretreament.handleData.HandleExcelData;
import textSemanticAnalysis.pretreament.handleData.HandleTxtData;
import textSemanticAnalysis.pretreament.POI.POIExcel;
import textSemanticAnalysis.pretreament.participle.Participle;
import textSemanticAnalysis.pretreament.stopWordFilter.StopWordFilter;
import textSemanticAnalysis.sogouR.HandleSogouR;
import textSemanticAnalysis.synonymy.HandleSynonymy;
import textSemanticAnalysis.textTraining.weka.ConstructVector;
import textSemanticAnalysis.textTraining.weka.TrainWithWeka;
import textSemanticAnalysis.wordMeaningDiscrimination.ReadWordMeaningDiscrition;
import textSemanticAnalysis.wordMeaningDiscrimination.WordMeaningDiscrimination;
import util.Print;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by 51157 on 2017/7/17.
 */
public class TextSemanticAnalysis {

    List<Sentence> sentences = null;
    List<String[]> featuresExtractions = null;

    public void start() {
        if (!new File(PathConstant.FEATURES_EXTRACTION_PATH).exists()) {
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
            Print.print("对文本内容进行分词");
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
                handleSogouR = new HandleSogouR(sentences, isHandledSogouR);
                handleSogouR.extractCollocation();
            } else {
                handleSogouR = new HandleSogouR(sentences, isHandledSogouR);
            }
//        Print.print(handleSogouR.getSogouRWords().get(20000));
            Print.print("抽取短文本搭配关系完毕");


        /*
         义项判别
         判别义项过程：多义词有多个原子词群，看原子词群中的词与上下文的对应关系
         */
            Print.print("义项判别");
            handleSynonymy.getSentencePolysemousWords();
            Print.printMap((Map) sentences.get(0).getSentencePolysemousWordsMap());
            if (!new File(PathConstant.WORDMEANINGDISCRIMATION_PATH).exists()) {
                WordMeaningDiscrimination wordMeaningDiscrimination = new WordMeaningDiscrimination(sentences, handleSogouR.getSogouRWords());
                wordMeaningDiscrimination.start();
            } else {
                Print.print("读取" + PathConstant.WORDMEANINGDISCRIMATION_PATH + "文本");
                new ReadWordMeaningDiscrition(sentences, handleSynonymy.getSynonymList()).readTxt();
                Print.print("读取" + PathConstant.WORDMEANINGDISCRIMATION_PATH + "文本完毕");
            }
            Print.printMap((Map) sentences.get(0).getSentencePolysemousWordsMap());
            Print.print("义项判别完毕");

        /*
        计算最终的拓展特征，选取出g函数为1的特征加入到原始特征中
         */
            Print.print("计算最终的拓展特征");
            if (!new File(PathConstant.EXPANSIONFEATURE_PATH).exists()) {
                ExpansionFeature expansionFeature = new ExpansionFeature(sentences, handleSogouR.getSogouRWords());
                expansionFeature.start();
            } else {
                new ReadExpansionFeature(sentences).readTxt();
            }
            Print.print("同义词大小：" + sentences.get(0).getRegisteredSynonyms().size() + "，拓展G大小：" + sentences.get(0).getG().size());
            Print.print("计算最终的拓展特征结束");

        /*
        合并特征并计算权重
         */
            Print.print("合并特征并计算权重");
            MergeFeaturesAndWeights mergeFeaturesAndWeights = new MergeFeaturesAndWeights(sentences);
            //合并特征
            if (!new File(PathConstant.MERGE_FEATURES_PATH).exists())
                mergeFeaturesAndWeights.startMergeFeatures();
            else {
                new ReadMergeFeatures(sentences).readTxt();
            }
            //计算权重
            if (!new File(PathConstant.FEATURES_WEIGHT_PATH).exists())
                mergeFeaturesAndWeights.startCalculateWeight();
            else {
                new ReadFeaturesWeight(sentences).readTxt();
            }
            Print.printMap((Map) sentences.get(16000).getFeaturesWeightMap());
            Print.print("合并特征并计算权重完毕");

        /*
        特征抽取，筛选出前FeatureConstant.NUMBER_OF_FEATURE_EXTRACTION的特征，倘若特征不足以null来代替
         */
            Print.print("特征抽取");
            FeatureExtraction featureExtraction = new FeatureExtraction(sentences);
            featuresExtractions = featureExtraction.start();
            featureExtraction.writeResultToTxt();
            Print.print("特征抽取完毕");
        } else {
            Print.print("特征已提取完毕，开始读取　特征提取文本");
            FeatureExtraction featureExtraction = new FeatureExtraction(null);
            featuresExtractions = featureExtraction.readTxt();
//            Print.print(featuresExtractions.get(0)[0]);
        }
        if (!new File(PathConstant.WEKA_TRAIN_ARFF).exists()) {
            ConstructVector.generateArffFile(ConstructVector.construct(featuresExtractions), PathConstant.WEKA_TRAIN_ARFF);
        }
        TrainWithWeka.startTrain();
    }

    public static void main(String[] args) {
        new TextSemanticAnalysis().start();
    }
}
