package engine.handler;

import engine.graphics.Shader;

import java.util.HashMap;

public class ShaderHandler {

    private static HashMap<String, Shader> shaders = new HashMap<>();

    public static Shader getShader(String path){
        Shader shader;
        if(shaders.containsKey(path))
            return shaders.get(path);
        shader = new Shader(path);
        shader.create();
        shaders.put(path, shader);
        return shader;
    }

    public static void clean(){
        for(String path : shaders.keySet())
            shaders.get(path).clean();
        shaders.clear();
    }

}
