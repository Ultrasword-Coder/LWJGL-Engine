package engine.Vertices;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;

public class VertexHandler {
    // static vars
    public static final int FLOATBYTES = Float.BYTES;
    public static final int DEFAULTMAXVERTICES = 256;

    private static final int DEFAULTINDICES[] = {3, 2, 0, 0, 2, 1};


    // class vars
    private int vao, vbo, ebo;
    private float vertices[];
    private int indices[];

    private int attribs;
    private ArrayList<Attribute> vertexAttribs;

    public VertexHandler(float[] vertices, int[] indices){
        // take and store data mainly
        this.vertices = vertices;
        this.indices = indices;
        this.attribs = 0;
        vertexAttribs = new ArrayList<>();
    }

    public void create(){
        // add vertex attribs before creating
        vao = glGenVertexArrays();
        bind();
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_STATIC_DRAW);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        for(int i = 0; i < attribs; i++) {
            Attribute attrib = vertexAttribs.get(i);
            attrib.enableAttrib();
            glVertexAttribPointer(attrib.getIndex(), attrib.getSize(), attrib.getType(), attrib.isNormalized(), attrib.getStride(), attrib.getPointer());
            attrib.disableAttrib();
        }
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        unbind();
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
        for(int i = 0; i < attribs; i++)
            vertexAttribs.get(i).enableAttrib();
    }

    public void disableVertexAttribs(){
        for(int i = 0; i < attribs; i++)
            vertexAttribs.get(i).disableAttrib();
    }

}
