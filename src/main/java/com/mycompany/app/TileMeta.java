package com.mycompany.app;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

public class TileMeta {
    private int width = 10;
    private int height = 10;
    private ArrayList<Tile> tiles;

    private Config config;
    private BufferedImage baseImage;

    public TileMeta(int width, int height, BufferedImage baseImage, Config conig) throws Exception {
        this.width = width;
        this.height = height;
        this.config = conig;
        this.baseImage = baseImage;

        loadTiles();
        SetTilePatterns();
        creatRotationalVariation();
        giveTilesIncompatible();
    }

    private TileMeta loadTiles() throws Exception {
        if (baseImage.getWidth() % this.width != 0) {
            throw new Exception("width of base image should be %0 to tile size");
        }
        if (baseImage.getHeight() % this.height != 0) {
            throw new Exception("height of base image should be %0 to tile size");
        }

        this.tiles = new ArrayList<>();
        for (int y = 0; y < this.baseImage.getHeight(); y += this.height) {
            for (int x = 0; x < this.baseImage.getWidth(); x += this.width) {
                BufferedImage subImg = this.baseImage.getSubimage(x, y, this.width, this.height);
                Tile tile = new Tile(subImg);
                this.tiles.add(tile);
            }
        }
        return this;
    }

    private void SetTilePatterns() {
        int mainIndex = 0;
        for (Tile mainTile : this.tiles) {
            mainTile.top = this.config.get(mainIndex).top;
            mainTile.right = this.config.get(mainIndex).right;
            mainTile.bot = this.config.get(mainIndex).bot;
            mainTile.left = this.config.get(mainIndex).left;

            mainIndex++;
        }
    }

    private void giveTilesIncompatible() {
        for (Tile main : this.tiles) {
            for (Tile match : this.tiles) {
                if (main.top != match.bot) {
                    main.incompatibleTop.add(match);
                }
                if (main.right != match.left) {
                    main.incompatibleRight.add(match);
                }
                if (main.bot != match.top) {
                    main.incompatibleBot.add(match);
                }
                if (main.left != match.right) {
                    main.incompatibleLeft.add(match);
                }
            }
        }
    }

    private void creatRotationalVariation() {
        Collection<Tile> rotatedTiles = new ArrayList<>();
        int index = 0;
        for (Tile mainTile : this.tiles) {
            String rotation = this.config.get(index).rotation; // TODO add enum
            boolean mirror = this.config.get(index).mirror;

            if (rotation.equals("full")) {
                BufferedImage ninety = ImageUtil.ninety(mainTile.img);
                Tile ninetyTile = new Tile(ninety);
                ninetyTile.setPattern(mainTile.left, mainTile.top, mainTile.right, mainTile.bot);
                rotatedTiles.add(ninetyTile);

                BufferedImage oneEighty = ImageUtil.ninety(ninety);
                Tile oneEightyTile = new Tile(oneEighty);
                oneEightyTile.setPattern(mainTile.bot, mainTile.left, mainTile.top, mainTile.right);
                rotatedTiles.add(oneEightyTile);

               
                BufferedImage sevenTwenty = ImageUtil.ninety(oneEighty);
                Tile sevenTwentyTile = new Tile(sevenTwenty);
                sevenTwentyTile.setPattern(mainTile.right, mainTile.bot, mainTile.left, mainTile.top);
                rotatedTiles.add(sevenTwentyTile);
            }

            if(mirror){
                BufferedImage mirroredImage = ImageUtil.mirror(mainTile.img);
                Tile mirroredTile = new Tile(mirroredImage);
                mirroredTile.setPattern(mainTile.top, mainTile.left, mainTile.bot, mainTile.right);
                rotatedTiles.add(mirroredTile);
            }




            index++;
        }
        this.tiles.addAll(rotatedTiles);
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
