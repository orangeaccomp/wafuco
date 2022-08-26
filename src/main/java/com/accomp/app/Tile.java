package com.accomp.app;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class Tile implements Cloneable {
    private BufferedImage img;
    private long hash;

    // Pattern
    Edge top;
    Edge right;
    Edge bot;
    Edge left;

    // incompatible Tiles
    Set<Tile> incompatiblesTop = new HashSet<>();
    Set<Tile> incompatiblesRight = new HashSet<>();
    Set<Tile> incompatiblesBot = new HashSet<>();
    Set<Tile> incompatiblesLeft = new HashSet<>();

    public Tile(BufferedImage img) {
        this.img = img;
        this.hash = hash();
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

    private long hash() {
        return ImageUtil.hash(this.img);
    }

    public Tile clone() {
        return new Tile(img);
    }

    public BufferedImage getImg() {
        return this.img;
    }

    public long getHash() {
        return this.hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Tile other = (Tile) obj;
        if (this.getHash() != other.getHash()) {
            return false;
        }

        return true;
    }

}
