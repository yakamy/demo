package com.example.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/1/23.
 */
public class ImageUtil {

    private BufferedImage bufferedImage;

    public ImageUtil(File file) throws IOException {
        this.bufferedImage = ImageIO.read(file);
    }

    public ImageUtil Inverter() {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        for (int u = 0; u < width; u++) {
            for (int v = 0; v < height; v++) {
                int rgb = bufferedImage.getRGB(u, v);
                bufferedImage.setRGB(u, v, 255 - rgb);
            }
        }
        return this;
    }

    public static BufferedImage Rotate270Degree(BufferedImage image){
        int height = image.getHeight();
        int width = image.getWidth();
        int type = image.getType();
        BufferedImage bufferedImage = new BufferedImage(height, width, type);
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                int rgb = image.getRGB(width-j-1, i);
                bufferedImage.setRGB(i, j, rgb);
            }
        }
        return bufferedImage;
    }

    public ImageUtil getHistogram(){

        return this;
    }

}
