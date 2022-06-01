package engine.handler;

import engine.graphics.Texture;

import java.util.HashMap;

public class TextureHandler {

    private static HashMap<String, Texture> textures = new HashMap<>();

    public static Texture getTexture(String path){
        Texture texture;
        if (textures.containsKey(path))
            return textures.get(path);
        texture = new Texture(path);
        texture.create();
        textures.put(path, texture);
        return texture;
    }

    public static void clean(){
        for(String path : textures.keySet())
            textures.get(path).clean();
        textures.clear();
    }

}
