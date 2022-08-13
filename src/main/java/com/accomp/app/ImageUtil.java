package com.accomp.app;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

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

    public static void debugEdgeGroup(Collection<Edge> edges) {
        int width = edges.stream().findAny().get().getPixels().length;
        int height = edges.size();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int y = 0;
        for (Edge edge : edges) {
            for (int x = 0; x < width; x++)
                image.setRGB(x, y, edge.getPixels()[x]);
            y++;
        }
        try {
            ImageIO.write(image, "png", new File("debug" + File.separator + "Edges.png"));
        } catch (IOException e) {           
            e.printStackTrace();
        }
    }

    public static void debugTiles(Collection<Tile> tiles) {
        if (tiles.size() < 1) {
            return;
        }

        BufferedImage tileImageExample = tiles.stream().findAny().get().getImg();
        int tileWidth = tileImageExample.getWidth();
        int tileHeight = tileImageExample.getHeight();

        double sqrt = Math.sqrt(tiles.size());
        double ceil = Math.ceil(sqrt);
        int tileCountX = (int) ceil;
        int tileCountInY = (int) ceil;
        BufferedImage image = new BufferedImage(tileCountX * tileWidth, tileCountInY * tileHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();    

        int i = 0;
        for (Tile tile : tiles) {
            int x = i % tileCountX;
            int y = i / tileCountX;
            graphics.drawImage(tile.getImg(), x * tileWidth, y * tileHeight, null);
            i++;
        }

        graphics.dispose();
        try {
            ImageIO.write(image, "png", new File("debug" + File.separator + "Tiles.png"));
        } catch (IOException e) {            
            e.printStackTrace();
        }
    }

    public static String hash(BufferedImage img) {
        String str = "";
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                str += img.getRGB(x, y);
            }
        }
        HashFunction hashFunction = Hashing.sha256();
        HashCode hashCode = hashFunction.hashUnencodedChars(str);   
        return hashCode.toString();
    }

}
