package engine.Vertices;

import engine.utils.Attribute;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;

public class VertexHandler {
    // static vars
    public static final int FLOATBYTES = Float.BYTES;
    public static final int DEFAULTMAXVERTICES = 256;

    public static final int DEFAULTINDICES[] = {3, 2, 0, 0, 2, 1};

    // class vars
    private int vao, vbo, ebo;
    private float vertices[];
    private int indices[];
    private int drawType;
    private int vertexSize;

    private int attribs;
    private ArrayList<Attribute> vertexAttribs;

    public VertexHandler(float[] vertices, int[] indices){
        // take and store data mainly
        this.vertices = vertices;
        this.indices = indices;
        this.attribs = 0;
        this.drawType = GL_STATIC_DRAW;
        this.vertexSize = 0;
        vertexAttribs = new ArrayList<>();
    }

    public void create(){
//        for(Attribute attrib : vertexAttribs)
//            System.out.println(attrib.toString());
        // add vertex attribs before creating
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, this.drawType);
        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, this.drawType);
        // enable attributes
        for(Attribute attrib : vertexAttribs) {
            glEnableVertexAttribArray(attrib.getIndex());
            glVertexAttribPointer(attrib.getIndex(), attrib.getSize(), attrib.getType(), attrib.isNormalized(), attrib.getStride(), attrib.getPointer());
        }
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        // disable attributes
        for(Attribute attrib : this.vertexAttribs)
            glDisableVertexAttribArray(attrib.getIndex());
        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void render(){
        bind();
        enableVertexAttribs();
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        disableVertexAttribs();
        unbind();
    }

    public void clean(){
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
    }

    public void addAttribArray(Attribute attribute){
        this.vertexAttribs.add(attribute);
        this.attribs++;
        this.vertexSize += attribute.getSize();
    }

    public int getIndexCount(){
        return indices.length;
    }

    public int getAttribCount(){
        return this.attribs;
    }

    public void bind(){
        glBindVertexArray(vao);
    }

    public void unbind(){
        glBindVertexArray(0);
    }

    public void enableVertexAttribs(){
        for(Attribute attrib : vertexAttribs)
            attrib.enableAttrib();
    }

    public void disableVertexAttribs(){
        for(Attribute attrib : vertexAttribs)
            attrib.disableAttrib();
    }

    public void setDrawType(int drawType){
        this.drawType = drawType;
    }

    public int getVertexSizeBytes(){
        return this.vertexSize * Float.BYTES;
    }

    public int getVertexSize(){
        return this.vertexSize;
    }

}
