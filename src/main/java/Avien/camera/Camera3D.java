package Avien.camera;

import engine.graphics.Camera;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.glFrustum;


public class Camera3D extends Camera {
    public static final float DEFAULT_FAR = 1000.0f;
    public static final float DEFAULT_NEAR = 0.1f;
    public static final float DEFAULT_ASPECT = 16f/9f;
    public static final float DEFAULT_FOV = 45.0f;

    private float near, far, fov, aspect, xScale, yScale;
    private Vector3f position;
    private Quaternionf rotation;

    public Camera3D(float width, float height, float fov, float aspect, float near, float far){
        /*
            3D cameras are different because whenever you want to render something
                1. upload the camera perspective matrix
                2. upload the objects transformation matrix :(
         */
        super(width, height);
        this.position = new Vector3f();
        this.rotation = new Quaternionf();
        this.aspect = aspect;
        this.fov = fov;
        this.near = near;
        this.far = far;
    }

    public Camera3D(float width, float height){
        this(width, height, DEFAULT_FOV, DEFAULT_ASPECT, DEFAULT_NEAR, DEFAULT_FAR);
    }

    public void updateProjMatrix(){
        projMatrix.identity();
        projMatrix.perspective((float)Math.toRadians(fov), aspect, near, far);
        projMatrix.frustum(-width/2, width/2, -height/2, height/2, near, far);
        // glFrustum(-width/2, width/2, -height/2, height/2, near, far);

    }

    public void updateTranslationMatrix(){
        viewMatrix.identity();
        viewMatrix.translate(this.position);
        viewMatrix.rotate(this.rotation);
    }

    @Override
    public void update() {
        if(isChanged()){
            updateProjMatrix();
            updateTranslationMatrix();
            notChanged();
        }
    }

    public void changeRotation(Quaternionf rotation){
        this.rotation.add(rotation);
        this.changed = true;
    }

    public void changeRotation(float x, float y, float z, float w){
        this.rotation.add(x, y, z, w);
        this.changed = true;
    }
}
