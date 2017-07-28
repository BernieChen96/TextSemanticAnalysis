package pretreatment.synonymy;

import constant.IOConstant;
import constant.PathConstant;
import domain.Sentence;
import domain.Synonym;
import util.IO;
import util.Print;

import java.util.*;

/**
 * Created by 51157 on 2017/7/28.
 */
public class HandleSynonymy {
    List<Synonym> synonymy = null;
    Map<String, Synonym> synonymMap = null;
    List<Sentence> sentences = null;

    public HandleSynonymy(List<Sentence> sentences) {
        synonymy = new ArrayList<Synonym>();
        synonymMap = new HashMap<String, Synonym>();
        this.sentences = sentences;
    }

    /**
     * 读取同义词词林内容
     */
    public void getSynonymy() {
        List<String> synonymyContent = IO.readLineForTxt(PathConstant.SYNONYMY_PATH, IOConstant.GBK);
        for (String synonymContent : synonymyContent) {
            Synonym synonym = new Synonym();
            String[] contents = synonymContent.split(" ");
            synonym.setCode(contents[0]);
            synonym.setCodeX1("" + contents[0].charAt(0));
            synonym.setCodeX2("" + contents[0].charAt(1));
            synonym.setCodeX3("" + contents[0].charAt(2) + contents[0].charAt(3));
            synonym.setCodeX4("" + contents[0].charAt(4));
            synonym.setCodeX5("" + contents[0].charAt(5) + contents[0].charAt(6));
            List<String> content = new ArrayList<String>();
            for (int i = 1; i < contents.length; i++) {
                content.add(contents[i]);
                synonymMap.put(contents[i], synonym);
            }
            synonym.setContent(content);
            synonymy.add(synonym);
        }
//        Print.print(synonymMap.get("刁民").getCode());
//        Print.print(synonymy.get(17814).getCodeX3());
//        Print.printList(synonymyContent);
//        Print.printList(synonymy.get(17814).getContent());
    }

    //判断分词结果是否为《同义词词林》未登录词
    public void judgeIsRegister() {
        getSynonymy();
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            List<String> unregisteredSynonyms = new ArrayList<String>();
            List<String> registeredSynonyms = new ArrayList<String>();
            sentence.setRegisteredSynonyms(registeredSynonyms);
            sentence.setUnregisteredSynonyms(unregisteredSynonyms);
            for (String word : sentence.getWords()) {
                boolean isRegistered = false;
                for (Map.Entry<String, Synonym> entry : synonymMap.entrySet()) {
                    if (word.equals(entry.getKey())) {
                        registeredSynonyms.add(word);
                        isRegistered = true;
                        break;
                    }
                }
                if (!isRegistered) {
                    unregisteredSynonyms.add(word);
                }
            }
        }
    }
}
