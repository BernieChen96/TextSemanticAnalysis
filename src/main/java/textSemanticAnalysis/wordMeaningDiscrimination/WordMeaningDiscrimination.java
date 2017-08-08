package textSemanticAnalysis.wordMeaningDiscrimination;

import constant.PathConstant;
import domain.Sentence;
import domain.SogouRWord;
import domain.Synonym;
import javafx.scene.shape.Path;
import util.IO;
import util.Print;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 51157 on 2017/8/4.
 */
public class WordMeaningDiscrimination {
    List<Sentence> sentences = null;
    List<SogouRWord> sogouRWords = null;
    int sentenceCount = 0;

    public WordMeaningDiscrimination(List<Sentence> sentences, List<SogouRWord> sogouRWords) {
        this.sentences = sentences;
        this.sogouRWords = sogouRWords;
    }

    public void start() {
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        while (sentenceIterator.hasNext()) {
            sentenceCount++;
            Sentence sentence = sentenceIterator.next();
            //得到句子中所有的多义词所对应的编码向量
            Map<String, List<Synonym>> sentencePolysemousWordsMap = sentence.getSentencePolysemousWordsMap();
            for (Map.Entry<String, List<Synonym>> entry : sentencePolysemousWordsMap.entrySet()) {
                String polysemousWord = entry.getKey();
                List<Synonym> synonyms = entry.getValue();
                //记录最大出现次数
                int maxCount = 0;
                Synonym currentSynonym = null;
                for (Synonym synonym : synonyms) {
                    //记录当前原子词群出现次数
                    int currentCount = 0;
                    List<String> contents = synonym.getContent();
                    for (String content : contents) {
                        Iterator<SogouRWord> sogouRWordIterator = sogouRWords.iterator();
                        while (sogouRWordIterator.hasNext()) {
                            SogouRWord sogouRWord = sogouRWordIterator.next();
                            if (sogouRWord.getRightWord().equals(content)) {
                                for (String word : sentence.getWords()) {
                                    if (sogouRWord.getLeftWord().equals(word))
                                        currentCount = currentCount + sogouRWord.getCount();
                                }
                            }
                            if (sogouRWord.getLeftWord().equals(content)) {
                                for (String word : sentence.getWords()) {
                                    if (sogouRWord.getRightWord().equals(word))
                                        currentCount = currentCount + sogouRWord.getCount();
                                }
                            }
                        }
                    }
                    if (currentCount > maxCount) {
                        maxCount = currentCount;
                        currentSynonym = synonym;
                    }
                }

                if (maxCount == 0) {
                    Synonym defaultSynonym = synonyms.get(0);
                    synonyms.clear();
                    synonyms.add(defaultSynonym);
                    IO.write(PathConstant.WORDMEANINGDISCRIMATION_PATH, polysemousWord + "," + defaultSynonym.getCode() + " ");
                } else {
                    synonyms.clear();
                    synonyms.add(currentSynonym);
                    IO.write(PathConstant.WORDMEANINGDISCRIMATION_PATH, polysemousWord + "," + currentSynonym.getCode() + " ");
                }
            }
            IO.write(PathConstant.WORDMEANINGDISCRIMATION_PATH, "\r\n");
            Print.print(sentenceCount);
        }
    }
}
