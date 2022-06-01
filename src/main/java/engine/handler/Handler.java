package engine.handler;

import engine.physics.GameObject;

import java.util.HashMap;

public class Handler {

    private long entityCount = 0;
    private HashMap<Long, GameObject> entities;

    public Handler(){
        this.entities = new HashMap<>();
    }

    public <T extends GameObject> void addEntity(T gameObject){
        gameObject.setEID(entityCount++);
        entities.put(gameObject.getEID(), gameObject);
    }

    public void removeEntity(long id){
        entities.remove(id);
    }

    public void update(){
        for(long i : entities.keySet()){
            entities.get(i).update();
        }
    }
}
