package cn.lger.util;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordUtils {
    /**
     * 写入word工具类
     * @author z
     *
     */


    /**
     * 替换段落里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数，导入的数据
     */
    public void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceInPara(para, params);
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    public void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        //Matcher matcher;
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();

            int start = -1;
            int end = -1;
            String str = "";
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                if ('$' == runText.charAt(0) && '{' == runText.charAt(1)) {
                    start = i;
                }
                if ((start != -1)) {
                    str += runText;
                }
                if ('}' == runText.charAt(runText.length() - 1)) {
                    if (start != -1) {
                        end = i;
                        break;
                    }
                }
            }

            for (int i = start; i <= end; i++) {
                para.removeRun(i);
                i--;
                end--;
            }

            for (String key : params.keySet()) {
                if (str.equals(key)) {
                    if(str.startsWith("${image"))
                    {
                        try {
                            XWPFRun run = para.createRun();
                            run.addBreak();
                            run.addPicture(
                                    new FileInputStream("d:/upload/images/wx509992ee3584678d.o6zAJs01U_6wpaKpA2LByp1IjMoc.vr4C0GKafu7X506eca9c5c0e0cf89a452bc3757e7eb1.png"),
                                    Document.PICTURE_TYPE_JPEG, "d:/upload/images/wx509992ee3584678d.o6zAJs01U_6wpaKpA2LByp1IjMoc.vr4C0GKafu7X506eca9c5c0e0cf89a452bc3757e7eb1.png", Units.toEMU(80), Units.toEMU(120)); // 200x200 pixels
                            run.addBreak(BreakType.PAGE);
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else {
                        para.createRun().setText((String) params.get(key));
                    }
                    break;
                }
            }


        }
    }

    /**
     * 替换表格里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    public void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        this.replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 关闭输入流
     *
     * @param is
     */
    public void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertImage(String key, XWPFDocument doc) {

        List<XWPFParagraph> paragraphList = doc.getParagraphs();
        try {
            if (paragraphList != null && paragraphList.size() > 0) {

                for (XWPFParagraph paragraph : paragraphList) {
                    List<XWPFRun> runs = paragraph.getRuns();
                    for (XWPFRun run : runs) {
                        String text = run.getText(0);
                        if (text != null) {
                            if (text.indexOf(key) >= 0) {
                                run.addBreak();
                                run.addPicture(
                                        new FileInputStream("d:/upload/images/wx509992ee3584678d.o6zAJs01U_6wpaKpA2LByp1IjMoc.vr4C0GKafu7X506eca9c5c0e0cf89a452bc3757e7eb1.png"),
                                        Document.PICTURE_TYPE_JPEG, "d:/upload/images/wx509992ee3584678d.o6zAJs01U_6wpaKpA2LByp1IjMoc.vr4C0GKafu7X506eca9c5c0e0cf89a452bc3757e7eb1.png", Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
                                run.addBreak(BreakType.PAGE);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

