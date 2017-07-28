package domain;

import java.util.List;

/**
 * Created by 51157 on 2017/7/17.
 */
public class Sentence {
    private String content;
    private List<Label> labels;
    private List<String> words;
    private List<String> unregisteredSynonyms;
    private List<String> registeredSynonyms;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
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
}
