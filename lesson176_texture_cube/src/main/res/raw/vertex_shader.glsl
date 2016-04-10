attribute vec4 a_Position;
uniform mat4 u_Matrix;
varying vec3 v_Position;

void main()
{
    v_Position = a_Position.xyz;
    gl_Position = u_Matrix * a_Position;
}