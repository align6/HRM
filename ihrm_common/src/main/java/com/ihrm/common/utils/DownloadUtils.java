package com.ihrm.common.utils;

import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DownloadUtils {
    public void download(ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response, String fileName) throws IOException {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        //保存的文件名,必须和页面编码一致,否则乱码
        fileName = response.encodeURL(new String(fileName.getBytes(), StandardCharsets.ISO_8859_1));
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentLength(byteArrayOutputStream.size());
        //取得输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //写到输出流
        byteArrayOutputStream.writeTo(outputStream);
        //关闭
        byteArrayOutputStream.close();
        //刷数据
        outputStream.flush();
    }
}
