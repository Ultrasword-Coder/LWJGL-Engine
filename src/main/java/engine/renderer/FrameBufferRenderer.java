package engine.renderer;

import engine.Vertices.VertexHandler;
import engine.graphics.FrameBuffer;
import engine.graphics.Shader;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.*;

public class FrameBufferRenderer {
    public static final float DEFAULTVERTICES[] = {
            // positions   // texCoords
            -1.0f,  1.0f,  0.0f, 1.0f,
            -1.0f, -1.0f,  0.0f, 0.0f,
            1.0f, -1.0f,  1.0f, 0.0f,

            -1.0f,  1.0f,  0.0f, 1.0f,
            1.0f, -1.0f,  1.0f, 0.0f,
            1.0f,  1.0f,  1.0f, 1.0f
    };

    public static final int DEFAULTINDICES[] = {
        3, 2, 0,
        0, 2, 1
    };


    private FrameBuffer frameBuffer;
    private VertexHandler vertexHandler;
    private Shader fbShader;
    private Vector4f clearColor;

    public FrameBufferRenderer(FrameBuffer frameBuffer, Shader fbShader){
        this.frameBuffer = frameBuffer;
        this.fbShader = fbShader;
        this.vertexHandler = new VertexHandler(DEFAULTVERTICES, DEFAULTINDICES);
        clearColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void create(){
        this.vertexHandler.create();
    }

    public void render(){
        glDisable(GL_DEPTH_TEST);
        // draw framebuffer
        glClearColor(clearColor.x, clearColor.y, clearColor.z, clearColor.w);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        fbShader.bind();
        frameBuffer.bindTexture();
        vertexHandler.bind();
        vertexHandler.enableVertexAttribs();
        glDrawArrays(GL_TRIANGLES, 0, 6);
        vertexHandler.disableVertexAttribs();
        vertexHandler.unbind();
        frameBuffer.unbindTexture();
        fbShader.unbind();
    }

    public void clean(){
        frameBuffer.clean();
        vertexHandler.clean();
    }

    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    public VertexHandler getVertexHandler() {
        return vertexHandler;
    }

    public Shader getFbShader() {
        return fbShader;
    }

    public Vector4f getClearColor() {
        return clearColor;
    }

    public void setFbShader(Shader fbShader) {
        this.fbShader = fbShader;
    }

    public void setClearColor(Vector4f clearColor) {
        this.clearColor = clearColor;
    }
}
