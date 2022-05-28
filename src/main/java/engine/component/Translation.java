package engine.component;

import org.joml.Vector3f;

public class Translation implements Component{

    private float x, y, z;

    public Translation(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Translation(Vector3f translation){
        this(translation.x, translation.y, translation.z);
    }

    public Translation(){
        this(0, 0, 0);
    }

    public Vector3f getPosition(){
        return new Vector3f(x, y, z);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public boolean equals(Translation other){
        return ((this.x == other.x) && (this.y == other.y) && (this.z == other.z));
    }

    @Override
    public void update() {

    }
}
