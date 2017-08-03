package textSemanticAnalysis.pretreament.participle;

import util.CLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by 51157 on 2017/7/17.
 */
public class Segment {
    /**
     * 对一段文字进行分词，返回标注词性的分词结果
     *
     * @param fileName
     * @return words
     * @throws Exception
     */
    public static String[] Segment(String fileName) throws Exception{
        //保存分词结果
        String result[]={"",""};
        String sourceString = "";
        //从文件中读入文本
        try {
            String encoding="UTF-8";

            File file=new File(fileName);

            if(file.isFile() && file.exists()){
                //判断文件是否存在
                String temp = null;
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);
                BufferedReader bufferedReader = new BufferedReader(read);

                while((temp = bufferedReader.readLine()) != null){
                    sourceString += temp;
                }

                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        //进行分词，对NLPIR初始化
        String argu = "";
        String system_charset = "UTF-8";
        int charset_type = 1;
        int init_flag = CLibrary.Instance.NLPIR_Init(argu.getBytes(system_charset), charset_type, "1".getBytes(system_charset));

//        AddUserWords("dic/dic.txt");

        if(0 == init_flag){
            System.out.println("init fail!");
            return null;
        }

        //保存分词结果
        String nativeBytes = null;
        //保存关键词
        String nativeByte = null;
        try{
            //分词
            nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(sourceString, 1);
            //获取关键词
            nativeByte = CLibrary.Instance.NLPIR_GetKeyWords(sourceString, 5, true);
        }catch(Exception e){
            e.printStackTrace();
        }
        result[0] = nativeBytes;
        result[1] = nativeByte;
        //返回分词结果
        return result;
    }

    /**
     * 添加用户词典并进行词性标注
     * @param filePath
     */
    public static void AddUserWords(String filePath){
        try{
            String encoding = "UTF-8";
            File file = new File(filePath);
            if(file.isFile()&&file.exists()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferReader = new BufferedReader(read);
                String lineText = "";
                while((lineText = bufferReader.readLine()) != null){
                    CLibrary.Instance.NLPIR_AddUserWord(lineText);
                }
            }
            else{
                System.out.println("未找到文件！");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
