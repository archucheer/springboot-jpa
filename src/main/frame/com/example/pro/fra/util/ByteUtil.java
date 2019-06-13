package com.example.pro.fra.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Copyright (C), 2018-2019, 福州海景科技有限公司
 *
 * @ClassName: ImageUtil
 * @Description: bype工具类
 * @Author: lqs
 * @Date: 2019/2/22 15:31
 */
public class ByteUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(ByteUtil.class.getSimpleName());
    //文件转byte数组
    public static byte[] file2byte(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        }
        catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        }
        catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }
    //byte数组转成文件
    public void byte2file(byte[] data,String path){
        if(data.length<3||path.equals("")) return;
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            LOGGER.info("生成文件成功，文件目录："+path);
        } catch(Exception ex) {
            LOGGER.info("生成文件失败："+ex);
            ex.printStackTrace();
        }
    }
}
