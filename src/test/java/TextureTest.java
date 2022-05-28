import Avien.camera.Camera2D;
import engine.utils.Attribute;
import engine.Vertices.VertexHandler;
import engine.graphics.Shader;
import engine.graphics.Texture;
import engine.hardware.KeyboardListener;
import engine.hardware.Window;
import engine.utils.Time;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class TextureTest extends Test{

    float[] vertices = {
            0.0f, 0.0f,                 1.0f, 1.0f,
            500.0f, 0.0f,               0.0f, 1.0f,
            500.0f, 500.0f,             0.0f, 0.0f,
            0.0f, 500.0f,               1.0f, 0.0f,

            200.0f, 200.0f,                 1.0f, 1.0f,
            600.0f, 200.0f,                 0.0f, 1.0f,
            600.0f, 600.0f,                 0.0f, 0.0f,
            200.0f, 600.0f,                 1.0f, 0.0f,
    };

    static int indices[] = {
            3, 2, 0, 0, 2, 1,
            7, 6, 4, 4, 6, 5,
    };

    static Texture image;
    static VertexHandler vertexHandler;
    static Shader texShader;

    @Override
    public void init() {
        texShader = new Shader("shaders/texvertex.glsl", "shaders/texfragment.glsl");
        texShader.create();
        image = new Texture("assets/art.jpg");
        image.create();
        vertexHandler = new VertexHandler(vertices, indices);
        vertexHandler.addAttribArray(new Attribute(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0));
        vertexHandler.addAttribArray(new Attribute(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES));
        vertexHandler.create();

        texShader.uploadInt("tex", 0);
    }

    @Override
    public void render() {
        texShader.uploadFloat("uTime", Time.getTime());

        glClearColor(0.0f, 0.0f, .0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        texShader.bind();
        image.bind();
        vertexHandler.render();
        image.unbind();
        texShader.unbind();
    }

    @Override
    public void clean() {
        image.clean();
        texShader.clean();
        vertexHandler.clean();
    }

    public static void main(String[] args){
        System.out.println("Hello World!");
        TextureTest test = new TextureTest();
        Window window = Window.getInstance(Window.DEFAULTWIDTH, Window.DEFAULTHEIGHT, Window.DEFAULTTITLE);
        window.init();
        test.init();

        Camera2D camera = new Camera2D(1280f, 720f);

        float SPEED = 200;

        Time.start();
        while(!window.shouldWindowClose()){
            Window.pollEvents();
//            window.clearMasks();

            // update
            if(KeyboardListener.isKeyPressed(GLFW_KEY_A))
                camera.move(-SPEED * Time.deltaTime, 0, 0);
            if(KeyboardListener.isKeyPressed(GLFW_KEY_D))
                camera.move(SPEED * Time.deltaTime, 0, 0);
            if(KeyboardListener.isKeyPressed(GLFW_KEY_W))
                camera.move(0, SPEED * Time.deltaTime, 0);
            if(KeyboardListener.isKeyPressed(GLFW_KEY_S))
                camera.move( 0, -SPEED * Time.deltaTime, 0);

            camera.update();
            texShader.uploadMat4f("view", camera.getViewMatrix());
            texShader.uploadMat4f("proj", camera.getProjMatrix());

            // render
            test.render();

            window.swapGLFWBuffers();
            Time.update();
        }
        test.clean();
        window.close();
        Window.end();
        System.out.println("Closing Application!");
    }
}
