package engine.renderer;

import engine.utils.RendererHandlerID;

public abstract class Renderer {
    private RendererHandlerID rendererHandlerID;

    public abstract void create();
    public abstract void render();
    public abstract void clean();

    public RendererHandlerID getRendererHandlerID() {
        return rendererHandlerID;
    }

    public void setRendererHandlerID(RendererHandlerID rendererHandlerID) {
        this.rendererHandlerID = rendererHandlerID;
    }
}
