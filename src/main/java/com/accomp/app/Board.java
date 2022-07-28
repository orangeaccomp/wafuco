package com.accomp.app;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.imageio.ImageIO;

public class Board {
    int dimensionWidth, dimensionHeight;
    TileMeta tileMeta;
    ArrayList<Entropy> spots;

    public Board(TileMeta tileMeta, int width, int height) {
        this.tileMeta = tileMeta;
        this.dimensionWidth = width;
        this.dimensionHeight = height;

        makeSpots(tileMeta.getTiles());
    }

    public void run() throws Exception {
        while (!this.isFullCollapst()) {
            iterat();
        }
    }

    public boolean iterat() throws Exception {
        if (isFullCollapst())
            return true;

        Entropy entropy = findLowestEntropy();
        entropy.influencedCollaps();
        changeStateNeighbor(entropy);

        return isFullCollapst();
    }

    public void buildImage(String path) throws Exception {
        BufferedImage img = new BufferedImage(
                dimensionWidth * this.tileMeta.getWidth(),
                dimensionHeight * this.tileMeta.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics graphics = img.getGraphics();

        for (Entropy entropy : this.spots) {
            entropy.collaps();
        }

        for (int i = 0; i < this.spots.size(); i++) {
            Entropy entropy = this.spots.get(i);
            BufferedImage imageOnSpot = entropy.getImage();

            int x = ((int) i % this.dimensionWidth) * this.tileMeta.getWidth();
            int y = ((int) i / this.dimensionHeight) * this.tileMeta.getHeight();
            graphics.drawImage(imageOnSpot, x, y, null);
        }

        graphics.dispose();

        String date = new Date().toString();
        ImageIO.write(img, "png", new File(path + "/Collapsed" + date + ".png"));
    }

    public boolean isFullCollapst() {
        for (Entropy entropy : this.spots) {
            if (!entropy.isFinal()) {
                return false;
            }
        }
        return true;
    }

    private void makeSpots(Collection<Tile> tiles) {
        this.spots = new ArrayList<>();
        for (int i = 0; i < this.dimensionWidth * this.dimensionHeight; i++) {
            spots.add(new Entropy(tiles));
        }
    }

    private void changeStateNeighbor(Entropy entropy) throws Exception {
        int index = this.spots.indexOf(entropy);
        if (index < 0) {
            throw new Exception("entropy not in list of spots");
        }

        int indexTop = index - this.dimensionWidth;
        int indexRight = index + 1;
        int indexBot = index + this.dimensionWidth;
        int indexLeft = index - 1;

        if (indexTop >= 0 && indexTop < this.spots.size()) {
            this.spots.get(indexTop).reduce(entropy.getTile().incompatibleTop);
        }
        if (indexRight >= 0 && indexRight < this.spots.size()) {
            this.spots.get(indexRight).reduce(entropy.getTile().incompatibleRight);
        }
        if (indexBot >= 0 && indexBot < this.spots.size()) {
            this.spots.get(indexBot).reduce(entropy.getTile().incompatibleBot);
        }
        if (indexLeft >= 0 && indexLeft < this.spots.size()) {
            this.spots.get(indexLeft).reduce(entropy.getTile().incompatibleLeft);
        }
    }

    private Entropy findLowestEntropy() {
        Entropy lowestEntropy = null;
        int size = Integer.MAX_VALUE;
        for (Entropy entropy : this.spots) {
            if (entropy.getEntropySize() < size && !entropy.isFinal()) {
                lowestEntropy = entropy;
            }
        }
        return lowestEntropy;
    }

    public String toString() {
        String boardAsString = new String("board[" + this.spots.size() + "]:");
        for (Entropy entropy : this.spots) {
            boardAsString += " " + entropy;
        }
        return boardAsString;
    }
}
