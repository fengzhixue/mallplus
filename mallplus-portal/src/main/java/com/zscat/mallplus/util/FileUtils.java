package com.zscat.mallplus.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件工具类
 *
 * @author Peter
 * @date 2018-4-11
 */
public class FileUtils {
    /**
     * 判断文件类型是否合法<>通过魔数判断</>
     *
     * @param file
     * @return
     */
    public static boolean checkFileMagicNum(final MultipartFile file) {
        if (file == null) {
            return false;
        }
        // 获取文件上传时带的后缀
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 由文件后缀得到的魔数，由于后缀可以伪造，所以该魔数不一定是文件的真实魔数
        String magicNum = FileTypeMap.map.get(suffix.toLowerCase());
        // 取不到魔数，说明该文件类型不能上传
        if (magicNum == null) {
            return false;
        }
        // 取到魔数之后，判断与文件的是否相同
        byte[] b = new byte[30];
        try {
            InputStream in = file.getInputStream();
            // 读取上传文件的前30个字节
            in.read(b, 0, 30);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String realMagicNum = bytesToHex(b);
        // 判断文件开始的一段内容是否是匹配的魔数
        if (realMagicNum.toUpperCase().startsWith(magicNum)) {
            return true;
        }
        return false;
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHex(final byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String getSuffix(final MultipartFile file) {
        if (file == null || file.getSize() == 0) {
            return null;
        }
        String fileName = file.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
