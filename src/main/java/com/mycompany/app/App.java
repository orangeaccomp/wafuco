package com.mycompany.app;

public class App {
    public static void main(String[] args) {        
        try {
            TileMeta meta = new TileMeta(
                    10, 10,
                    "./data/map2.png",
                    "./data/map2.json");
            Board board = new Board(meta, 12, 12); // TODO if width != height image is broken
            board.run();
            board.buildImage("./generated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
