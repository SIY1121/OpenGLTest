#version 330 core

// Interpolated values from the vertex shaders
in vec2 UV;

// Ouput data
out vec3 color;

// Values that stay constant for the whole mesh.
uniform sampler2D myTextureSampler;

void main(){
    vec4 tt = texture2D( myTextureSampler, UV);
    float a = (tt.r+tt.g+tt.b)/3.0;
	// Output color = color of the texture at the specified UV
	color = vec3(a);
}