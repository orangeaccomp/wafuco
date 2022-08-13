package com.accomp.app;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TileMeta {
    private int width = 10;
    private int height = 10;
    private ArrayList<Tile> tiles;

    private BufferedImage baseImage;

    public TileMeta(int width, int height, BufferedImage baseImage) throws Exception {
        this.width = width;
        this.height = height;
        this.baseImage = baseImage;

        creatTiles();
        creatRotationalVariation();
        creatMirroredVariation();
        //deletDuplicate();
        giveTilesIncompatible();


        // debug
        analyseEdges();
        analyseTileVariation();
    }

    private void creatTiles() throws Exception {
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
    }

    private void giveTilesIncompatible() {
        for (Tile main : this.tiles) {
            for (Tile match : this.tiles) {
                if (!main.top.isEqual(match.bot)) {
                    main.incompatibleTop.add(match);
                }
                if (!main.right.isEqual(match.left)) {
                    main.incompatibleRight.add(match);
                }
                if (!main.bot.isEqual(match.top)) {
                    main.incompatibleBot.add(match);
                }
                if (!main.left.isEqual(match.right)) {
                    main.incompatibleLeft.add(match);
                }
            }
        }
    }

    private void creatRotationalVariation() {
        Collection<Tile> rotatedTiles = new ArrayList<>();
        for (Tile mainTile : this.tiles) {
            Tile ninety = this.creatRotatedTile(mainTile);
            Tile oneEighty = this.creatRotatedTile(ninety);
            Tile sevenTwenty = this.creatRotatedTile(oneEighty);
            rotatedTiles.add(ninety);
            rotatedTiles.add(oneEighty);
            rotatedTiles.add(sevenTwenty);
        }
        this.tiles.addAll(rotatedTiles);
    }

    private void creatMirroredVariation() {
        Collection<Tile> mirroredTiles = new ArrayList<>();
        for (Tile mainTile : this.tiles) {
            mirroredTiles.add(creatMirroredTile(mainTile));
        }
        this.tiles.addAll(mirroredTiles);
    }

    // fix deleting all symetery tiles
    private void deletDuplicate() {
        ArrayList<Tile> dupplicateTiles = new ArrayList<>();
        Set<Tile> saveDupplicateTiles = new HashSet<>();
        for (Tile tileA : this.tiles) {
            for (Tile tileB : this.tiles) {
                if (tileA != tileB) {
                    boolean duplicat = ImageUtil.equal(tileA.getImg(), tileB.getImg());
                    if (duplicat) {
                        dupplicateTiles.add(tileB);
                        saveDupplicateTiles.add(tileB);
                    }
                }
            }
        }
        System.out.println("removing: " + dupplicateTiles.size());
        this.tiles.removeAll(dupplicateTiles);
        this.tiles.addAll(saveDupplicateTiles);
    }

    // 90deg
    private Tile creatRotatedTile(Tile tile) {
        BufferedImage rotatedImg = ImageUtil.rotate(tile.getImg());
        Tile rotaTile = new Tile(rotatedImg);
        return rotaTile;
    }

    private Tile creatMirroredTile(Tile tile) {
        BufferedImage mirroedImage = ImageUtil.mirrorX(tile.getImg());
        Tile mirroredTile = new Tile(mirroedImage);
        return mirroredTile;
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

    private void analyseEdges() {
        ArrayList<Edge> edges = new ArrayList<>();
        for (Tile tile : this.tiles) {
            edges.add(tile.top);
            edges.add(tile.right);
            edges.add(tile.bot);
            edges.add(tile.left);
        }

        HashMap<String, Edge> map = new HashMap<>();
        for (Edge edge : edges) {
            map.put(edge.hash(), edge);
        }
        System.out.println(map.keySet().size());
        ImageUtil.debugEdgeGroup(map.values());
    }

    private void analyseTileVariation(){
        ImageUtil.debugTiles(tiles);
    }

}
