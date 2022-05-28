#version 330 core

layout(location=0) in vec3 aPos;
layout(location=1) in vec2 aTex;

uniform mat4 proj;
uniform mat4 tran;

out vec2 fTex;

void main(){
    gl_Position = proj * tran * vec4(aPos, 1.0);
    fTex = aTex;
}
