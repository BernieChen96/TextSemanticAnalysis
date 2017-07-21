package pretreatment.participle;

import constant.PathConstant;
import domain.Label;
import domain.Sentence;
import util.IO;
import util.ParticipleUtil;

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
        int sentenceCount = 0;
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            String participleString = ParticipleUtil.participle(sentence.getContent());
            IO.write(PathConstant.PARTICIPLE_PATH, "第" + sentenceCount++ + "内容：" + participleString);
            Iterator<Label> labelIterator = sentence.getLabels().iterator();
            int labelCount = 0;
            while (labelIterator.hasNext()) {
                labelCount++;
                Label label = labelIterator.next();
                IO.write(PathConstant.PARTICIPLE_PATH, "标签" + labelCount + ":" + label.affection + "," + label.classify);
            }
            IO.write(PathConstant.PARTICIPLE_PATH, "\r\n");
        }
    }
}
