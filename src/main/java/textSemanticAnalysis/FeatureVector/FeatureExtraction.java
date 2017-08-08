package textSemanticAnalysis.FeatureVector;

import constant.FeatureConstant;
import constant.IOConstant;
import constant.PathConstant;
import domain.Sentence;
import javafx.scene.shape.Path;
import util.IO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 51157 on 2017/8/7.
 */
public class FeatureExtraction {
    List<Sentence> sentences = null;
    List<String[]> featuresExtractions = null;

    public FeatureExtraction(List<Sentence> sentences) {
        this.sentences = sentences;

    }

    public List<String[]> start() {
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        featuresExtractions = new ArrayList<String[]>();
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            String[] features = new String[sentence.getFeaturesWeightMap().size()];
            String[] featuresExtraction = new String[FeatureConstant.NUMBER_OF_FEATURE_EXTRACTION + 1];
            double[] weights = new double[sentence.getFeaturesWeightMap().size()];
            int count = 0;
            for (Map.Entry entry : sentence.getFeaturesWeightMap().entrySet()) {
                features[count] = (String) entry.getKey();
                weights[count] = (Double) entry.getValue();
                count++;
            }
            for (int i = 0; i < weights.length - 1; i++) {
                for (int j = i + 1; j < weights.length; j++) {
                    if (weights[j] > weights[i]) {
                        double weightTemp = weights[j];
                        weights[j] = weights[i];
                        weights[i] = weightTemp;
                        String featureTemp = features[j];
                        features[j] = features[i];
                        features[i] = featureTemp;
                    }
                }
            }
            if (weights.length < FeatureConstant.NUMBER_OF_FEATURE_EXTRACTION) {
                for (int i = 0; i < features.length; i++) {
                    featuresExtraction[i] = features[i];
                }
                for (int i = features.length; i < featuresExtraction.length - 1; i++) {
                    featuresExtraction[i] = "null";
                }
                featuresExtraction[featuresExtraction.length - 1] = sentence.getLabel().getClassify();
            } else {
                for (int i = 0; i < featuresExtraction.length - 1; i++) {
                    featuresExtraction[i] = features[i];
                }
                featuresExtraction[featuresExtraction.length - 1] = sentence.getLabel().getClassify();
            }
            featuresExtractions.add(featuresExtraction);
        }
        return featuresExtractions;
    }

    public void writeResultToTxt() {
        for (String[] featuresExtraction : featuresExtractions) {
            for (String feature : featuresExtraction)
                IO.write(PathConstant.FEATURES_EXTRACTION_PATH, feature + " ");
            IO.write(PathConstant.FEATURES_EXTRACTION_PATH, "\r\n");
        }
    }

    public List<String[]> readTxt() {
        List<String> contents = IO.readLineForTxt(PathConstant.FEATURES_EXTRACTION_PATH, IOConstant.UTF8);
        featuresExtractions = new ArrayList<String[]>();
        for (String content : contents) {
            String[] featureExtraction = content.split(" ");
            featuresExtractions.add(featureExtraction);
        }
        return featuresExtractions;
    }
}
