import Avien.camera.Camera2D;
import Avien.camera.Camera3D;
import engine.Vertices.VertexHandler;
import engine.graphics.Shader;
import engine.graphics.Texture;
import engine.handler.ShaderHandler;
import engine.handler.TextureHandler;
import engine.hardware.KeyboardListener;
import engine.hardware.Window;
import engine.utils.Attribute;
import engine.utils.Time;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Camera3DTest extends Test{

    float[] vertices = {
            0.0f, 0.0f, 0.0f,                1.0f, 1.0f,
            400.0f, 0.0f, 0.0f,              0.0f, 1.0f,
            400.0f, 400.0f, 0.0f,            0.0f, 0.0f,
            0.0f, 400.0f, 0.0f,              1.0f, 0.0f,

            200.0f, 200.0f, -100.0f,             1.0f, 1.0f,
            600.0f, 200.0f, -100.0f,            0.0f, 1.0f,
            600.0f, 600.0f, -100.0f,           0.0f, 0.0f,
            200.0f, 600.0f, -100.0f,            1.0f, 0.0f,
    };

    int[] indices = {
            3, 2, 0, 0, 2, 1,
            7, 6, 4, 4, 6, 5
    };

    static Camera3D camera;
    static VertexHandler vertexHandler;
    static Shader shader;
    static Texture texture;

    @Override
    public void init() {
        texture = TextureHandler.getTexture("assets/art.png");
        camera = new Camera3D(1280f, 720f);
        vertexHandler = new VertexHandler(vertices, indices);
        vertexHandler.addAttribArray(new Attribute(0, 3, GL_FLOAT, false, 5 * Float.BYTES, 0));
        vertexHandler.addAttribArray(new Attribute(1, 2, GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES));
        vertexHandler.create();
        shader = ShaderHandler.getShader("shaders/cam3d.glsl");

        shader.uploadInt("tex", 0);
    }

    @Override
    public void render() {
        shader.uploadFloat("uTime", Time.getTime());

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shader.bind();
        texture.bind();

        vertexHandler.render();

        texture.unbind();
        shader.unbind();
    }

    @Override
    public void clean() {
        vertexHandler.clean();
        TextureHandler.clean();
        ShaderHandler.clean();
    }

    public static void main(String[] args){
        System.out.println("Hello World!");
        Camera3DTest test = new Camera3DTest();
        Window window = Window.getInstance(Window.DEFAULTWIDTH, Window.DEFAULTHEIGHT, Window.DEFAULTTITLE);
        window.init();
        test.init();

        float SPEED = 200;

        Time.start();
        while(!window.shouldWindowClose()){
            Window.pollEvents();
//            window.clearMasks();
            camera.changeRotation(100 * Time.deltaTime, 50 * Time.deltaTime, 0, 0);

            // update
            if(KeyboardListener.isKeyPressed(GLFW_KEY_A))
                camera.move(-SPEED * Time.deltaTime, 0, 0);
            if(KeyboardListener.isKeyPressed(GLFW_KEY_D))
                camera.move(SPEED * Time.deltaTime, 0, 0);
            if(KeyboardListener.isKeyPressed(GLFW_KEY_W))
                camera.move(0, 0, -SPEED * Time.deltaTime);
            if(KeyboardListener.isKeyPressed(GLFW_KEY_S))
                camera.move( 0, 0, SPEED * Time.deltaTime);
            if(KeyboardListener.isKeyPressed(GLFW_KEY_SPACE))
                camera.move(0, SPEED * Time.deltaTime, 0);
            if(KeyboardListener.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
                camera.move(0, -SPEED * Time.deltaTime, 0);

            camera.update();
            shader.uploadMat4f("tran", camera.getViewMatrix());
            shader.uploadMat4f("proj", camera.getProjMatrix());

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
