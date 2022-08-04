package com.accomp.app;

public class TileNotFinalException extends Exception {
    Entropy entropy;

    public TileNotFinalException(Entropy entropy) {
        this.entropy = entropy;
    }

    public void printStackTrace() {

    }

}
