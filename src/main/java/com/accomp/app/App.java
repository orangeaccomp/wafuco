package com.accomp.app;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class App {
    public static void main(String[] args) {
        try {
            BufferedImage baseImage = ImageIO.read(new File("./data/clearMap3.png"));
            TileMeta meta = new TileMeta(10, 10, baseImage);
            Board board = new Board(meta, 20, 20); // TODO if width != height image is broken
            board.run();
            board.buildImage("./generated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
