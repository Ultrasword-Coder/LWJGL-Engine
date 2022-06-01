#vertex
#version 330 core

layout(location=0) in vec3 aPos;
layout(location=1) in vec4 aCol;
//layout(location=2) in vec2 aTexCoords;
//layout(location=3) in float aTexID;

uniform mat4 proj;
uniform mat4 view;
//uniform float uTime;

out vec3 fPos;
out vec4 fCol;
//out vec2 fTexCoords;
//out float fTexID;

void main() {
    gl_Position = proj * view * vec4(aPos, 1.0);
    fPos = aPos;
    fCol = aCol;
    //    fTexCoords = aTexCoords;
    //    fTexID = aTexID;
}


#fragment
#version 330 core

in vec3 fPos;
in vec4 fCol;
//in vec2 fTexCoords;
//in float fTexID;

//uniform sampler2D tex[9];
uniform float uTime;
uniform vec3 uRes;

void main() {
    //    gl_FragColor = texture(tex[int(fTexID)], fTexCoords);
    //    if(gl_FragColor.a == 0) discard;
    gl_FragColor = vec4(sin(uTime), fCol.y, fCol.z, fCol.w);
}
