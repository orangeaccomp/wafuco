package com.mycompany.app;

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
        if (isFinal()) {
            return;
        }
        int size = this.states.size();
        Tile[] array = this.states.toArray(new Tile[size]);
        Random random = new Random();
        int randomIndex = random.nextInt(size);
        Tile finalTile = array[randomIndex];
        this.states = new HashSet<>();
        this.states.add(finalTile);
    }

    /**
     * removes all tiles of states wich @param tile hates
     */
    public void reduce(Collection<Tile> tiles) {
        if (isFinal())
            return;
        this.states.removeAll(tiles);
    }

    public int getEntropySize() {
        return this.states.size();
    }

    public boolean isFinal() {
        return this.states.size() == 1;
    }

    public Tile getTile() throws Exception {
        if (!this.isFinal()) {
            throw new Exception("Entropy not final, states:{" + this.states + "}");
        }
        return this.states.stream().findAny().get();
    }

    public BufferedImage getImage() throws Exception {
        if (!this.isFinal()) {
            throw new Exception("Entropy not final, states:{" + this.states + "}");
        }
        return this.states.stream().findAny().get().img;
    }

    @Override
    public String toString() {       
        return isFinal() ? "x" : String.valueOf(getEntropySize());
    }
}
