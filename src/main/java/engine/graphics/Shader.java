package engine.graphics;

import org.joml.*;
import org.lwjgl.BufferUtils;
import engine.utils.FileIO;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;


public class Shader {
    // const vars
    public static final int NULLSHADER = 0;


    // shader
    private String vFile, fFile;
    private String vShader, fShader;
    private int vID, fID, programID;

    // TODO - make a buffer that stores positions of uniforms

    public Shader(String v, String f){
        this.vFile = v;
        this.fFile = f;
        // load file contents into strings
        this.vShader = FileIO.getFileContents(v);
        this.fShader = FileIO.getFileContents(f);
    }

    public void create(){
        // create the shader here
        vID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vID, vShader);
        glCompileShader(vID);
        checkCompileErrors(GL_VERTEX_SHADER, vID);

        fID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fID, fShader);
        glCompileShader(fID);
        checkCompileErrors(GL_FRAGMENT_SHADER, fID);

        // link shaders
        programID = glCreateProgram();
        glAttachShader(programID, vID);
        glAttachShader(programID, fID);
        glLinkProgram(programID);
        int success = glGetProgrami(programID, GL_LINK_STATUS);
        if (success == GL_FALSE){
            int len = glGetProgrami(programID, GL_INFO_LOG_LENGTH);
            System.err.println("[Shader | create] Shader Program could not be linked!");
            System.err.println(glGetProgramInfoLog(programID, len));
            assert false : "Shader could not be linked!";
        }
    }

    private void checkCompileErrors(int type, int sID){
        int success = glGetShaderi(sID, GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int len = glGetShaderi(sID, GL_INFO_LOG_LENGTH);
            System.err.format("[Shader | checkCompileErrors] %s shader compilation failed from file '%s'\n",
                    type == GL_VERTEX_SHADER ? "Vertex" : "Fragment", type == GL_VERTEX_SHADER ? vShader : fShader);
            System.err.println(glGetShaderInfoLog(sID, len));
            assert false : "Failed to compile shader!";
        }
    }

    // methods
    public String getVertexShader(){
        return vFile;
    }

    public String getFragShader(){
        return fFile;
    }

    // methods for uploading
    public void bind(){
        glUseProgram(programID);
    }

    public void unbind(){
        glUseProgram(Shader.NULLSHADER);
    }

    public void clean(){
        unbind();
        glDeleteProgram(programID);
        glDeleteShader(vID);
        glDeleteShader(fID);
    }

    public void uploadMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(programID, varName);
        bind();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    public void uploadMat3f(String varName, Matrix3f mat3) {
        int varLocation = glGetUniformLocation(programID, varName);
        bind();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
        mat3.get(matBuffer);
        glUniformMatrix3fv(varLocation, false, matBuffer);
    }

    public void uploadVec4f(String varName, Vector4f vec) {
        int varLocation = glGetUniformLocation(programID, varName);
        bind();
        glUniform4f(varLocation, vec.x, vec.y, vec.z, vec.w);
    }

    public void uploadVec3f(String varName, Vector3f vec) {
        int varLocation = glGetUniformLocation(programID, varName);
        bind();
        glUniform3f(varLocation, vec.x, vec.y, vec.z);
    }

    public void uploadVec2f(String varName, Vector2f vec) {
        int varLocation = glGetUniformLocation(programID, varName);
        bind();
        glUniform2f(varLocation, vec.x, vec.y);
    }

    public void uploadFloat(String varName, float val) {
        int varLocation = glGetUniformLocation(programID, varName);
        bind();
        glUniform1f(varLocation, val);
    }

    public void uploadInt(String varName, int val) {
        int varLocation = glGetUniformLocation(programID, varName);
        bind();
        glUniform1i(varLocation, val);
    }

    public void uploadTexture(String varName, int slot){
        int varLocation = glGetUniformLocation(programID, varName);
        bind();
        glUniform1i(varLocation, slot);
    }

    public void uploadIntArray(String varName, int[] data){
        int varLocation = glGetUniformLocation(programID, varName);
        bind();
        glUniform1iv(varLocation, data);
    }

}
