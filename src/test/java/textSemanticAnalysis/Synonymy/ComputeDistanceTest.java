package textSemanticAnalysis.Synonymy;

import domain.Synonym;
import org.junit.Test;
import textSemanticAnalysis.synonymy.ComputeDistance;
import util.Print;

/**
 * Created by 51157 on 2017/7/29.
 */
public class ComputeDistanceTest {
    @Test
    public void computeDisTest() {
        Synonym synonym1 = new Synonym();
        Synonym synonym2 = new Synonym();
        synonym1.setCode("Aa01A05#");
        synonym1.setCodeX1("A");
        synonym1.setCodeX2("a");
        synonym1.setCodeX3("01");
        synonym1.setCodeX4("A");
        synonym1.setCodeX5("04");
        synonym1.setF("@");

        synonym2.setCode("Aa01A05#");
        synonym2.setCodeX1("A");
        synonym2.setCodeX2("a");
        synonym2.setCodeX3("01");
        synonym2.setCodeX4("A");
        synonym2.setCodeX5("04");
        synonym2.setF("=");

        Print.print(ComputeDistance.computeDis(synonym1, synonym2));
    }
}
