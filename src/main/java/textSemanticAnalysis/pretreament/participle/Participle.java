package textSemanticAnalysis.pretreament.participle;

import constant.PathConstant;
import domain.Label;
import domain.Sentence;
import util.IO;
import util.ParticipleUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by 51157 on 2017/7/18.
 */
public class Participle {
    List<Sentence> sentences = null;

    public Participle(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public void participle() {
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        int sentenceCount = 1;
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            String participleString = ParticipleUtil.participle(sentence.getContent());
            sentence.setContent(participleString);
            sentence.setWords(getWords(participleString));
            IO.write(PathConstant.PARTICIPLE_PATH, "第" + sentenceCount++ + "内容：" + participleString);
//            Iterator<Label> labelIterator = sentence.getLabels().iterator();
//            int labelCount = 0;
//            while (labelIterator.hasNext()) {
//                labelCount++;
//                Label label = labelIterator.next();
            Label label = sentence.getLabel();
            IO.write(PathConstant.PARTICIPLE_PATH, "标签:" + label.affection + "," + label.classify);
//                IO.write(PathConstant.PARTICIPLE_PATH, "标签" + labelCount + ":" + label.affection + "," + label.classify);
//            }
            IO.write(PathConstant.PARTICIPLE_PATH, "\r\n");
        }
    }

    public List<String> getWords(String content) {
        List<String> words = new ArrayList<String>();
        for (String word : content.split(" +")) {
            if (word.indexOf("/") == -1)
                words.remove(word);
            else {
                word = word.substring(0, word.indexOf("/"));
                words.add(word);
            }
        }
        return words;
    }
}
