package com.example.train;

import com.example.util.HttpUtil;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Administrator on 2016/12/22.
 */
public class Home {
    public static void index() throws Exception {
        try {
            String path = "C:\\Users\\Administrator\\Desktop\\identy";
            BufferedImage image = ImageIO.read(new File(path, "train.jpg"));
            //image = ImageHelper.convertImageToGrayscale(ImageHelper.getSubImage(image, 0, 0, image.getWidth(), image.getHeight()));
            ITesseract instance = new Tesseract();
            String result = instance.doOCR(image);
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
        hehe();
    }
    public static void hehe(){
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
