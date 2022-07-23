package com.mycompany.app;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class Tile implements Cloneable {
    BufferedImage img;
    boolean top;
    boolean right;
    boolean bot;
    boolean left;

    Set<Tile> incompatibleTop = new HashSet<>();
    Set<Tile> incompatibleRight = new HashSet<>();
    Set<Tile> incompatibleBot = new HashSet<>();
    Set<Tile> incompatibleLeft = new HashSet<>();

    public Tile(BufferedImage img) {
        this.img = img;
    }

    public Tile clone(){
        Tile cloneTile = new Tile(img);
        cloneTile.incompatibleTop = incompatibleTop;
        cloneTile.incompatibleRight = incompatibleRight;
        cloneTile.incompatibleBot = incompatibleBot;
        cloneTile.incompatibleLeft = incompatibleLeft;
        return cloneTile;
    }
}
