package textSemanticAnalysis.pretreament.handleData;

import domain.Label;
import domain.Sentence;
import util.Print;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 51157 on 2017/7/17.
 */
public class HandleExcelData {
    String[][] excelData = null;
    List<Sentence> sentences = null;

    public HandleExcelData(String[][] excelData) {
        this.excelData = excelData;
        sentences = new ArrayList<Sentence>();
    }

    public List<Sentence> getSentences() {
        Sentence sentence = null;
        int count = 0;
//        for (int i = 0; i < excelData.length; i++) {
//            if (sentence == null || !sentence.getContent().equals(excelData[i][0])) {
//                sentence = new Sentence();
//                labels = new ArrayList<Label>();
//                sentence.setContent(excelData[i][0]);
//                sentence.setLabels(labels);
//                sentences.add(sentence);
////                System.out.println(count++);
//            }
//            Label label = new Label();
//            label.setAffection(excelData[i][1]);
//            label.setClassify(excelData[i][2]);
//            labels.add(label);
//        }
        for (int i = 0; i < excelData.length; i++) {
            sentence = new Sentence();
            sentence.setContent(excelData[i][0]);
            Label label = new Label();
            label.setAffection(excelData[i][1]);
            label.setClassify(excelData[i][2]);
            sentence.setLabel(label);
            sentences.add(sentence);
//            System.out.println(count++);
        }
        return sentences;
    }
}
