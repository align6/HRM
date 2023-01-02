package com.ihrm.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.util.Date;

public class QiniuUploadUtils {

    private static final String accessKey = "hYwT0UwseDc9h0aSF5SqwOUes71uI2yoxrgZMNpC";
    private static final String secretKey = "OgeK56dQh_pclIDxEOHtzW6jWXbgHKmSlxGywb53";
    private static final String bucket = "staffphoto-bucket";
    private static final String prix = "http://r8opc3p1v.hn-bkt.clouddn.com/";
    private UploadManager manager;

    public QiniuUploadUtils(){
        //初始化基本配置
        Configuration cfg = new Configuration(Region.autoRegion());
        //创建上传管理器
        manager = new UploadManager(cfg);
    }

    /**
     *
     * @param imgName ：文件名
     * @param bytes  ：文件的byte数组
     * @return
     */
    public String upload(String imgName, byte[] bytes){
        Auth auth = Auth.create(accessKey,secretKey);
        //构造覆盖上传token
        String upToken = auth.uploadToken(bucket,imgName);
        try {
            Response response = manager.put(bytes, imgName, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            //返回请求地址
            return prix+putRet.key+"?t="+new Date().getTime();
        }catch (QiniuException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
