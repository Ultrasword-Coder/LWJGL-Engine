package engine.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Camera {
    protected float width, height;
    private float pixelSize = 1.0f;         // the size of each pixel (bigger means more pixelated)

    protected Vector3f position;
    protected Vector3f up;
    protected Matrix4f viewMatrix;
    protected Matrix4f projMatrix;

    private boolean changed;

    public Camera(float width, float height){
        this.width = width;
        this.height = height;
        this.position = new Vector3f();
        this.up = new Vector3f(0.0f, 1.0f, 0.0f);               // set default up
        this.viewMatrix = new Matrix4f();
        this.projMatrix = new Matrix4f();
        changed = true;
    }

    public Vector3f getPosition(){
        return this.position;
    }

    public void setPosition(Vector3f position){
        this.position.x = position.x;
        this.position.y = position.y;
        this.position.z = position.z;
    }

    public void setPosition(float x, float y, float z){
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
        changed = true;
    }

    public Vector3f getUp(){
        return this.up;
    }

    public void setUp(Vector3f up){
        this.up.x = up.x;
        this.up.y = up.y;
        this.up.z = up.z;
    }

    public void setUp(float x, float y, float z){
        this.up.x = x;
        this.up.y = y;
        this.up.z = z;
    }

    public void setViewArea(float width, float height){
        this.width = width;
        this.height = height;
        changed = true;
    }

    public void setPixelSize(int pixelSize){
        this.pixelSize = 1.f / (float)pixelSize;
        changed = true;
    }

    public float getPixelSize(){
        return this.pixelSize;
    }

    public boolean isChanged(){
        return this.changed;
    }

    public void notChanged(){
        this.changed = false;
    }

    public Matrix4f getViewMatrix(){
        return this.viewMatrix;
    }

    public Matrix4f getProjMatrix(){
        return this.projMatrix;
    }

    public abstract void update();

}
