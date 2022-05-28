#version 330 core

in vec2 fTex;

uniform sampler2D tex;
uniform float uTime;


void main() {
    gl_FragColor = texture2D(tex, fTex);
}
