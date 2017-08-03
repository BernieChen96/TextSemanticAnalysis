package textSemanticAnalysis.pretreament.HandleData;

import domain.Sentence;
import org.junit.Test;

/**
 * Created by 51157 on 2017/7/17.
 */
public class HandleExcelDataTest {
    @Test
    public void test() {
        Sentence sentence = new Sentence();
        System.out.print(sentence.getContent().equals("123"));
    }
}
