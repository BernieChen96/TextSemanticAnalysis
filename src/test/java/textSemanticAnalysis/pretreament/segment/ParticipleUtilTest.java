package textSemanticAnalysis.pretreament.segment;

import org.junit.Test;
import textSemanticAnalysis.pretreament.participle.Segment;


/**
 * Created by 51157 on 2017/7/17.
 */

public class ParticipleUtilTest {
    @Test
    public void segmentTest() {
        try {
            Segment.Segment("././pretreament/TxtData/test1.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
