package engine.renderer;

import engine.Vertices.VertexHandler;
import engine.physics.GameObject;

import java.util.ArrayList;

public class SquareBatchRenderer implements Renderer{
    public static final int DEFAULT_MAX_OBJECTS = 256;
    public static final int FLOATBYTES = Float.BYTES;

    private int maxObjects;

    private float vertices[];
    private int indices[];
    private ArrayList<GameObject> entitites;
    private VertexHandler vertexHandler;

    public SquareBatchRenderer(int maxObjects){
        /*
            All vertices will be in float format
                - developers should call the getVertexHandler.addAttribArray(new Attribute());
                - to add vertex attributes :)
         */
        this.maxObjects = maxObjects;
        this.entitites = new ArrayList<>();
    }

    @Override
    public void create() {
        vertices = new float[this.maxObjects * 4 * this.vertexHandler.getVertexSize()];
        indices = new int[this.maxObjects * 6];
        generateIndices();
        vertexHandler.create();
    }

    @Override
    public void render() {

    }

    @Override
    public void clean() {
        vertexHandler.clean();
    }

    private void generateIndices(){
        int start, vertexSize = vertexHandler.getVertexSize(), count = 0;
        for(int i = 0; i < this.maxObjects; i++){
            start = i * 6;
            indices[start + 0] = count + 3;
            indices[start + 1] = count + 2;
            indices[start + 3] = count;
            indices[start + 4] = count;
            indices[start + 5] = count + 2;
            indices[start + 6] = count + 1;
            count += 4;
        }
    }

    public <T extends GameObject> void addEntity(T object){
        // TODO - finish this part
    }

    public VertexHandler getVertexHandler(){
        return this.vertexHandler;
    }
}
