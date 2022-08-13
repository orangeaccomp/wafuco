package com.accomp.app;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Entropy {
    private Set<Tile> states = new HashSet<>();

    public Entropy(Collection<Tile> startTiles) {
        this.states.addAll(startTiles);
    }

    public void collaps() {
        int size = this.states.size();
        Tile[] array = this.states.toArray(new Tile[size]);
        Random random = new Random();
        int randomIndex = random.nextInt(size);
        Tile finalTile = array[randomIndex];
        this.states = new HashSet<>();
        this.states.add(finalTile);
    }

    /**
     * removes all tiles of states wich are in tiles
     */
    public void reduce(Collection<Tile> tiles) {
        if (isFinal())
            return;
        this.states.removeAll(tiles);
    }

    public boolean isEmptyAfterReduce(Collection<Tile> tiles) {
        this.states.removeAll(tiles);
        boolean isEmpty = this.states.isEmpty();
        this.states.addAll(tiles);
        return isEmpty;
    }

    public int getEntropySize() {
        return this.states.size();
    }

    public boolean isFinal() {
        return this.states.size() == 1;
    }

    public Tile getFinalTile() throws Exception {
        if (!this.isFinal()) {
            throw new TileNotFinalException(this);
        }
        return this.states.stream().findAny().get();
    }

    public BufferedImage getImageFromFinalTile() throws Exception {
        if (!this.isFinal()) {
            throw new Exception("Entropy not final, states:{" + this.states + "}");
        }
        return this.states.stream().findAny().get().getImg();
    }

    @Override
    public String toString() {
        return isFinal() ? "x" : String.valueOf(getEntropySize());
    }
}
