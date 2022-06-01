package engine.component;

import engine.graphics.Texture;

public class Sprite implements Component{

    private Texture texture;

    public Sprite(Texture texture){
        this.texture = texture;
    }

    public Sprite(String path){
        this(new Texture(path));
    }

    @Override
    public void update() {}

    public Texture getTexture() {
        return texture;
    }
}
