package com.ihrm.common.poi;

import com.ihrm.domain.poi.ExcelAttribute;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelImportUtil<T> {
    private Class clazz;  //对象的字节码

    private Field fields[];  //对象中的所有属性

    public ExcelImportUtil(Class clazz){
        this.clazz = clazz;
        fields = clazz.getDeclaredFields();
    }

    /**
     * 基于注解读取excel
     */
    public List<T> readExcel(InputStream is, Integer rowIndex, Integer cellIndex){
        List<T> list = new ArrayList<T>();
        T entity = null;
        try {
            XSSFWorkbook wb = new XSSFWorkbook(is);
            Sheet sheet = wb.getSheetAt(0);
            Integer rowLength = sheet.getLastRowNum();  //不准确
            System.out.println(rowLength);

            for (int rowNum=rowIndex; rowNum<=rowLength; rowNum++){
                Row row = sheet.getRow(rowNum);
                entity = (T)clazz.newInstance();

                short cellLength = row.getLastCellNum();
                for (int cellNum=cellIndex; cellNum<=cellLength; cellNum++){
                    Cell cell = row.getCell(cellNum);
                    for (Field field : fields){
                        if (field.isAnnotationPresent(ExcelAttribute.class)){
                            field.setAccessible(true);
                            ExcelAttribute ea = field.getAnnotation(ExcelAttribute.class);
                            if (cellNum == ea.sort()){
                                field.set(entity, covertAttrType(field, cell));
                            }
                        }
                    }
                }
                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将cell单元格格式转为字段类型
     */
    private Object covertAttrType(Field field, Cell cell) throws Exception {
        String fieldType = field.getType().getSimpleName();
        if ("String".equals(fieldType)){
            return getValue(cell);
        }else if ("Date".equals(fieldType)){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getValue(cell));
        }else if ("int".equals(fieldType) || "Integer".equals(fieldType)){
            return Integer.parseInt(getValue(cell));
        }else if ("double".equals(fieldType) || "Double".equals(fieldType)){
            return Double.parseDouble(getValue(cell));
        }else {
            return null;
        }

    }

    /**
     * 将cell单元格格式转为String
     */
    public String getValue(Cell cell){
        if (cell == null){
            return "";
        }
        switch (cell.getCellType()){
            case STRING:
                return cell.getRichStringCellValue().getString().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                }else {
                    //防止数值变成科学计数法
                    String strCell = "";
                    Double num = cell.getNumericCellValue();
                    BigDecimal bigDecimal = new BigDecimal(num.toString());
                    if (bigDecimal != null){
                        strCell = bigDecimal.toPlainString();
                    }
                    //去除浮点型自动加的.0
                    if (strCell.endsWith(".0")){
                        strCell = strCell.substring(0, strCell.indexOf("."));
                    }
                    return strCell;
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}
