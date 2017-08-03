package textSemanticAnalysis.Synonymy;

import org.junit.Test;
import textSemanticAnalysis.synonymy.HandleSynonymy;

/**
 * Created by 51157 on 2017/7/28.
 */
public class HandleSynonymyTest {
    @Test
    public void readSynonymyTest() {
        new HandleSynonymy(null).getSynonymy();
    }

    @Test
    public void getPolysemousWordsTest() {
        new HandleSynonymy(null).getPolysemousWords();
    }
}
