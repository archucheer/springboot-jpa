package com.example.pro.fra.util;

import java.util.HashMap;

/**
 * @author Nebula
 * @version 1.0.0
 * @date 2018/7/14
 */
public class ContentTypeUtil {
    private static HashMap<String, String> contentTypeMap = new HashMap<>();

    static {
        contentTypeMap.put("image/png", ".png");
        contentTypeMap.put("image/jpeg", ".jpg");
        contentTypeMap.put("audio/amr", ".amr");
        contentTypeMap.put("audio/wav", ".wav");
        contentTypeMap.put("audio/mpeg3", ".mp3");
    }

    public static String getTypeByContentType(String contentType) {
        String type = contentTypeMap.get(contentType);
        if (null == type) {
            return "";
        }
        return type;
    }
}
