package com.mycompany.app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TileMeta {
    private int width = 10;
    private int height = 10;
    private ArrayList<Tile> tiles;

    public TileMeta(int width, int height, String fileImage, String fileConfig) throws Exception {
        this.width = width;
        this.height = height;

        loadTiles(fileImage);
        SetTilePatterns(new Config(fileConfig));
        giveTilesIncompatible();
    }

    private TileMeta loadTiles(String path) throws Exception {
        BufferedImage baseImage = ImageIO.read(new File(path));
        if (baseImage.getWidth() % this.width != 0) {
            throw new Exception("width of base image should be %0 to tile size");
        }
        if (baseImage.getHeight() % this.height != 0) {
            throw new Exception("height of base image should be %0 to tile size");
        }

        this.tiles = new ArrayList<>();
        for (int y = 0; y < baseImage.getHeight(); y += this.height) {
            for (int x = 0; x < baseImage.getWidth(); x += this.width) {
                BufferedImage subImg = baseImage.getSubimage(x, y, this.width, this.height);
                Tile tile = new Tile(subImg);
                this.tiles.add(tile);
            }
        }
        return this;
    }

    private TileMeta SetTilePatterns(Config config) {
        int mainIndex = 0;
        for (Tile mainTile : this.tiles) {
            mainTile.top = config.get(mainIndex).top;
            mainTile.right = config.get(mainIndex).right;
            mainTile.bot = config.get(mainIndex).bot;
            mainTile.left = config.get(mainIndex).left;
            
            mainIndex++;
        }
        return this;
    }

    private TileMeta giveTilesIncompatible(){
        for(Tile main: this.tiles){
            for(Tile match: this.tiles){
                if(main.top != match.bot){
                    main.incompatibleTop.add(match);
                }
                if(main.right != match.left){
                    main.incompatibleRight.add(match);
                }
                if(main.bot != match.top){
                    main.incompatibleBot.add(match);
                }
                if(main.left != match.right){
                    main.incompatibleLeft.add(match);
                }
            }
        }
        return this;        
    }

    public ArrayList<Tile> getTiles() {
        return this.tiles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
