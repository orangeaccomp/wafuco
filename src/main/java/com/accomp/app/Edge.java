package com.accomp.app;

public class Edge {
    private int[] pixels;

    public Edge(int[] pixels) {
        this.pixels = pixels;
    }

    public boolean isEqual(Edge someEdge) {
        if (this.pixels.length != someEdge.pixels.length)
            return false;
        for (int i = 0; i < this.pixels.length; i++) {
            if (this.pixels[i] != someEdge.pixels[i]) {
                return false;
            }
        }
        return true;
    }

    public void set(int i, int pixel) {
        pixels[i] = pixel;
    }

    public int[] getPixels() {
        return pixels;
    }
}
