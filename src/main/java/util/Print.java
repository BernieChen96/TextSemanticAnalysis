package util;

import java.util.List;

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

    public static void print(String content) {
        System.out.println(content);
    }
}
