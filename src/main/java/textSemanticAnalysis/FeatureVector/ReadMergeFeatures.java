package textSemanticAnalysis.FeatureVector;

import constant.IOConstant;
import constant.PathConstant;
import domain.Sentence;
import util.IO;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 51157 on 2017/8/7.
 */
public class ReadMergeFeatures {
    List<Sentence> sentences = null;

    public ReadMergeFeatures(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public void readTxt() {
        int sentenceCount = 0;
        List<String> contents = IO.readLineForTxt(PathConstant.MERGE_FEATURES_PATH, IOConstant.UTF8);
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            String content = contents.get(sentenceCount++);
            String[] features = content.split(" ");
            if (features[0].equals(""))
                continue;
            Map<String, Integer> featuresMap = sentence.getFeaturesMap();
            for (String feature : features) {
                featuresMap.put(feature.substring(0, feature.indexOf(":")), Integer.parseInt(feature.substring(feature.indexOf(":") + 1)));
            }
        }
    }
}
