package util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;

/**
 * Created by 51157 on 2017/7/17.
 */
public class POI {
    /**
     * 读取Excel内容
     *
     * @param filePath
     * @return
     */
    public static String[][] readExcel(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return null;
        String[][] result = null;
        try {
            InputStream inputStream = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(inputStream);
            inputStream.close();

            //Excel工作表
            Sheet sheet = workbook.getSheet("已完成");
            //工作表中的行数
            int rowLength = sheet.getLastRowNum() + 1;
            //工作表的行
            Row row = sheet.getRow(0);
            //工作表的列数
            int colLength = row.getLastCellNum();
            System.out.println("行数：" + rowLength + ",列数：" + colLength);
            result = new String[rowLength-1][colLength];
            for (int i = 0; i < rowLength-1; i++) {
                row = sheet.getRow(i+1);
                for (int j = 0; j < colLength; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null)
                        cell.setCellType(CellType.STRING);
//                    System.out.println("第"+(i+1)+"行，第"+(j+1)+"列："+cell.getStringCellValue());
                    result[i][j] = cell.getStringCellValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return result;
    }
}
