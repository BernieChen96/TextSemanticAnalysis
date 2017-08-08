package textSemanticAnalysis.textTraining.weka;

import constant.PathConstant;
import constant.TrainConstant;
import util.Print;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by 51157 on 2017/8/6.
 */
public class TrainWithWeka {

    public static void startTrain() {
        Classifier classifier;
        File file = new File(PathConstant.WEKA_TRAIN_ARFF);
        ArffLoader atf = new ArffLoader();
        try {
            atf.setFile(file);
            Instances instancesTrain = atf.getDataSet();
            instancesTrain.setClassIndex(TrainConstant.CLASS_INDEX);
            classifier = (Classifier) Class.forName("weka.classifiers.bayes.NaiveBayes").newInstance();
            classifier.buildClassifier(instancesTrain);
            Evaluation eval = new Evaluation(instancesTrain);
//            eval.evaluateModel(classifier, instancesTrain);
            eval.crossValidateModel(classifier, instancesTrain, TrainConstant.CROSS_VALIDATION_NUM, new Random(5));
            Print.print("评估错误率为：" + eval.errorRate());
            Print.print("平均绝对误差：" + eval.meanAbsoluteError());//越小越好
            Print.print("均方根误差：" + eval.rootMeanSquaredError());//越小越好
            Print.print("根均方误差：" + eval.rootMeanSquaredError());//越小越好
            Print.print("是否准确的参考值：" + eval.meanAbsoluteError());//越小越好

            System.out.println(eval.toSummaryString());//输出总结信息
            System.out.println(eval.toClassDetailsString());//输出分类详细信息
            System.out.println(eval.toMatrixString());//输出分类的混淆矩阵
            //保存分类器模型
            SerializationHelper.write(PathConstant.WEKA_SVM_MODEL, classifier);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
