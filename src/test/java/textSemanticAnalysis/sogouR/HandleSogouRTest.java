package textSemanticAnalysis.sogouR;

import org.junit.Test;
import textSemanticAnalysis.synonymy.HandleSynonymy;

/**
 * Created by 51157 on 2017/8/2.
 */
public class HandleSogouRTest {
    @Test
    public void ergodicSogouR() {
        new HandleSogouR(null, false);
    }

    @Test
    public void isPplysemousWordTest() {
        HandleSogouR handleSogouR = new HandleSogouR(null, true);
        handleSogouR.setPolysemousWords(new HandleSynonymy(null).getPolysemousWordsMap());
        handleSogouR.isPplysemousWord("你好吗");
    }
}
