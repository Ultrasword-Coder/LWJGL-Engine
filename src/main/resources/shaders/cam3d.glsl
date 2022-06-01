#vertex
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

#fragment
#version 330 core

in vec2 fTex;

uniform sampler2D tex;
uniform float uTime;


void main() {
    gl_FragColor = texture2D(tex, fTex);
}
