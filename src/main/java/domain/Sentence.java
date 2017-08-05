package domain;

import java.util.List;
import java.util.Map;

/**
 * Created by 51157 on 2017/7/17.
 */
public class Sentence {
    //文本内容
    private String content;
    //文本标签
    private Label label;
    //经过分词后的词语
    private List<String> words;
    //是否为同义词词林中的词语
    private List<String> unregisteredSynonyms;
    private List<String> registeredSynonyms;
    //多义词词语
    private Map<String, List<Synonym>> sentencePolysemousWordsMap;
    //单义词词语
    private Map<String, List<Synonym>> sentenceSingleSynonymWordsMap;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public List<String> getUnregisteredSynonyms() {
        return unregisteredSynonyms;
    }

    public void setUnregisteredSynonyms(List<String> unregisteredSynonyms) {
        this.unregisteredSynonyms = unregisteredSynonyms;
    }

    public List<String> getRegisteredSynonyms() {
        return registeredSynonyms;
    }

    public void setRegisteredSynonyms(List<String> registeredSynonyms) {
        this.registeredSynonyms = registeredSynonyms;
    }

    public Map<String, List<Synonym>> getSentencePolysemousWordsMap() {
        return sentencePolysemousWordsMap;
    }

    public void setSentencePolysemousWordsMap(Map<String, List<Synonym>> sentencePolysemousWordsMap) {
        this.sentencePolysemousWordsMap = sentencePolysemousWordsMap;
    }

    public Map<String, List<Synonym>> getSentenceSingleSynonymWordsMap() {
        return sentenceSingleSynonymWordsMap;
    }

    public void setSentenceSingleSynonymWordsMap(Map<String, List<Synonym>> sentenceSingleSynonymWordsMap) {
        this.sentenceSingleSynonymWordsMap = sentenceSingleSynonymWordsMap;
    }
}
