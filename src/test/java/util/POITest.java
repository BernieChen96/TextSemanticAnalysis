package util;

import constant.PathConstant;
import org.junit.Test;

/**
 * Created by 51157 on 2017/7/17.
 */
public class POITest {
    @Test
    public void readExcel() {
        POI.readExcel(PathConstant.EXCEL_PATH);
    }
}
