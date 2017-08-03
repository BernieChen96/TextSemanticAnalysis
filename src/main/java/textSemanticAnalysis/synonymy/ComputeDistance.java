package textSemanticAnalysis.synonymy;

import constant.SynonymyConstant;
import domain.Synonym;
import util.Print;

/**
 * Created by 51157 on 2017/7/29.
 */
public class ComputeDistance {
    public static double computeDis(Synonym synonym1, Synonym synonym2) {
        if (synonym1.getF().equals("@") || synonym2.getF().equals("@"))
            return (float) SynonymyConstant.INIT_DIS;
        else {
            if (synonym1.getCode().equals(synonym2.getCode())) {
                if (synonym1.getF().equals("="))
                    return 0;
                else {
                    return SynonymyConstant.WEIGHT[5] * SynonymyConstant.INIT_DIS;
                }
            } else {
                if (synonym1.getCodeX1().equals(synonym2.getCodeX1())) {
                    if (synonym1.getCodeX2().equals(synonym2.getCodeX2())) {
                        if (synonym1.getCodeX3().equals(synonym2.getCodeX3())) {
                            if (synonym1.getCodeX4().equals(synonym2.getCodeX4())) {
                                Print.print(SynonymyConstant.WEIGHT[4]);
                                return SynonymyConstant.WEIGHT[4] * SynonymyConstant.INIT_DIS;
                            } else {
                                return SynonymyConstant.WEIGHT[3] * SynonymyConstant.INIT_DIS;
                            }
                        } else {
                            return SynonymyConstant.WEIGHT[2] * SynonymyConstant.INIT_DIS;
                        }
                    } else {
                        return SynonymyConstant.WEIGHT[1] * SynonymyConstant.INIT_DIS;
                    }
                } else {
                    return SynonymyConstant.WEIGHT[0] * SynonymyConstant.INIT_DIS;
                }
            }
        }
    }
}
