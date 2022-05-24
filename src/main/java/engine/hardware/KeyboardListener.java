package engine.hardware;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyboardListener {
    private static KeyboardListener instance;

    public static KeyboardListener getInstance(){
        if(instance == null)
            instance = new KeyboardListener();
        return instance;
    }

    public static boolean isKeyPressed(int keyCode){
        return getInstance().keyPressed[keyCode];
    }

    public static boolean isKeyClicked(int keyCode){
        return getInstance().clicked.contains(keyCode);
    }

    public static void update(){
        getInstance().clicked.clear();
    }


    // params
    private boolean keyPressed[]; // 600 cuz why not
    private Set<Integer> clicked;

    // constructor
    private KeyboardListener(){
        keyPressed = new boolean[600];
        clicked = new HashSet<>();
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods){
        if(action == GLFW_PRESS) {
            getInstance().keyPressed[key] = true;
            getInstance().clicked.add(key);
        }
        else if(action == GLFW_RELEASE) {
            getInstance().keyPressed[key] = false;
        }
    }

}
