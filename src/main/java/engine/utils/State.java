package engine.utils;

import engine.handler.Handler;
import engine.handler.RendererHandler;

public abstract class State {

    public Handler handler;
    public RendererHandler rendererHandler;

    public State(){
        handler = new Handler();
        rendererHandler = new RendererHandler();
    }

    public abstract void update();
    public abstract void render();

    public void clean(){
        rendererHandler.clean();
    }

}
