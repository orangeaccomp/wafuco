package com.accomp.app;

import java.awt.image.BufferedImage;

public class ImageUtil {
    public static BufferedImage rotate(BufferedImage src) {
        final int w = src.getWidth();
        final int h = src.getHeight();
        BufferedImage rot = new BufferedImage(w, h, src.getType());
        for (int x = 0; x < h; x++) {
            for (int y = 0; y < h; y++) {
                int tranX = w - y - 1;
                int tranY = x;
                int rgb = src.getRGB(x, y);
                rot.setRGB(tranX, tranY, rgb);
            }
        }
        return rot;
    }

    public static BufferedImage mirrorX(BufferedImage src) {
        final int w = src.getWidth();
        final int h = src.getHeight();
        BufferedImage mir = new BufferedImage(w, h, src.getType());
        for (int x = 0; x < h; x++) {
            for (int y = 0; y < h; y++) {
                int tranX = w - x - 1;
                int tranY = y;
                int rgb = src.getRGB(x, y);
                mir.setRGB(tranX, tranY, rgb);
            }
        }
        return mir;
    }

    public static BufferedImage getEmptyImage() {
        return new BufferedImage(10, 10, BufferedImage.TYPE_BYTE_BINARY);
    }

    public static boolean equal(BufferedImage a, BufferedImage b) {
        int width = a.getWidth();
        int height = a.getHeight();
        if (width != b.getWidth() || height != b.getWidth())
            return false;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                int rgbA = a.getRGB(x, y);
                int rgbB = b.getRGB(x, y);
                if (rgbA != rgbB)
                    return false;
            }
        }
        return true;
    }
}
