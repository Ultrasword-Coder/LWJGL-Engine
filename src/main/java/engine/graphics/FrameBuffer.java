package engine.graphics;

import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {
    private int width, height;
    private int frameBufferObject, frameBufferTex, renderBufferDepth;

    public FrameBuffer(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void create(){
        frameBufferObject = glGenFramebuffers();
        bind();
        // create framebuffer texture
        frameBufferTex = glGenTextures();
        bindTexture();
        // set image area + rgba
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
        // set params
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, frameBufferTex, 0);
        // create render buffer object
        renderBufferDepth = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, renderBufferDepth);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height); //  single renderbuffer object for | depth + stencil
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, renderBufferDepth); // actually attack
        // check for errors
        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE){
            // error
            System.err.format("[FrameBuffer | create] Failed to link the framebuffer renderer and texture\n");
            assert false : "Framebuffer could not be created";
        }
        // if success - unbind buffer
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        unbindTexture();
        unbind();
    }

    public void setFrameSize(int width, int height){
        this.width = width;
        this.height = height;
        updateSize();
    }

    public void updateSize(){
        // rescale the framebuffer object + render buffer object
        // resize texture
        glBindTexture(GL_TEXTURE_2D, frameBufferTex);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
        // unbind
        glBindTexture(GL_TEXTURE_2D, 0);

        // resize render buffer
        glBindRenderbuffer(GL_RENDERBUFFER, renderBufferDepth);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT16, width, height);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
    }

    public void clean(){
        glDeleteRenderbuffers(renderBufferDepth);
        glDeleteTextures(frameBufferTex);
        glDeleteFramebuffers(frameBufferObject);
    }

    public void bind(){
        glBindFramebuffer(GL_FRAMEBUFFER, frameBufferObject);
    }

    public void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public int getFrameBufferTexID(){
        return this.frameBufferTex;
    }

    public void bindTexture(){
        glBindTexture(GL_TEXTURE_2D, frameBufferTex);
    }

    public void unbindTexture(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

}
