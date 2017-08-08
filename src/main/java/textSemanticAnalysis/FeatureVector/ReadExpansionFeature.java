package textSemanticAnalysis.FeatureVector;

import constant.IOConstant;
import constant.PathConstant;
import domain.Sentence;
import util.IO;
import util.Print;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 51157 on 2017/8/7.
 */
public class ReadExpansionFeature {
    List<Sentence> sentences = null;

    public ReadExpansionFeature(List<Sentence> sentences) {
        this.sentences = sentences;

    }

    public void readTxt() {
        List<String> contents = IO.readLineForTxt(PathConstant.EXPANSIONFEATURE_PATH, IOConstant.UTF8);
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        int sentenceCount = 0;
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            String content = contents.get(sentenceCount++);
            List<Integer> G = new ArrayList<Integer>();
            sentence.setG(G);
            String[] GStrings = content.split(" ");
            for (String GString : GStrings) {
                if (GString.equals("")) break;
                G.add(Integer.parseInt(GString));
            }
        }
    }
}
