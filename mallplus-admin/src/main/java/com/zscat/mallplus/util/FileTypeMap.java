package com.zscat.mallplus.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件类型<p>允许上传的类型<p/>
 *
 * @author Peter
 * @date 2018-4-11
 */
public class FileTypeMap {

    public static final Map<String, String> map = new HashMap<String, String>(9);

    static {
        map.put("jpeg", "FFD8FF");
        map.put("jpg", "FFD8FFE0");
        map.put("png", "89504E47");
        map.put("wav", "57415645");
        map.put("avi", "41564920");
        map.put("mp4", "00000020667479706D70");
        map.put("mp3", "49443303000000002176");
    }


}
