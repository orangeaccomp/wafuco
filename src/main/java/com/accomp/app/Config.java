package com.accomp.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Config {
    TileInformationList tileInformationList;

    public Config(String path) throws IOException {
        loadConfig(path);
    }

    class TileInformationList {
        Meta meta;
        ArrayList<TileInformation> list;
    }

    class Meta {
        int width;
        int height;
    }

    class TileInformation {
        boolean top;
        boolean right;
        boolean bot;
        boolean left;
        String rotation;
        boolean mirror;
    }

    private void loadConfig(String path) throws IOException {
        Path fileName = Paths.get(path);
        String content = Files.readString(fileName);

        Gson gson = new Gson();
        this.tileInformationList = gson.fromJson(content, TileInformationList.class);
    }

    public TileInformation get(int index) {
        return this.tileInformationList.list.get(index);
    }
}
