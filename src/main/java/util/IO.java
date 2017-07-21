package util;

import java.io.*;

/**
 * Created by 51157 on 2017/7/17.
 */
public class IO {
    static InputStream in = null;
    static BufferedReader br = null;
    static OutputStream out = null;
    static BufferedWriter bw = null;

    public static void write(String filePath, String content) {
        File file = new File(filePath);
        try {
            if (!file.exists())
                file.createNewFile();
            out = new FileOutputStream(file, true);
            bw = new BufferedWriter(new OutputStreamWriter(out));
            bw.write(content);
            bw.flush();
            out.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
