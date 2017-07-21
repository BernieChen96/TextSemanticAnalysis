package util;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Created by 51157 on 2017/7/17.
 */
//定义JNA接口
public interface CLibrary extends Library {
    //建立实例
    CLibrary Instance = (CLibrary) Native.loadLibrary("./lib/NLPIR", CLibrary.class);


    //系统初始化
    public int NLPIR_Init(byte[] sDataPath, int encoding, byte[] sLicenceCode);

    //段落处理
    public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);

    //获取关键词
    public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit, boolean bWeightOut);

    //退出函数
    public void NLPIR_Exit();

    //文档处理
    public double NLPIR_FileProcess(String sSourceFilename, String sResultFilename, int bPOStagged);

    //引入用户自定义词典
    public int NLPIR_ImportUserDict(String sFilename, Boolean bOverwrite);

    //添加用户新词并标注词性
    public int NLPIR_AddUserWord(String sWords);
}

