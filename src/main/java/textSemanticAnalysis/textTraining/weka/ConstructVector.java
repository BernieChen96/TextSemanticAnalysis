package textSemanticAnalysis.textTraining.weka;


import constant.FeatureConstant;
import constant.PathConstant;
import weka.core.*;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 51157 on 2017/8/7.
 */
public class ConstructVector {
    public static Instances construct(List<String[]> featuresExtractions) {
        List<String> features = new ArrayList<String>();
        List<String> className = new ArrayList<String>();


        className.add("问题");
        className.add("建议");
        className.add("毫无意义反馈");
        for (String[] featuresExtraction : featuresExtractions) {
            for (int i = 0; i < FeatureConstant.NUMBER_OF_FEATURE_EXTRACTION; i++) {
                features.add(featuresExtraction[i]);
            }
        }
        Set<String> set = new HashSet<String>(features);
        features.clear();//将原来的list的数据全部清空
        features.addAll(set);
        //set attributes
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        for (int i = 0; i < FeatureConstant.NUMBER_OF_FEATURE_EXTRACTION; i++) {
            attributes.add(new Attribute("第" + (i + 1) + "个关键特征", features));
        }
        attributes.add(new Attribute("分类", className));
        //set instances
        Instances instances = new Instances("特征表", attributes, 0);
        instances.setClassIndex(instances.numAttributes() - 1);
        //add instance
//        for (SecRepoEntity secRepoEntity: entities) {
//            Instance instance = new DenseInstance(attributes.size());
//            instance.setValue(0,secRepoEntity.getForkCount());
//            instance.setValue(1,secRepoEntity.getSize());
//            instance.setValue(2,secRepoEntity.getSumFollower());
//            instance.setValue(3,secRepoEntity.getAvgFollower());
//            instance.setValue(4,secRepoEntity.getWeightFollower());
//            instances.add(instance);
//        }
        for (String[] featuresExtraction : featuresExtractions) {
            Instance instance = new DenseInstance(attributes.size());
            instance.setDataset(instances);
            for (int i = 0; i < FeatureConstant.NUMBER_OF_FEATURE_EXTRACTION; i++) {
                instance.setValue(i, featuresExtraction[i]);
            }
            if (featuresExtraction[FeatureConstant.NUMBER_OF_FEATURE_EXTRACTION].indexOf("问题") != -1) {
                instance.setValue(FeatureConstant.NUMBER_OF_FEATURE_EXTRACTION, "问题");
            } else if (featuresExtraction[FeatureConstant.NUMBER_OF_FEATURE_EXTRACTION].indexOf("建议") != -1) {
                instance.setValue(FeatureConstant.NUMBER_OF_FEATURE_EXTRACTION, "建议");
            } else if (featuresExtraction[FeatureConstant.NUMBER_OF_FEATURE_EXTRACTION].indexOf("反馈") != -1) {
                instance.setValue(FeatureConstant.NUMBER_OF_FEATURE_EXTRACTION, "毫无意义反馈");
            }
            instances.add(instance);
        }
        return instances;
    }

    /**
     * generate weka dataSource file
     *
     * @param instances weka Instances
     */
    public static void generateArffFile(Instances instances, String path) {
        ArffSaver saver = new ArffSaver();
        saver.setInstances(instances);
        try {
            saver.setFile(new File(path));
            saver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
