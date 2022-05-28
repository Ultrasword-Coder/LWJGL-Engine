package engine.renderer;

import engine.Vertices.VertexHandler;
import engine.graphics.FrameBuffer;
import engine.graphics.Shader;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.*;

public class FrameBufferRenderer implements Renderer {
    // const vars
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


    // class vars

    private FrameBuffer frameBuffer;
    private VertexHandler vertexHandler;
    private Shader fbShader;
    private Vector4f clearColor;

    public FrameBufferRenderer(FrameBuffer frameBuffer, Shader fbShader){
        /*
            To use the FrameBufferRenderer
                1. create framebuffer
                2. create a shader for the framebuffer to use
                3. after creating framebufferrenderer object, you must getVertexHandler
                4. and add Attributes as needed
                5. then call this.create()
                6. then you can render :)
        */
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
