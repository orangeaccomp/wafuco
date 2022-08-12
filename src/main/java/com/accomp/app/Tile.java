package com.accomp.app;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class Tile implements Cloneable {
    private BufferedImage img;

    // Pattern
    Edge top;
    Edge right;
    Edge bot;
    Edge left;

    // incompatible Tiles
    Set<Tile> incompatibleTop = new HashSet<>();
    Set<Tile> incompatibleRight = new HashSet<>();
    Set<Tile> incompatibleBot = new HashSet<>();
    Set<Tile> incompatibleLeft = new HashSet<>();

    public Tile(BufferedImage img) {
        this.img = img;
        makeEdge();
    }

    private void makeEdge() {
        top = new Edge(new int[img.getWidth()]);
        right = new Edge(new int[img.getHeight()]);
        bot = new Edge(new int[img.getWidth()]);
        left = new Edge(new int[img.getHeight()]);

        for (int x = 0; x < img.getWidth(); x++) {
            top.set(x, img.getRGB(x, 0));
            bot.set(x, img.getRGB(x, img.getHeight() - 1));
        }

        for (int y = 0; y < img.getHeight(); y++) {
            right.set(y, img.getRGB(img.getWidth() - 1, y));
            left.set(y, img.getRGB(0, y));
        }
    }

    public Tile clone() {
        return new Tile(img);
    }

    public BufferedImage getImg(){
        return this.img;
    }
}
