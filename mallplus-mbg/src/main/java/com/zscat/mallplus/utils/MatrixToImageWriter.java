package com.zscat.mallplus.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.core.io.ByteArrayResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 二维码的生成需要借助MatrixToImageWriter类，该类是由Google提供的，可以将该类直接拷贝到源码中使用
 */
public class MatrixToImageWriter {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private MatrixToImageWriter() {
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix, String words) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        Graphics2D outg = image.createGraphics();
        setGraphics2D(outg);
        // 画二维码到新的面板
        outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        // 画文字到新的面板
        //Color color=new Color(183,183,183);
        outg.setColor(Color.BLACK);
        // 字体、字型、字号
        outg.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        //文字长度
        int strWidth = outg.getFontMetrics().stringWidth(words);
        //总长度减去文字长度的一半  （居中显示）
        int wordStartX = (width - strWidth) / 2;
        //height + (outImage.getHeight() - height) / 2 + 12
        int wordStartY = height - 20;
        // 画文字
        outg.drawString(words, wordStartX, wordStartY);
        outg.dispose();
        image.flush();
        return image;
    }

    private static void setGraphics2D(Graphics2D graphics2D) {
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        Stroke s = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        graphics2D.setStroke(s);
    }

    static void writeToFile(BitMatrix matrix, String format, File file, String words) throws IOException {
        BufferedImage image = toBufferedImage(matrix, words);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    static void writeToStream(BitMatrix matrix, String format, OutputStream stream, String words) throws IOException {
        BufferedImage image = toBufferedImage(matrix, words);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }


    public static ByteArrayResource createQrCode(String url, String words) {
        try {
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
            //=======================生成文件 可以放在本地======================
            //File file = new File(path, fileName);
            //if (file.exists() || ((file.getParentFile().exists() || file.getParentFile().mkdirs()) && file.createNewFile())) {
            //   writeToFile(bitMatrix, "jpg", file);
            //    System.out.println("搞定：" + file);
            //}
            //=============此处生成流因为后面要上传到文件系统中，所以直接通过流上传===========
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writeToStream(bitMatrix, "jpg", outputStream, words);
            //==============生成输入流========================
            //ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ByteArrayResource arrayResource = new ByteArrayResource(outputStream.toByteArray()) {
                @Override
                public String getFilename() throws IllegalStateException {
                    return System.currentTimeMillis() + "";
                }
            };
            return arrayResource;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String text = "http://www.baidu.com"; // 二维码内容
        int width = 300; // 二维码图片宽度
        int height = 300; // 二维码图片高度
        String format = "jpg";// 二维码的图片格式

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
                BarcodeFormat.QR_CODE, width, height, hints);
        // 生成二维码
        File outputFile = new File("d:" + File.separator + "new.jpg");
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile, "123");
    }
}
