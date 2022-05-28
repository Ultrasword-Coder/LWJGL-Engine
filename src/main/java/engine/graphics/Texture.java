package engine.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;


public class Texture {

    private String path;
    private int texID, width, height;

    public Texture(String path){
        this.path = path;
    }

    public void create(){
        // create texture on gpu
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        // Set texture parameters
        // Repeat image in both directions
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        // When stretching the image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        // When shrinking an image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = STBImage.stbi_load(path, width, height, channels, 0);
        this.width = width.get(0);
        this.height = height.get(0);

        if (image != null) {
            if (channels.get(0) == 3) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0),
                        0, GL_RGB, GL_UNSIGNED_BYTE, image);
            } else if (channels.get(0) == 4) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),
                        0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            } else {
                System.err.format("[Texture | create] Unknown number of channels!\n");
                assert false : "Error: (Texture) Unknown number of channels '" + channels.get(0) + "'";
            }
        } else {
            System.err.format("[Texture | create] Unable to load image!\n");
            assert false : "Error: (Texture) Could not load image '" + path + "'";
        }
        STBImage.stbi_image_free(image);
    }

    public void clean(){
        glDeleteTextures(texID);
    }

    public void bind(){
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    public void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getTexID(){
        return texID;
    }

    public String getPath(){
        return this.path;
    }

    public boolean equals(Texture other){
        return this.texID == other.getTexID();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
