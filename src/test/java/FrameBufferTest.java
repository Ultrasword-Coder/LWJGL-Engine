import Avien.camera.Camera2D;
import engine.utils.Attribute;
import engine.Vertices.VertexHandler;
import engine.graphics.Shader;
import engine.hardware.KeyboardListener;
import engine.hardware.Window;
import engine.renderer.FrameBufferRenderer;
import engine.utils.Time;
import org.joml.Vector2f;
import org.joml.Vector3f;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.opengl.GL30.*;

public class FrameBufferTest extends Test{

    static float vertices[] = {
            // bottom left, bottom right, top right, topleft
            0.0f, 0.0f, 0.0f,               1.0f, 0.0f, 0.0f, 1.0f,
            250.0f, 0.0f, 0.0f,             0.0f, 1.0f, 0.0f, 1.0f,
            250.0f, 250.0f, 0.0f,           0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 250.0f, 0.0f,             1.0f, 1.0f, 0.0f, 1.0f
    };

    static float vertices2[] = {
            // bottom left, bottom right, top right, topleft
            0.0f, 0.0f, 0.0f,               1.0f, 0.0f, 0.0f, 1.0f,
            300.0f, 0.0f, 0.0f,             0.0f, 1.0f, 0.0f, 1.0f,
            300.0f, 300.0f, 0.0f,           0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 300.0f, 0.0f,             1.0f, 1.0f, 0.0f, 1.0f
    };

    static int indices[] = {
            3, 2, 0,
            0, 2, 1
    };

    static Shader shader, fbShader;
    static engine.graphics.FrameBuffer frameBuffer;
    static VertexHandler vertexHandler;
    static FrameBufferRenderer fbr;

    public void init(){
        shader = new Shader("shaders/vertex.glsl", "shaders/fragment.glsl");
        shader.create();
        shader.uploadVec3f("uRes", new Vector3f(250.0f, 250.0f, 0.0f));

        frameBuffer = new engine.graphics.FrameBuffer(1280, 720);
        frameBuffer.create();

        fbShader = new Shader("shaders/fbvertex.glsl", "shaders/fbfragment.glsl");
        fbShader.create();
        fbShader.uploadInt("fbTex", 0);
        // optional
        fbShader.uploadVec2f("pixels", new Vector2f(160f/2, 90f/2));

        vertexHandler = new VertexHandler(vertices2, indices);
        vertexHandler.addAttribArray(new Attribute(0, 3, GL_FLOAT, false, 7 * Float.BYTES, 0));
        vertexHandler.addAttribArray(new Attribute(1, 4, GL_FLOAT, false, 7 * Float.BYTES, 3 * Float.BYTES));
        vertexHandler.create();

        fbr = new FrameBufferRenderer(frameBuffer, fbShader);
        fbr.getVertexHandler().addAttribArray(new Attribute(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0));
        fbr.getVertexHandler().addAttribArray(new Attribute(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES));
        fbr.create();

    }

    public void render(){
        // draw to framebuffer
        shader.uploadFloat("uTime", Time.getTime());
        fbShader.uploadFloat("uTime", Time.getTime());

        frameBuffer.bind();
        glEnable(GL_DEPTH_TEST);
        // clear framebuffer contents
        glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        // draw scene
        shader.bind();
        vertexHandler.render();
        shader.unbind();
        frameBuffer.unbind();

        // render framebuffer
        fbr.render();

    }

    public void clean(){
        shader.clean();
        fbShader.clean();
        vertexHandler.clean();
        fbr.clean();
    }


    public static void main(String[] args){
        System.out.println("Hello World!");

        FrameBufferTest test = new FrameBufferTest();

        Window window = Window.getInstance(Window.DEFAULTWIDTH, Window.DEFAULTHEIGHT, Window.DEFAULTTITLE);
        window.init();

        // TEST AREA
        Camera2D camera2D = new Camera2D(Window.DEFAULTWIDTH, Window.DEFAULTHEIGHT);
        float SPEED = 200;

        test.init();
        Time.start();
        while(!window.shouldWindowClose()){
            Window.pollEvents();
//            window.clearMasks();

            // update
            if(KeyboardListener.isKeyPressed(GLFW_KEY_A))
                camera2D.move(-SPEED * Time.deltaTime, 0, 0);
            if(KeyboardListener.isKeyPressed(GLFW_KEY_D))
                camera2D.move(SPEED * Time.deltaTime, 0, 0);
            if(KeyboardListener.isKeyPressed(GLFW_KEY_W))
                camera2D.move(0, SPEED * Time.deltaTime, 0);
            if(KeyboardListener.isKeyPressed(GLFW_KEY_S))
                camera2D.move( 0, -SPEED * Time.deltaTime, 0);
            camera2D.update();
            shader.uploadMat4f("proj", camera2D.getProjMatrix());
            shader.uploadMat4f("view", camera2D.getViewMatrix());

            // render
            test.render();

            window.swapGLFWBuffers();
            Time.update();
        }

        // TESTING
        test.clean();

        window.close();
        Window.end();
        System.out.println("Closing Application!");

    }
}
