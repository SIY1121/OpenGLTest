varying vec2 vuv;
uniform sampler2D texture;
void main(void){
vec4 color = texture2D(texture, vuv);
float a = (color.x + color.y + color.z)/3.0;
       gl_FragColor = vec4(a,a,a,0.0);
}