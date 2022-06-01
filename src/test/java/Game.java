import Avien.camera.Camera2D;
import engine.hardware.Window;
import engine.utils.Time;

public class Game extends Test {
    public Window window;
    public Camera2D camera;

    public Game(){
        super();
        window = Window.getInstance(1280, 720, "TestGame");
        window.init();
    }

    @Override
    public void init() {
        camera = new Camera2D(1280, 720);
    }

    public void update(){

    }

    @Override
    public void render() {

    }

    @Override
    public void clean() {
        window.close();
        Window.end();
    }

    public static void main(String[] args){
        Game game = new Game();
        game.init();

        Time.start();
        while(game.window.shouldWindowClose()){
            Window.pollEvents();
            game.update();
            game.render();
            game.window.swapGLFWBuffers();
            Time.update();
        }

        game.clean();

    }
}
