package engine.hardware;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {

    public static final int DEFAULTWIDTH = 1280, DEFAULTHEIGHT = 720;
    public static final String DEFAULTTITLE = "Template";

    private static Window instance = null;
    private static boolean initializedLWJGL = false;

    // instance methods
    public static Window getInstance(int width, int height, String title){
        if(instance != null) return instance;
        return instance = new Window(width, height, title);
    }

    public static void sizeCallback(long window, int w, int h){
        // what you want to do is change the size
        Window display = getInstance(Window.DEFAULTWIDTH, Window.DEFAULTHEIGHT, Window.DEFAULTTITLE);
        display.setWidth(w); display.setHeight(h);
        glViewport(0, 0, w, h);
    }

    public static void pollEvents(){
        glfwPollEvents();
    }

    // class params
    private float baseWidth, baseHeight, width, height;
    private String title;

    private long glfwWindow;
    private int clearMask = GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT;

    // constructor
    private Window(float w, float h, String t){
        if (t == null) t = "LWJGL Window";
        this.baseWidth = w;
        this.baseHeight = h;
        this.width = w;
        this.height = h;
        this.title = t;
    }

    // initialize window and glfw
    public void init(){
        // output version
        System.out.println("Starting LWJGL with version: " + Version.getVersion() + "!");
        // set error printing method
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW!");

        // configure glfw
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        // create window
        glfwWindow = glfwCreateWindow((int)instance.width, (int)instance.height, instance.title, 0, 0);
        if(glfwWindow == 0)
            throw new IllegalStateException("Failed to create GLFW Window!");

        // set the callbacks for engine.hardware
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyboardListener::keyCallback);

        // scuffed aspect ratio stuff
        // glfwSetWindowAspectRatio(glfwWindow, 16, 9);
        glfwSetWindowSizeCallback(glfwWindow, Window::sizeCallback);

        // make opengl context current
        glfwMakeContextCurrent(glfwWindow);
        // vsync
        glfwSwapInterval(1);

        // final steps
        glfwShowWindow(glfwWindow);
        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
    }

    // customize
    public void setClearMask(int mask){
        clearMask = mask;
    }

    public void clearMasks(){
        glClear(clearMask);
    }

    public void swapGLFWBuffers(){
        glfwSwapBuffers(getInstance(Window.DEFAULTWIDTH, Window.DEFAULTHEIGHT, Window.DEFAULTTITLE).glfwWindow);
    }

    public void setClearColor(float r, float g, float b, float a){
        glClearColor(r, g, b, a);
    }

    public boolean shouldWindowClose(){
        return glfwWindowShouldClose(glfwWindow);
    }


    // get information
    public float getBaseWidth() {
        return baseWidth;
    }

    public float getBaseHeight() {
        return baseHeight;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public float getWidthRatio(){
        return this.width / this.baseWidth;
    }

    public float getHeightRatio(){
        return this.height / this.baseHeight;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void close(){
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
    }

    public static void end(){
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
