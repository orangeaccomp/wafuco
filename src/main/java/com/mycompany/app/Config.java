package com.mycompany.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Config {
    Holder holder;

    public Config(String path) throws IOException {
        loadConfig(path);
    }

    class Holder {
        ArrayList<TileInformation> list;
    }

    class TileInformation {
        boolean top;
        boolean right;
        boolean bot;
        boolean left;
        String rotation;
    }

    private void loadConfig(String path) throws IOException {
        Path fileName = Paths.get(path);
        String content = Files.readString(fileName);

        Gson gson = new Gson();
        this.holder = gson.fromJson(content, Holder.class);  
    }

    public TileInformation get(int index){
        return this.holder.list.get(index);
    }
}
