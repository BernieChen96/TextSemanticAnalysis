package textSemanticAnalysis.synonymy;

import constant.IOConstant;
import constant.PathConstant;
import domain.Sentence;
import domain.Synonym;
import external.LinkedMultiValueMap;
import external.impl.MultiValueMap;
import util.IO;
import util.Print;

import java.util.*;

/**
 * Created by 51157 on 2017/7/28.
 */
public class HandleSynonymy {
    //编码集合
    List<Synonym> synonymy = null;
    //词语对应的编码集合
    MultiValueMap<String, Synonym> synonymMap = null;
    List<Sentence> sentences = null;
    //多义词词典
    Map<String, List<Synonym>> polysemousWordsMap = null;

    public HandleSynonymy(List<Sentence> sentences) {
        synonymy = new ArrayList<Synonym>();
        synonymMap = new LinkedMultiValueMap<String, Synonym>();
        this.sentences = sentences;
        getSynonymy();
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
            synonym.setF("" + contents[0].charAt(7));
            List<String> content = new ArrayList<String>();
            for (int i = 1; i < contents.length; i++) {
                content.add(contents[i]);
                synonymMap.add(contents[i], synonym);
            }
            synonym.setContent(content);
            synonymy.add(synonym);
        }
//        Print.print(synonymMap.get("刁民").getCode());
//        Print.print(textSemanticAnalysis.synonymy.get(17814).getCodeX3());
//        Print.printList(synonymyContent);
//        Print.printList(textSemanticAnalysis.synonymy.get(17814).getContent());
    }

    //判断分词结果是否为《同义词词林》未登录词
    public void judgeIsRegister() {
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            List<String> unregisteredSynonyms = new ArrayList<String>();
            List<String> registeredSynonyms = new ArrayList<String>();
            sentence.setRegisteredSynonyms(registeredSynonyms);
            sentence.setUnregisteredSynonyms(unregisteredSynonyms);
            for (String word : sentence.getWords()) {
                boolean isRegistered = false;
                for (String key : synonymMap.keySet()) {
                    if (word.equals(key)) {
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

    /**
     * 得到多义词词典,多义词词典
     */
    public void getPolysemousWords() {
        polysemousWordsMap = new HashMap<String, List<Synonym>>();
        for (String key : synonymMap.keySet()) {
            List<Synonym> synonyms = synonymMap.getValues(key);
            if (synonyms.size() > 1) {
                polysemousWordsMap.put(key, synonyms);
            }
        }
//        Print.printMap((Map) polysemousWordsMap);
    }

    /**
     * 通过多义词词典，识别sentence中同义词的多义词词语和单义词
     */
    public void getSentencePolysemousWords() {
        getPolysemousWords();
        Iterator<Sentence> sentenceIterator = sentences.listIterator();
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            List<String> registeredSynonyms = sentence.getRegisteredSynonyms();
            Map<String, List<Synonym>> sentencePolysemousWordsMap = new HashMap<String, List<Synonym>>();
            Map<String, List<Synonym>> sentenceSinglsSynonymWordsMap = new HashMap<String, List<Synonym>>();
            sentence.setSentencePolysemousWordsMap(sentencePolysemousWordsMap);
            sentence.setSentenceSingleSynonymWordsMap(sentenceSinglsSynonymWordsMap);
            for (String registeredSynonmy : registeredSynonyms) {
                if (polysemousWordsMap.get(registeredSynonmy) != null) {
                    sentencePolysemousWordsMap.put(registeredSynonmy, polysemousWordsMap.get(registeredSynonmy));
                } else {
                    sentenceSinglsSynonymWordsMap.put(registeredSynonmy, synonymMap.getValues(registeredSynonmy));
                }
            }
        }
    }

    public Map<String, List<Synonym>> getPolysemousWordsMap() {
        return polysemousWordsMap;
    }

    public List<Synonym> getSynonymList() {
        return synonymy;
    }
}
