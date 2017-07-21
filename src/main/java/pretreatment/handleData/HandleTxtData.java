package pretreatment.handleData;

import constant.PathConstant;
import domain.Label;
import domain.Sentence;
import util.IO;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 51157 on 2017/7/17.
 */
public class HandleTxtData {
    List<Sentence> sentences = null;

    public HandleTxtData(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public void writeSentencesToTxt() {
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        int sentenceCount = 0;
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            IO.write(PathConstant.TXT_PATH, "第" + sentenceCount++ + "内容：" + sentence.content);
            Iterator<Label> labelIterator = sentence.getLabels().iterator();
            int labelCount = 0;
            while (labelIterator.hasNext()) {
                labelCount++;
                Label label = labelIterator.next();
                IO.write(PathConstant.TXT_PATH, "标签" + labelCount + ":" + label.affection + "," + label.classify);
            }
            IO.write(PathConstant.TXT_PATH, "\r\n");
        }
    }
}
