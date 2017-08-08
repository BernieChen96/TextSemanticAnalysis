package textSemanticAnalysis.FeatureVector;

import constant.PathConstant;
import domain.Sentence;
import domain.Synonym;
import util.IO;
import util.Print;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 51157 on 2017/8/7.
 */
public class MergeFeaturesAndWeights {
    List<Sentence> sentences = null;
    Map<String, Integer> featuresMap = null;
    Map<String, List<Synonym>> sentencePolysemousWordsMap = null;
    Map<String, List<Synonym>> sentenceSingleSynonymWordsMap = null;
    List<Integer> G = null;
    List<String> registeredSynonyms = null;


    public MergeFeaturesAndWeights(List<Sentence> sentences) {
        this.sentences = sentences;
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            Map<String, Integer> featuresMap = new HashMap<String, Integer>();
            sentence.setFeaturesMap(featuresMap);
        }

    }

    public void startMergeFeatures() {
        int sentenceCount = 0;
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        while (sentenceIterator.hasNext()) {
            sentenceCount++;
            Sentence sentence = sentenceIterator.next();
            featuresMap = sentence.getFeaturesMap();
            sentencePolysemousWordsMap = sentence.getSentencePolysemousWordsMap();
            sentenceSingleSynonymWordsMap = sentence.getSentenceSingleSynonymWordsMap();
            G = sentence.getG();
            registeredSynonyms = sentence.getRegisteredSynonyms();
            for (String word : sentence.getWords()) {
                addCount(word);
                Synonym synonym = getSynonym(word);
                if (synonym != null)
                    if (isExpansionFeature(word)) {
                        List<String> contents = synonym.getContent();
                        for (String content : contents) {
                            if (!content.equals(word))
                                addCount(content);
                        }
                    }
            }
            for (Map.Entry<String, Integer> entry : featuresMap.entrySet()) {
                IO.write(PathConstant.MERGE_FEATURES_PATH, entry.getKey() + ":" + entry.getValue() + " ");
            }
            IO.write(PathConstant.MERGE_FEATURES_PATH, "\r\n");
            Print.print(sentenceCount);
        }

    }

    public void startCalculateWeight() {
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        int sentenceCount = 0;
        while (sentenceIterator.hasNext()) {
            sentenceCount++;
            Sentence sentence = sentenceIterator.next();
            featuresMap = sentence.getFeaturesMap();
            int featuresAllCount = getFeaturesAllCount();
            int sentenceAllcount = sentences.size();
            Map<String, Double> featuresWeightMap = new HashMap<String, Double>();
            sentence.setFeaturesWeightMap(featuresWeightMap);
            for (Map.Entry entry : featuresMap.entrySet()) {
                double TF = ((Integer) entry.getValue()).doubleValue() / featuresAllCount;
                double IDF = Math.log((double) sentenceAllcount / (getWordOccurrenceNum((String) entry.getKey()) + 1));
                double TF_IDF = TF * IDF;
                featuresWeightMap.put((String) entry.getKey(), TF_IDF);
                IO.write(PathConstant.FEATURES_WEIGHT_PATH, entry.getKey() + ":" + TF_IDF + " ");
            }
            IO.write(PathConstant.FEATURES_WEIGHT_PATH, "\r\n");
            Print.print(sentenceCount);
        }
    }

    /**
     *
     */
    public int getWordOccurrenceNum(String content) {
        int count = 0;
        for (Sentence sentence : sentences) {
            for (String word : sentence.getWords()) {
                if (word.equals(content)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    /**
     * 文本的总特征数
     *
     * @return
     */
    public int getFeaturesAllCount() {
        int count = 0;
        for (Map.Entry entry : featuresMap.entrySet()) {
            count = count + (Integer) entry.getValue();
        }
        return count;
    }

    public void addCount(String word) {
        if (featuresMap.get(word) == null) {
            featuresMap.put(word, 1);
        } else {
            featuresMap.put(word, featuresMap.get(word) + 1);
        }
    }

    public Synonym getSynonym(String word) {
        Synonym synonym = null;
        if (sentencePolysemousWordsMap.get(word) != null)
            synonym = sentencePolysemousWordsMap.get(word).get(0);
        if (sentenceSingleSynonymWordsMap.get(word) != null)
            synonym = sentenceSingleSynonymWordsMap.get(word).get(0);
        return synonym;
    }

    public boolean isExpansionFeature(String word) {
        int wordCount = 0;
        for (String registeredSynonym : registeredSynonyms) {
            if (registeredSynonym.equals(word))
                if (G.get(wordCount) == 1)
                    return true;
            wordCount++;
        }
        return false;
    }
}
