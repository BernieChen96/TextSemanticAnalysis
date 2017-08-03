package textSemanticAnalysis.pretreament.stopWordFilter;

import constant.IOConstant;
import constant.PathConstant;
import domain.Sentence;
import util.IO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 51157 on 2017/7/27.
 */
public class StopWordFilter {
    List<Sentence> sentences = null;
    List<String> stopWords = null;

    public StopWordFilter(List<Sentence> sentences) {
        this.sentences = sentences;
        stopWords = new ArrayList<String>();
    }

    public void readStopWord() {
        stopWords = IO.readLineForTxt(PathConstant.STORWORD1_PATH, IOConstant.GBK);
//        Print.printList(stopWords);
    }

    public void stopWordFilter() {
        readStopWord();
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            List<String> words = sentence.getWords();
//            Print.printList(words);
            for (Iterator<String> wordIterator = words.iterator(); wordIterator.hasNext(); ) {
                String word = wordIterator.next();
                boolean isStopWord = false;
                for (String stopWord : stopWords) {
                    if (word.indexOf(stopWord) != -1) {
                        isStopWord = true;
                        break;
                    }
                }
                if (isStopWord) {
                    wordIterator.remove();
                }
            }
        }
    }
}
