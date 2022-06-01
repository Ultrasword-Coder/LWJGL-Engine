#vertex
#version 330 core

layout(location=0) in vec2 aPos;
layout(location=1) in vec2 aTex;

uniform mat4 proj;
uniform mat4 view;

out vec2 fTex;

void main() {
    gl_Position = proj * view * vec4(aPos, 0.0, 1.0);
    fTex = aTex;
}

#fragment
#version 330 core

in vec2 fTex;

uniform float uTime;
uniform sampler2D tex;

void main() {
    gl_FragColor = texture2D(tex, fTex);
}
