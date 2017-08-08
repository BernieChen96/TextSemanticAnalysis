package constant;

/**
 * Created by 51157 on 2017/7/17.
 */
public class PathConstant {
    public static final String EXCEL_PATH = "pretreament/ExcelData/数据导出.xlsx";
    public static final String TXT_PATH = "pretreament/TxtData/sentences.txt";
    public static final String PARTICIPLE_PATH = "pretreament/TxtData/participle.txt";
    public static final String STORWORD1_PATH = "pretreament/Stopword/总停用词表.txt";
    public static final String HANDELED_STOPWORD_PATH = "pretreament/TxtData/stopword.txt";
    //同义词词林
    public static final String SYNONYMY_PATH = "pretreament/Synonymy/哈工大信息检索研究中心同义词词林扩展版.txt";
    //中文词语搭配库MiNi版
    public static final String SOGOUR_PATH = "pretreament/sogouR/sogouR.mini.txt";
    //短文本集的搭配库
    public static final String SHORTTEXTSET_COLLOCATION_PATH = "pretreament/sogouR/ShortTextSetCollocation.txt";
    //义项判别结果
    public static final String WORDMEANINGDISCRIMATION_PATH = "pretreament/TxtData/WordMeaningDiscriminationResult.txt";
    //拓展特征选取
    public static final String EXPANSIONFEATURE_PATH = "pretreament/TxtData/ExpansionFeatureResult.txt";
    //合并特征
    public static final String MERGE_FEATURES_PATH = "pretreament/TxtData/MergeFeaturesResult.txt";
    //特征权重
    public static final String FEATURES_WEIGHT_PATH = "pretreament/TxtData/FeaturesWeightResult.txt";
    //特征抽取
    public static final String FEATURES_EXTRACTION_PATH = "pretreament/TxtData/FeaturesExtraction.txt";

    //训练预料文件
    public static final String WEKA_TRAIN_ARFF = "weka/train.arff";
    //分类器模型
    public static final String WEKA_SVM_MODEL = "weka/model/LibSvm.model";
}
