#version 330 core

in vec2 fTex;

uniform sampler2D fbTex;
uniform float uTime;
uniform vec2 pixels;

#define pi 3.1415

void main() {
//    gl_FragColor = vec4(vec3(1.0 - texture2D(fbTex, fTex)), 1.0);
//     vec2 uv = floor(fTex * pixels) / pixels;
    gl_FragColor = texture2D(fbTex, fTex);//uv);
}
