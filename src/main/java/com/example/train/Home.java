package com.example.train;

import com.example.util.ClearImageHelper;
import com.example.util.HttpUtil;
import com.example.util.ImageUtil;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/12/22.
 */
public class Home {
    public static void index() {
        try {
            String path = "C:\\Users\\Administrator\\Desktop\\identy";
            /*String path = "C:\\Users\\Administrator\\Desktop\\identy";
            BufferedImage image = ImageIO.read(new File(path, "5001_14_07024501A6B2B54728.gif"));*/
            //image = ImageHelper.convertImageToGrayscale(ImageHelper.getSubImage(image, 0, 0, image.getWidth(), image.getHeight()));
            BufferedImage image = ImageIO.read(new File(path, "5001_14_070234010ADD281834.gif"));
            image = ImageUtil.Rotate270Degree(image);
            image = ClearImageHelper.cleanImage(image);

            ITesseract instance = new Tesseract();
            List list = new ArrayList();
            list.add("digits");
            instance.setConfigs(list);
            instance.setTessVariable("tessedit_char_whitelist", "0123456789");

            String result = instance.doOCR(image);
            System.out.println(result);
            File file0 = new File(UUID.randomUUID().toString() + ".jpg");
            ImageIO.write(image, "jpg", file0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void index1() {
        try {
            String path = "C:\\Users\\Administrator\\Desktop\\identy";
            File file = new File(path, "5001_14_070234010ADD281834.gif");
            BufferedImage image = ImageIO.read(file);
            //逆向旋转
            image = ImageUtil.Rotate270Degree(image);


            int type = image.getType();
            BufferedImage image0 = ImageHelper.getSubImage(image, 215, 0, 230, 70);
            BufferedImage image1 = ImageHelper.getSubImage(image, 52, 125, 250, 50);
            BufferedImage image2 = ImageHelper.getSubImage(image, 394, 125, 250, 50);
            BufferedImage image3 = ImageHelper.getSubImage(image, 52, 200, 250, 50);
            BufferedImage image4 = ImageHelper.getSubImage(image, 394, 200, 250, 50);

            int height = image0.getHeight();
            int width = image0.getWidth();
            /*for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int rgb = image0.getRGB(i, j);
                    int r = ((rgb >> 16) & 0xFF);
                    int g = ((rgb >> 8) & 0xFF);
                    int b = ((rgb >> 0) & 0xFF);
                    if (r + g + b > 600) {
                        image0.setRGB(i, j, 0xFFFFFF);
                    } else {
                        image0.setRGB(i, j, 0x000000);
                    }
                }
            }*/
            ColorProcessor processor = new ColorProcessor(image0);
            processor.findEdges();
            image0 = (BufferedImage) processor.createImage();
            File file0 = new File(UUID.randomUUID().toString() + ".jpg");
            ImageIO.write(image0, "jpg", file0);
            ITesseract instance = new Tesseract();
            List list = new ArrayList();
            list.add("digits");
            instance.setConfigs(list);
            String result = instance.doOCR(image0);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void test() throws Exception {
        String path = "C:\\Users\\Administrator\\Desktop\\identy";
        BufferedImage image = ImageIO.read(new File(path, "train.jpg"));
        int width = image.getWidth();
        int height = image.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                System.out.println(rgb);
            }
        }

    }

    public static void main(String[] args) {
        index1();
    }

    public static void hehe() {
        try {
            String url = "http://passport.csdn.net/ajax/verifyhandler.ashx";
            InputStream inputStream = HttpUtil.post(url, "");
            if (inputStream == null) {
                return;
            }
            BufferedImage image = ImageIO.read(inputStream);
            File file = new File(UUID.randomUUID().toString() + ".jpg");
            ImageIO.write(image, "jpg", file);
            ITesseract instance = new Tesseract();
            String result = instance.doOCR(image);
            System.out.println(result);
            HttpUtil.releaseResource();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
