package util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 51157 on 2017/7/17.
 */
public class ParticipleUtil {
    static CLibrary cLibrary = null;

    static {
        cLibrary = CLibrary.Instance;
        String argu = "";
        String system_charset = "GBK";
        int charset_type = 1;
        int init_flag = 0;
        try {
            init_flag = cLibrary.NLPIR_Init(argu.getBytes(system_charset), charset_type, "0".getBytes(system_charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (0 == init_flag) {
            System.err.println("分词系统初始化失败");
        }
    }

    public static String participle(String content) {
        String sInput = content;
        String nativeBytes = null;
        try {
            nativeBytes = cLibrary.NLPIR_ParagraphProcess(sInput, 3);
//            System.out.println("分词结果为： " + nativeBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nativeBytes.toString();
    }
}
