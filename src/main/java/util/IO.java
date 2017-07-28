package util;

import sun.nio.cs.ext.GBK;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    public static List<String> readLineForTxt(String filePath,String charsetName) {
        File file = new File(filePath);
        List<String> allLine = new ArrayList<String>();
        try {
            if (!file.exists()) {
                System.out.println(filePath + "不存在");
                return null;
            }
            in = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(in, charsetName));
            while (true) {
                String currentLine = br.readLine();
                if (currentLine == null)
                    break;
                allLine.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allLine;
    }
}
