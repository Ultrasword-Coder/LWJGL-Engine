package engine.hardware;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance = null;

    public static MouseListener getInstance(){
        if(instance == null)
            instance = new MouseListener();
        return instance;
    }

    public static void mousePosCallback(long window, double xpos, double ypos){
        MouseListener inst = getInstance();
        inst.lastX = inst.xPos; inst.lastY = inst.yPos;
        inst.xPos = xpos; inst.yPos = ypos;
        inst.isDragging = inst.mouseButtonPressed[0] || inst.mouseButtonPressed[1] || inst.mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods){
        if(action == GLFW_PRESS){
            if(button < getInstance().mouseButtonPressed.length){
                getInstance().mouseButtonPressed[button] = true;
                getInstance().clicked.add(button);
            }
        }else if(action == GLFW_RELEASE){
            if(button < getInstance().mouseButtonPressed.length){
                getInstance().mouseButtonPressed[button] = false;
                getInstance().isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOff, double yOff){
        getInstance().scrollX = xOff;
        getInstance().scrollY = yOff;
    }

    public static void startInput(){
        update(); // sets movement stuff to 0
        update();
    }

    public static void update(){
        MouseListener inst = getInstance();
        inst.moveX = inst.xPos - inst.lastX;
        inst.moveY = inst.yPos - inst.lastY;
        inst.scrollX = 0.0;
        inst.scrollY = 0.0;
        inst.lastX = inst.xPos;
        inst.lastY = inst.yPos;
    }

    // get information
    public static boolean isButtonDown(int button){
        if(button < getInstance().mouseButtonPressed.length)
            return getInstance().mouseButtonPressed[button];
        return false;
    }

    public static float getRawMouseX(){
        return (float) getInstance().xPos;
    }

    public static float getRawMouseY(){
        return (float) getInstance().yPos;
    }

    public static float getMouseX(){
        return (float)((float) getInstance().xPos) * Window.getInstance(Window.DEFAULTWIDTH, Window.DEFAULTHEIGHT, Window.DEFAULTTITLE).getWidthRatio();
    }

    public static float getMouseY(){
        return (float)((float) getInstance().yPos) * Window.getInstance(Window.DEFAULTWIDTH, Window.DEFAULTHEIGHT, Window.DEFAULTTITLE).getHeightRatio();
    }

    public static float getMoveX(){
        return (float)(getInstance().lastX - getInstance().xPos);
    }

    public static float getMoveY(){
        return (float)(getInstance().lastY - getInstance().yPos);
    }

    public static float getScrollX(){
        return (float)getInstance().scrollX;
    }

    public static float getScrollY(){
        return (float)getInstance().scrollY;
    }

    public static boolean isDragging(){
        return getInstance().isDragging;
    }


    // params
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY, moveX, moveY;
    private boolean mouseButtonPressed[];
    private boolean isDragging;
    private Set<Integer> clicked;

    // constructor
    private MouseListener() {
        scrollX = 0.0; scrollY = 0.0;
        xPos = 0.0; yPos = 0.0;
        lastX = 0.0; lastY = 0.0;
        moveX = 0.0; moveY = 0.0;
        mouseButtonPressed = new boolean[10];       // 10 just in case, only needs 3
        clicked = new HashSet<>();                  // why not :D
        isDragging = false;
    }

}
