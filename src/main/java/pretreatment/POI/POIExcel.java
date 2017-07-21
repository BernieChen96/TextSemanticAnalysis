package pretreatment.POI;

import constant.PathConstant;
import util.POI;

/**
 * Created by 51157 on 2017/7/17.
 */
public class POIExcel {
    public String[][] readExcel() {
        String[][] result = POI.readExcel(PathConstant.EXCEL_PATH);
//        System.out.println(result.length+","+result[2].length);
        return result;
    }
}
