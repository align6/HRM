package com.ihrm;

import com.ihrm.common.poi.ExcelImportUtil;
import com.ihrm.domain.attendance.vo.AtteUploadVo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class PoiTest {
    public static void main(String[] args) throws FileNotFoundException {
        ExcelImportUtil<AtteUploadVo> util = new ExcelImportUtil<>(AtteUploadVo.class);
        FileInputStream stream = new FileInputStream(new File("D:\\3、黑马程序员iHRM 人力资源管理系统\\14和15\\day15\\like.xlsx"));
        List<AtteUploadVo> list = util.readExcel(stream,1,0);
        for (AtteUploadVo atteUploadVo : list){
            System.out.println(atteUploadVo);
        }
    }
}
