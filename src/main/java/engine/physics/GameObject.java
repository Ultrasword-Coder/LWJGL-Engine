package engine.physics;

import engine.component.Component;
import engine.component.Translation;

import java.util.ArrayList;

public class GameObject {

    private long eid;
    private ArrayList<Component> components;

    public GameObject(){
        this.components = new ArrayList<>();
        // default add translation
        addComponent(new Translation());
    }

    public void update(){
        // CUSTOM!
    }

    public <T extends Component> void addComponent(T comp){
        this.components.add(comp);
    }

    public long getEID(){
        return this.eid;
    }

    public void setEID(long eid){
        this.eid = eid;
    }

}
