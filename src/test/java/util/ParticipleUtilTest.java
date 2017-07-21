package util;

import org.junit.Test;

/**
 * Created by 51157 on 2017/7/17.
 */
public class ParticipleUtilTest {
    @Test
    public void segmentTest() {
        String segmentString= ParticipleUtil.participle("游戏不兼容，开游戏会没有绚丽的效果");
            System.out.println(segmentString);
    }
}
