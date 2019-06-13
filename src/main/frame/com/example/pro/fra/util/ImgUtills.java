package com.example.pro.fra.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 图像处理工具类
 */
public class ImgUtills {
    /**
     * 裁剪图片方法
     *
     * @param base64 Base64图像
     * @param startX 裁剪开始x坐标
     * @param startY 裁剪开始y坐标
     * @param endX   裁剪结束x坐标
     * @param endY   裁剪结束y坐标
     * @return
     */
    public static String cropImage(String base64, int startX, int startY, int endX, int endY) {

        BASE64Encoder encoder = new BASE64Encoder();
        BASE64Decoder decoder = new BASE64Decoder();
        String resultImg = null;
        try {
            //base64转成BufferedImage
            byte[] buffer = decoder.decodeBuffer(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            BufferedImage bufferedImage = ImageIO.read(bais);

            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (startX == -1) {
                startX = 0;
            }
            if (startY == -1) {
                startY = 0;
            }
            if (endX == -1) {
                endX = width - 1;
            }
            if (endY == -1) {
                endY = height - 1;
            }
            BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);
            for (int x = startX; x < endX; ++x) {
                for (int y = startY; y < endY; ++y) {
                    int rgb = bufferedImage.getRGB(x, y);
                    result.setRGB(x - startX, y - startY, rgb);
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(result, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            resultImg = encoder.encodeBuffer(bytes).trim();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultImg;
    }

    /**
     * 将图片压缩到指定大小以内
     *
     * @param oldStringBase64 源图片数据
     * @param maxSize         目的图片大小
     * @return 压缩后的图片数据
     */
    public static String compressUnderSize(String oldStringBase64, long maxSize) {
        BASE64Encoder encoder = new BASE64Encoder();
        BASE64Decoder decoder = new BASE64Decoder();
        double scale = 0.9;
        byte[] imgData = null;
        try {
            byte[] srcImgData = decoder.decodeBuffer(oldStringBase64);
            imgData = Arrays.copyOf(srcImgData, srcImgData.length);
            if (imgData.length > maxSize) {
                do {
                    imgData = compress(imgData, scale);
                } while (imgData.length > maxSize);
            }
        } catch (IOException e) {
            throw new IllegalStateException("压缩图片过程中出错，请及时联系管理员！", e);
        }

        return encoder.encode(imgData);
    }

    /**
     * 判断目标字符窜是否大于指定大小
     *
     * @param targetString
     * @param expectSize   默认单位 kb
     * @return
     */
    public static boolean isGltTargetSize(String targetString, int expectSize) {
        Integer equalIndex = targetString.indexOf("=");//2.找到等号，把等号也去掉
        if (targetString.indexOf("=") > 0) {
            targetString = targetString.substring(0, equalIndex);
        }
        Integer strLength = targetString.length();//3.原来的字符流大小，单位为字节
        Integer size = strLength - (strLength / 8) * 2;//4.计算后得到的文件流大小，单位为字节
        return size > expectSize;
    }

    /**
     * 按照 宽高 比例压缩
     *
     * @param scale 压缩刻度
     * @return 压缩后图片数据
     * @throws IOException 压缩图片过程中出错
     */
    public static byte[] compress(byte[] srcImgData, double scale) throws IOException {

        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(srcImgData));
        int width = (int) (bi.getWidth() * scale); // 源图宽度
        int height = (int) (bi.getHeight() * scale); // 源图高度

        Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = tag.getGraphics();
        g.setColor(Color.RED);
        g.drawImage(image, 0, 0, null); // 绘制处理后的图
        g.dispose();

        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        ImageIO.write(tag, "JPEG", bOut);

        return bOut.toByteArray();
    }

//    private String cutFacePic2Base64(String base64, FaceModel model) {
//        BASE64Encoder encoder = new BASE64Encoder();
//        BASE64Decoder decoder = new BASE64Decoder();
//
//        String formatName = "PNG";
//        byte[] buffer = decoder.decodeBuffer(base64);
//        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
//        BufferedImage bufferedImage = ImageIO.read(bais);
//        InputStream is = BaseToInputStream(base64Img);
//        try {
//            ImageInputStream iis = ImageIO.createImageInputStream(is);
//            reader.setInput(iis, true);
//            ImageReadParam param = reader.getDefaultReadParam();
//            Rectangle rect = new Rectangle(hjFaceModel.getLeft(), hjFaceModel.getTop(), hjFaceModel.getRight() - hjFaceModel.getLeft(), hjFaceModel.getBottom() - hjFaceModel.getTop());
//            param.setSourceRegion(rect);
//            BufferedImage bi = reader.read(0, param);
//            // bufferImage->base64
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            ImageIO.write(bi, formatName, outputStream);
//            base64Img = Base64.encodeBase64String(outputStream.toByteArray());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return base64Img;
//    }

}
