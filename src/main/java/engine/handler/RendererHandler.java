package engine.handler;

import engine.renderer.Renderer;
import engine.utils.RendererHandlerID;

import java.util.ArrayList;

public class RendererHandler {

    public ArrayList<Renderer> renderers;
    private RendererHandlerID rendererHandlerID;

    public RendererHandler(){
        rendererHandlerID = new RendererHandlerID(this);
        this.renderers = new ArrayList<>();
    }

    public void render(){
        for(int i = 0; i < renderers.size(); i++) {
            renderers.get(i).render();
        }
    }

    public <T extends Renderer> void addRenderer(T renderer){
        this.renderers.add(renderer);
        renderer.setRendererHandlerID(rendererHandlerID);
    }

    public void clean(){
        for(int i = 0; i < renderers.size(); i++)
            renderers.get(i).clean();
    }

}
