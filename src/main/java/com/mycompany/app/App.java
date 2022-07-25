package com.mycompany.app;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class App {
    public static void main(String[] args) {
        try {
            BufferedImage baseImage = ImageIO.read(new File("./data/map2.png"));
            Config config = new Config("./data/map2.json");
            TileMeta meta = new TileMeta(10, 10, baseImage, config);
            Board board = new Board(meta, 20, 20); // TODO if width != height image is broken
            board.run();
            board.buildImage("./generated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
