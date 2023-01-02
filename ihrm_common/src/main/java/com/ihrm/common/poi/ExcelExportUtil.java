package com.ihrm.common.poi;

import com.ihrm.domain.poi.ExcelAttribute;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class ExcelExportUtil<T> {
    private int rowIndex;  //行序号

    private int styleIndex;  //单元格格式序号

    private Class clazz;  //对象的字节码

    private Field fields[];  //对象中的所有属性

    public ExcelExportUtil(Class clazz, int rowIndex, int styleIndex){
        this.clazz = clazz;
        this.rowIndex = rowIndex;
        this.styleIndex = styleIndex;
        fields = clazz.getDeclaredFields();
        //getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段
    }

    /**
     * 基于注解导出
     */
    public void export(HttpServletResponse response, InputStream is, List<T> objs, String fileName) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook(is);
        Sheet sheet = wb.getSheetAt(0);
        CellStyle[] styles = getTemplateStyles(sheet.getRow(styleIndex));
        AtomicInteger datasAi = new AtomicInteger(rowIndex);
        for (T t : objs){
            Row row = sheet.createRow(datasAi.getAndIncrement());
            for (int i=0; i<styles.length; i++){
                Cell cell = row.createCell(i);  //创建单元格
                cell.setCellStyle(styles[i]);  //赋予单元格样式
                /**
                 * 给予单元格相应的内容
                 */
                for (Field field : fields){
                    if (field.isAnnotationPresent(ExcelAttribute.class)){  //A.isAnnotationPresent(B.class):B类型的注解是否在A类上
                        field.setAccessible(true);  //通过setAccessible(true)的方式关闭安全检查可以提升反射速度
                        ExcelAttribute ea = field.getAnnotation(ExcelAttribute.class);  //将注释的内容拿出来，与现在的style序号进行比较，相同则写入
                        if (i == ea.sort()){
                            if (field.get(t) != null){
                                cell.setCellValue(field.get(t).toString());
                            }
                        }
                    }
                }
            }
        }
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        fileName = response.encodeURL(new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        wb.write(response.getOutputStream());
    }

    /**
     * 将该行的单元格格式提取出来
     */
    public CellStyle[] getTemplateStyles(Row row){
        CellStyle[] styles = new CellStyle[row.getLastCellNum()];
        for (int i=0; i<row.getLastCellNum(); i++){
            styles[i] = row.getCell(i).getCellStyle();
        }
        return styles;
    }
}
