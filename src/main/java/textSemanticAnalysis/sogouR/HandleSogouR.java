package textSemanticAnalysis.sogouR;

import constant.IOConstant;
import constant.PathConstant;
import domain.Sentence;
import domain.SogouRWord;
import domain.Synonym;
import javafx.scene.shape.Path;
import util.IO;
import util.Print;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 51157 on 2017/8/2.
 */
public class HandleSogouR {
    List<String> sogouRRelativeWords = null;
    List<SogouRWord> sogouRWords = null;
    List<Sentence> sentences = null;
    static int sentenceCount = 0;

    public HandleSogouR(List<Sentence> sentences, boolean isHandled) {
        this.sentences = sentences;
        Print.print(isHandled);
        if (isHandled)
            ergodicSogouR(PathConstant.SHORTTEXTSET_COLLOCATION_PATH);
        else
            ergodicSogouR(PathConstant.SOGOUR_PATH);
    }

    /**
     * 抽取搭配关系
     */
    public void extractCollocation() {
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        while (sentenceIterator.hasNext()) {
            sentenceCount++;
            Sentence sentence = sentenceIterator.next();
            List<String> words = sentence.getWords();
            if (words.size() < 2)
                continue;
            for (int i = 0; i < words.size() - 1; i++) {
                String word1 = words.get(i);
                for (int j = i + 1; j < words.size(); j++) {
                    String word2 = words.get(j);
                    if (!word1.equals(word2)) {
                        boolean isHave = false;
                        Iterator<SogouRWord> sogouRWordIterator = sogouRWords.iterator();
                        while (sogouRWordIterator.hasNext()) {
                            SogouRWord sogouRWord = sogouRWordIterator.next();
                            if ((word1.equals(sogouRWord.getLeftWord()) && word2.equals(sogouRWord.getRightWord())) || (word1.equals(sogouRWord.getRightWord()) && word2.equals(sogouRWord.getLeftWord()))) {
                                isHave = true;
                                sogouRWord.setCount(sogouRWord.getCount() + 1);
                                break;
                            }
                        }
                        if (!isHave) {
                            SogouRWord sogouRWord = new SogouRWord();
                            sogouRWord.setLeftWord(word1);
                            sogouRWord.setRightWord(word2);
                            sogouRWord.setCount(1);
                            sogouRWords.add(sogouRWord);
                        }
                    }
                }
            }
            Print.print(sentenceCount);
        }
        Iterator<SogouRWord> sogouRWordIterator = sogouRWords.iterator();
        while (sogouRWordIterator.hasNext()) {
            SogouRWord sogouRWord = sogouRWordIterator.next();
            IO.write(PathConstant.SHORTTEXTSET_COLLOCATION_PATH, sogouRWord.getLeftWord() + "-" + sogouRWord.getRightWord() + " " + sogouRWord.getCount() + "\r\n");
        }
    }

    public void ergodicSogouR(String path) {
        sogouRRelativeWords = IO.readLineForTxt(path, IOConstant.UTF8);
        sogouRWords = new ArrayList<SogouRWord>();
        //遍历每对关系词
        for (String sogouRRelativeWord : sogouRRelativeWords) {
            SogouRWord sogouRWord = new SogouRWord();
            sogouRWord.setLeftWord(sogouRRelativeWord.substring(0, sogouRRelativeWord.indexOf("-")));
            sogouRWord.setRightWord(sogouRRelativeWord.substring(sogouRRelativeWord.indexOf("-") + 1, sogouRRelativeWord.indexOf(" ")));
            sogouRWord.setCount(Integer.parseInt(sogouRRelativeWord.substring(sogouRRelativeWord.indexOf(" ") + 1)));
            sogouRWords.add(sogouRWord);
        }
    }


    public List<SogouRWord> getSogouRWords() {
        return sogouRWords;
    }
}
