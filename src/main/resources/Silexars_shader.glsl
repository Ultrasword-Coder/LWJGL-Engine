#version 330 core

in vec3 fPos;
in vec4 fCol;
//in vec2 fTexCoords;
//in float fTexID;

//uniform sampler2D tex[9];
uniform float uTime;
uniform vec3 uRes;

#define r uRes.xy

// http://www.pouet.net/prod.php?which=57245
// If you intend to reuse this shader, please add credits to 'Danilo Guanabara'

void main() {
    //    gl_FragColor = texture(tex[int(fTexID)], fTexCoords);
    //    if(gl_FragColor.a == 0) discard;
    //    gl_FragColor = vec4(sin(uTime), fCol.y, fCol.z, fCol.w);
    vec3 c;
    float l, z=uTime;
    for(int i = 0; i < 3; i++){
        vec2 uv, p = (fPos.xy-r.xy/2)/r;
        uv=p;
        p-=.5;
        p.x*=r.x/r.y;
        z+=.07;
        l=length(p);
        uv+=p/l*(sin(z)+1.)*abs(sin(l*9.-z-z));
        c[i] = .01/length(mod(uv,1.)-.5);
    }
    gl_FragColor = vec4(c/l, uTime);

}
