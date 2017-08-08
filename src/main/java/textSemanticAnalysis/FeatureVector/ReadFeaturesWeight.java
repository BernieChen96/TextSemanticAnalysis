package textSemanticAnalysis.FeatureVector;

import constant.IOConstant;
import constant.PathConstant;
import domain.Sentence;
import util.IO;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 51157 on 2017/8/7.
 */
public class ReadFeaturesWeight {
    List<Sentence> sentences = null;

    public ReadFeaturesWeight(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public void readTxt() {
        int sentenceCount = 0;
        List<String> contents = IO.readLineForTxt(PathConstant.FEATURES_WEIGHT_PATH, IOConstant.UTF8);
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            String content = contents.get(sentenceCount++);
            String[] features = content.split(" ");
            Map<String, Double> featuresWeightMap = new HashMap<String, Double>();
            sentence.setFeaturesWeightMap(featuresWeightMap);
            if (features[0].equals(""))
                continue;
            for (String feature : features) {
                featuresWeightMap.put(feature.substring(0, feature.indexOf(":")), Double.parseDouble(feature.substring(feature.indexOf(":") + 1)));
            }
        }
    }
}
