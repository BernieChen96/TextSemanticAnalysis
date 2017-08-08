package textSemanticAnalysis.wordMeaningDiscrimination;

import constant.IOConstant;
import constant.PathConstant;
import domain.Sentence;
import domain.Synonym;
import util.IO;
import util.Print;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 51157 on 2017/8/5.
 */
public class ReadWordMeaningDiscrition {
    List<Sentence> sentences = null;
    List<Synonym> synonyms = null;

    public ReadWordMeaningDiscrition(List<Sentence> sentences, List<Synonym> synonyms) {
        this.sentences = sentences;
        this.synonyms = synonyms;
    }

    public void readTxt() {
        List<String> contentList = IO.readLineForTxt(PathConstant.WORDMEANINGDISCRIMATION_PATH, IOConstant.UTF8);
        int sentenceCount = 0;
        for (Sentence sentence : sentences) {
            String content = contentList.get(sentenceCount++);
            String[] polyWordsAndSynonyms = content.split(" ");
            Map<String, List<Synonym>> sentencePolysemousWordsMap = new HashMap<String, List<Synonym>>();
            sentence.setSentencePolysemousWordsMap(sentencePolysemousWordsMap);
            for (String polyWordAndSynonym : polyWordsAndSynonyms) {
                if (polyWordAndSynonym.indexOf(",") == -1) {
                    continue;
                } else {
                    String polyWord = polyWordAndSynonym.substring(0, polyWordAndSynonym.indexOf(","));
                    String synonymCode = polyWordAndSynonym.substring(polyWordAndSynonym.indexOf(",") + 1);
                    List<Synonym> synonymList = new ArrayList<Synonym>();
                    for (Synonym synonym : synonyms) {
                        if (synonymCode.equals(synonym.getCode())) {
                            synonymList.add(synonym);
                            sentencePolysemousWordsMap.put(polyWord, synonymList);
                            break;
                        }
                    }
                }
            }

        }
    }
}
