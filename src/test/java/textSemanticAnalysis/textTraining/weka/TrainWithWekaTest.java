package textSemanticAnalysis.textTraining.weka;

import org.junit.Test;
import weka.classifiers.Classifier;

/**
 * Created by 51157 on 2017/8/6.
 */
public class TrainWithWekaTest {
    @Test
    public void test() {
        // 朴素贝叶斯算法
        try {
            Classifier classifier1 = (Classifier) Class.forName(
                    "weka.classifiers.functions.LibSVM").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
