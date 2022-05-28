#version 330 core

in vec2 fTex;

uniform float uTime;
uniform sampler2D tex;

void main() {
    gl_FragColor = texture2D(tex, fTex);
}
