import Avien.camera.Camera2D;
import engine.Vertices.Attribute;
import engine.graphics.FrameBuffer;
import engine.graphics.Shader;
import engine.hardware.Window;
import engine.renderer.FrameBufferRenderer;
import engine.utils.Time;
import org.joml.Vector2f;
import org.joml.Vector3f;


import static org.lwjgl.opengl.GL30.*;

public class Game {

    static float vertices[] = {
            // bottom left, bottom right, top right, topleft
            0.0f, 0.0f, 0.0f,               1.0f, 0.0f, 0.0f, 1.0f,
            120.0f, 0.0f, 0.0f,             0.0f, 1.0f, 0.0f, 1.0f,
            120.0f, 120.0f, 0.0f,           0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 120.0f, 0.0f,             1.0f, 1.0f, 0.0f, 1.0f
    };

    static int indices[] = {
            3, 2, 0,
            0, 2, 1
    };

    static float fbVertices[] = {
            // positions   // texCoords
            -1.0f,  1.0f,  0.0f, 1.0f,
            -1.0f, -1.0f,  0.0f, 0.0f,
            1.0f, -1.0f,  1.0f, 0.0f,

            -1.0f,  1.0f,  0.0f, 1.0f,
            1.0f, -1.0f,  1.0f, 0.0f,
            1.0f,  1.0f,  1.0f, 1.0f
    };

    static Shader shader, fbShader;
    static FrameBuffer frameBuffer;
    static int vao, vbo, ebo;
    static FrameBufferRenderer fbr;

    public static void init(){
        shader = new Shader("vertex.glsl", "fragment.glsl");
        shader.create();
        shader.uploadVec3f("uRes", new Vector3f(120.0f, 120.0f, 0.0f));

        frameBuffer = new FrameBuffer(1280, 720);
        frameBuffer.create();

        fbShader = new Shader("fbvertex.glsl", "fbfragment.glsl");
        fbShader.create();
        fbShader.uploadInt("fbTex", 0);

        // enable vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_STATIC_DRAW);
        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        // attrib stuffs
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 7 * Float.BYTES, 0);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 7 * Float.BYTES, 3 * Float.BYTES);
        // actually upload data
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        // unbind
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        fbr = new FrameBufferRenderer(frameBuffer, fbShader);
        fbr.getVertexHandler().addAttribArray(new Attribute(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0));
        fbr.getVertexHandler().addAttribArray(new Attribute(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES));
        fbr.create();


        // create framebuffer stuff | only 2 float per vertex b/c vshader can calculate tex coords
//        fbvao = glGenVertexArrays();
//        glBindVertexArray(fbvao);
//        fbvbo = glGenBuffers();
//        glBindBuffer(GL_ARRAY_BUFFER, fbvbo);
//        glBufferData(GL_ARRAY_BUFFER, fbVertices.length * Float.BYTES, GL_STATIC_DRAW);
//        glBufferSubData(GL_ARRAY_BUFFER, 0, fbVertices);
//        fbebo = glGenBuffers();
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, fbebo);
//        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
//        glEnableVertexAttribArray(0);
//        glEnableVertexAttribArray(1);
//        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0);
//        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
//        // unbind
//        glDisableVertexAttribArray(0);
//        glDisableVertexAttribArray(1);
//        glBindBuffer(GL_ARRAY_BUFFER, 0);
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
//        glBindVertexArray(0);


    }

    public static void render(){
        // draw to framebuffer
        shader.uploadFloat("uTime", Time.getTime());
        fbShader.uploadFloat("uTime", Time.getTime());
        fbShader.uploadVec2f("pixels", new Vector2f(180, 90));

        frameBuffer.bind();
        glEnable(GL_DEPTH_TEST);
        // clear framebuffer contents
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // draw scene
        shader.bind();
        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        shader.unbind();

        // back to default window surface
        frameBuffer.unbind();

        fbr.render();
//        glDisable(GL_DEPTH_TEST);
//
//        // draw framebuffer to screen
//        glClearColor(.5f, .5f, .5f, 1.0f);
//        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//
//        fbShader.bind();
//        glBindVertexArray(fbvao);
//        frameBuffer.bindTexture();
//        glEnableVertexAttribArray(0);
//        glEnableVertexAttribArray(1);
//        glDrawArrays(GL_TRIANGLES, 0, 6);
//        glDisableVertexAttribArray(0);
//        glDisableVertexAttribArray(1);
//
//        frameBuffer.unbindTexture();
//        fbShader.unbind();


    }

    public static void clean(){
        shader.clean();
        fbShader.clean();
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        fbr.clean();
    }


    public static void main(String[] args){
        System.out.println("Hello World!");
        Window window = Window.getInstance(Window.DEFAULTWIDTH, Window.DEFAULTHEIGHT, Window.DEFAULTTITLE);
        window.init();

        // TEST AREA
        Camera2D camera2D = new Camera2D(Window.DEFAULTWIDTH, Window.DEFAULTHEIGHT);

        init();

        /* TODO - textures, shaders, rendering, triangle
            steps:
                - //shaders
                - //triangle
                - //textures
                - //rendering
                - //camera
                - framebuffer >:C
         */
        Time.start();
//        window.setClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        while(!window.shouldWindowClose()){
            Window.pollEvents();
//            window.clearMasks();

            // update
            camera2D.update();
            shader.uploadMat4f("proj", camera2D.getProjMatrix());
            shader.uploadMat4f("view", camera2D.getViewMatrix());

            // render
            render();

            window.swapGLFWBuffers();
            Time.update();
        }

        // TESTING
        clean();

        window.close();
        Window.end();
        System.out.println("Closing Application!");

    }
}
