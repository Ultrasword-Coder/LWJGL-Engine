package engine.utils;

import static org.lwjgl.opengl.GL20.*;

public class Attribute {

    private int index, size, type, stride, pointer;
    private boolean normalized;

    public Attribute(int index, int size, int type, boolean normalized, int stride, int pointer){
        this.index = index;
        this.size = size;
        this.type = type;
        this.normalized = normalized;
        this.stride = stride;
        this.pointer = pointer;
    }

    public int getIndex(){
        return this.index;
    }

    public int getSize(){
        return this.size;
    }

    public int getType() {
        return type;
    }

    public int getStride() {
        return stride;
    }

    public int getPointer() {
        return pointer;
    }

    public boolean isNormalized() {
        return normalized;
    }

    public void enableAttrib(){
        glEnableVertexAttribArray(index);
    }

    public void disableAttrib(){
        glDisableVertexAttribArray(index);
    }

    public String toString(){
        return String.format("Index: %d | Size(bytes): %d | Type: %d | Normalized: %s | Stride(bytes): %d | Pointer(bytes): %d", index, size, type, String.valueOf(normalized), stride, pointer);
    }

}
