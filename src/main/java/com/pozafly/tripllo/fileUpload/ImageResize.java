package com.pozafly.tripllo.fileUpload;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ImageResize {

    private static final double STANDARD_WIDTH = 640;
    static int newWidth;
    static int newHeight;

    public static File resize(File convertedFile, String extension) throws IOException {

        Image image = ImageIO.read(convertedFile);

        int width = image.getWidth(null);
        int height = image.getHeight(null);

        if(width > 640 || height > 640) {
            double ratio = STANDARD_WIDTH / (double) width;
            newWidth = (int)(width * ratio);
            newHeight = (int)(height * ratio);
        } else {
            newWidth = width;
            newHeight = height;
        }

        Image resizeImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);

        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = newImage.getGraphics();
        g.drawImage(resizeImage, 0, 0, null);
        g.dispose();

        ImageIO.write(newImage, extension, convertedFile);
        //convertedFile.delete();

        return convertedFile;
    }

    public static String createFileName(String userId, String extension) {
        UUID uuid = UUID.randomUUID();
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        return userId + "_" + uuid.toString() + "_" + format.format(now) + "." + extension;
    }
}
