package com.accomp.app;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.imageio.ImageIO;

public class Board {
    private int dimensionWidth, dimensionHeight;
    private TileMeta tileMeta;
    private ArrayList<Entropy> spots;

    public Board(TileMeta tileMeta, int width, int height) {
        this.tileMeta = tileMeta;
        this.dimensionWidth = width;
        this.dimensionHeight = height;

        makeSpots(tileMeta.getTiles());
    }

    public void run() throws Exception {
        long startTime  = System.currentTimeMillis();
        while (!this.isFullCollapst()) {
            // TODO remove need for newstart
            boolean newstart = false;
            newstart = !iterat();            
            if(newstart){
                makeSpots(tileMeta.getTiles());
            }
        }
        long endTime = System.currentTimeMillis();
        long runTime = endTime - startTime;
        System.out.println("run endet after: "+ runTime + " ms");
    }

    private boolean iterat() throws Exception {
        Entropy entropy = findLowestEntropy();
        entropy.collaps();
        return changeStateNeighbor(entropy);
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
            BufferedImage imageOnSpot = entropy.getImageFromFinalTile();

            int x = ((int) i % this.dimensionWidth) * this.tileMeta.getWidth();
            int y = ((int) i / this.dimensionHeight) * this.tileMeta.getHeight();
            graphics.drawImage(imageOnSpot, x, y, null);
        }

        graphics.dispose();

        // long date = new Date().getTime();
        ImageIO.write(img, "png", new File(path + File.separator + "Collapsed.png"));
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

    /**
     * remove all incompatible Tiles from Entrophys around one center Entrophy
     * @param entropy determents all neighbors and the incombatible Tiles
     * @return true if all neighbor states were changed to a legal state
     * @throws Exception
     */
    private boolean changeStateNeighbor(Entropy entropy) throws Exception {
        int index = this.spots.indexOf(entropy);
        if (index < 0) {
            throw new Exception("entropy not in list of spots");
        }

        // finde Neighbors
        int indexTop = index - this.dimensionWidth;
        int indexRight = index + 1;
        int indexBot = index + this.dimensionWidth;
        int indexLeft = index - 1;

        // get Incompatibles
        Set<Tile> incompatiblesTop = entropy.getFinalTile().incompatiblesTop;
        Set<Tile> incompatiblesRight = entropy.getFinalTile().incompatiblesRight;
        Set<Tile> incompatiblesBot = entropy.getFinalTile().incompatiblesBot;
        Set<Tile> incompatiblesLeft = entropy.getFinalTile().incompatiblesLeft;

        boolean error = false;

        Entropy entropyTop;
        if (indexTop >= 0 && indexTop < this.spots.size()) {
            entropyTop = this.spots.get(indexTop);
            error = entropyTop.isEmptyAfterReduce(incompatiblesTop) ? true : error;
        }
        Entropy entropyRight;
        if (indexRight >= 0 && indexRight < this.spots.size()) {
            entropyRight = this.spots.get(indexRight);
            error = entropyRight.isEmptyAfterReduce(incompatiblesRight) ? true : error;
        }
        Entropy entropyBot;
        if (indexBot >= 0 && indexBot < this.spots.size()) {
            entropyBot = this.spots.get(indexBot);
            error = entropyBot.isEmptyAfterReduce(incompatiblesBot) ? true : error;
        }
        Entropy entropyLeft;
        if (indexLeft >= 0 && indexLeft < this.spots.size()) {
            entropyLeft = this.spots.get(indexLeft);
            error = entropyLeft.isEmptyAfterReduce(incompatiblesLeft) ? true : error;
        }

        if (error) {
            return false;
        }

        if (indexTop >= 0 && indexTop < this.spots.size()) {
            entropyTop = this.spots.get(indexTop);
            entropyTop.reduce(incompatiblesTop);
        }
        if (indexRight >= 0 && indexRight < this.spots.size()) {
            entropyRight = this.spots.get(indexRight);
            entropyRight.reduce(incompatiblesRight);
        }
        if (indexBot >= 0 && indexBot < this.spots.size()) {
            entropyBot = this.spots.get(indexBot);
            entropyBot.reduce(incompatiblesBot);
        }
        if (indexLeft >= 0 && indexLeft < this.spots.size()) {
            entropyLeft = this.spots.get(indexLeft);
            entropyLeft.reduce(incompatiblesLeft);
        }
        return true;

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
