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

    public static BufferedImage mirror(BufferedImage src) {
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
}
