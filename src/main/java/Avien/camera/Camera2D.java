package Avien.camera;

import engine.graphics.Camera;
import org.joml.Vector3f;

public class Camera2D extends Camera {
    public static final float DEFAULT_FAR = 100.0f;
    public static final float DEFAULT_NEAR = 0.0f;

    private float near, far;

    public Camera2D(float width, float height, float near, float far){
        super(width, height);
        this.near = near;
        this.far = far;
        // set default position
        this.position.z = -10.0f;
        update();
    }

    public Camera2D(float width, float height){
        super(width, height);
        this.near = DEFAULT_NEAR;
        this.far = DEFAULT_FAR;
        // set default position
        this.position.z = -10.0f;
        update();
    }

    public void updateProjMatrix(){
        projMatrix.identity();
        projMatrix.ortho(0.0f, getPixelSize() * width, 0.0f, getPixelSize() * height, near, far);
    }

    public void updateViewMatrix(){
        viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(position.x, position.y, 0.0f), position, up);
    }

    @Override
    public void update() {
        if(isChanged()){
            // update the view matrix + projection matrix 2D
            updateProjMatrix();
            updateViewMatrix();

            System.out.println(projMatrix.toString());
            System.out.println(viewMatrix.toString());
            notChanged();
        }
    }
}
