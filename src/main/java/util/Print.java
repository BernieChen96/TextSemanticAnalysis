package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 51157 on 2017/7/27.
 */
public class Print {
    public static void printList(List<String> contents) {
        for (int i = 0; i < contents.size(); i++) {
            System.out.println("第" + (i + 1) + "个:" + contents.get(i));
        }
    }

    public static void printStrings(String[] contents) {
        int i = 0;
        for (String content : contents) {
            System.out.println("第" + ++i + "个:" + content);
        }
    }

    public static void print(Object content) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
        System.out.println(df.format(date) + ":" + content);
    }

    public static void printMap(Map<Object, Object> contentMap) {
        for (Map.Entry<Object, Object> entry : contentMap.entrySet()) {
            Print.print("当前Key：" + entry.getKey());
            Print.print(entry.getValue());
        }
    }
}
