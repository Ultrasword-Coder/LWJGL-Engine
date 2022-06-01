#vertex
#version 330 core

layout(location=0) in vec2 aPos;
layout(location=1) in vec2 aTex;

out vec2 fTex;

void main() {
    gl_Position = vec4(aPos, 0.0, 1.0);
    fTex = aTex;
}

#fragment
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
