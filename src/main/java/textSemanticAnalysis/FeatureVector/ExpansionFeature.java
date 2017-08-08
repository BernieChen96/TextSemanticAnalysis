package textSemanticAnalysis.FeatureVector;

import constant.GConstant;
import constant.PathConstant;
import domain.Sentence;
import domain.SogouRWord;
import domain.Synonym;
import util.IO;
import util.Print;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 51157 on 2017/8/5.
 */
public class ExpansionFeature {
    List<Sentence> sentences = null;
    List<SogouRWord> sogouRWords = null;
    int sentenceCount = 0;

    public ExpansionFeature(List<Sentence> sentences, List<SogouRWord> sogouRWords) {
        this.sentences = sentences;
        this.sogouRWords = sogouRWords;
    }

    public void start() {
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        while (sentenceIterator.hasNext()) {
            sentenceCount++;
            Sentence sentence = sentenceIterator.next();
            List<String> registeredSynonyms = sentence.getRegisteredSynonyms();
            Map<String, List<Synonym>> sentencePolysemousWordsMap = sentence.getSentencePolysemousWordsMap();
            Map<String, List<Synonym>> sentenceSingleSynonymWordsMap = sentence.getSentenceSingleSynonymWordsMap();
            List<Integer> G = new ArrayList<Integer>();
            List<String> words = sentence.getWords();
            sentence.setG(G);
            for (String registeredSynonym : registeredSynonyms) {
                int principalWordCount = 0;
                int othersWordCount = 0;
                Synonym synonym;
                if (sentencePolysemousWordsMap.get(registeredSynonym) != null)
                    synonym = sentencePolysemousWordsMap.get(registeredSynonym).get(0);
                else
                    synonym = sentenceSingleSynonymWordsMap.get(registeredSynonym).get(0);
                for (String word : words) {
                    Iterator<SogouRWord> sogouRWordIterator = sogouRWords.iterator();
                    while (sogouRWordIterator.hasNext()) {
                        SogouRWord sogouRWord = sogouRWordIterator.next();
                        if (sogouRWord.getRightWord().equals(word)) {
                            if (sogouRWord.getLeftWord().equals(registeredSynonym)) {
                                principalWordCount = principalWordCount + sogouRWord.getCount();
                            }
                            for (String content : synonym.getContent()) {
                                if (content.equals(registeredSynonym))
                                    continue;
                                if (sogouRWord.getLeftWord().equals(content))
                                    othersWordCount = othersWordCount + sogouRWord.getCount();
                            }
                        }
                        if (sogouRWord.getLeftWord().equals(word)) {
                            if (sogouRWord.getRightWord().equals(registeredSynonym))
                                principalWordCount = principalWordCount + sogouRWord.getCount();
                            for (String content : synonym.getContent()) {
                                if (content.equals(registeredSynonym))
                                    continue;
                                if (sogouRWord.getRightWord().equals(content))
                                    othersWordCount = othersWordCount + sogouRWord.getCount();
                            }
                        }
                    }
                }
//                Print.print("principalCount:" + principalWordCount + ",othersCount:" + othersWordCount);
                if (principalWordCount <= othersWordCount) {
                    G.add(1);
                    IO.write(PathConstant.EXPANSIONFEATURE_PATH, "1 ");
                } else if (principalWordCount > othersWordCount && principalWordCount - othersWordCount < GConstant.ε) {
                    G.add(1);
                    IO.write(PathConstant.EXPANSIONFEATURE_PATH, "1 ");
                } else {
                    G.add(0);
                    IO.write(PathConstant.EXPANSIONFEATURE_PATH, "0 ");
                }
            }
            IO.write(PathConstant.EXPANSIONFEATURE_PATH, "\r\n");
            Print.print(sentenceCount);
        }
    }

}
