package main.event

import com.jogamp.opengl.GL2
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import com.jogamp.opengl.util.texture.Texture
import com.jogamp.opengl.util.texture.TextureIO
import main.UCreateFragmentShader
import main.UCreateProgram
import main.UCreateVertexShader
import main.loadTextFromResource
import java.nio.FloatBuffer

class Listener1 : GLEventListener {
    lateinit var gl: GL2

    var program = 0
    lateinit var texture: Texture

    override fun reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT)
    }

    override fun display(drawable: GLAutoDrawable) {
        gl.glUseProgram(program)
        gl.glBindTexture(GL2.GL_TEXTURE_2D,0)
        val uv = floatArrayOf(
                1f, 1f,
                0f, 1f,
                0f, 0f,
                1f, 0f
        )

        val pos = floatArrayOf(
                0.5f, 0.5f,
                -0.5f, 0.5f,
                -0.5f, -0.5f,
                0.5f, -0.5f
        )

        val uvLocation = gl.glGetAttribLocation(program, "uv")
        val positionLocation = gl.glGetAttribLocation(program, "position")

        gl.glEnableVertexAttribArray(uvLocation)
        gl.glEnableVertexAttribArray(positionLocation)

        gl.glVertexAttribPointerARB(uvLocation, 2, GL2.GL_FLOAT, false, 0, FloatBuffer.wrap(uv))
        gl.glVertexAttribPointerARB(positionLocation, 2, GL2.GL_FLOAT, false, 0, FloatBuffer.wrap(pos))

        val textureLocation = gl.glGetUniformLocation(program, "texture")
        gl.glUniform1i(textureLocation, texture.target)

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT)
        gl.glMatrixMode(GL2.GL_MODELVIEW)
        gl.glLoadIdentity()

        //gl.glRotated(   90.0,0.0, 0.0, 1.0)
        //gl.glScaled(1.5,1.5,1.5)
        texture.bind(gl)

        gl.glDrawArrays(GL2.GL_TRIANGLE_FAN, 0, 4)

//        gl.glBegin(GL2.GL_QUADS)
//        gl.glTexCoord2d(1.0,1.0)
//        gl.glVertex3d(0.5,0.5,0.0)
//        gl.glTexCoord2d(0.0,1.0)
//        gl.glVertex3d(-0.5,0.5,0.0)
//        gl.glTexCoord2d(0.0,0.0)
//        gl.glVertex3d(-0.5,-0.5,0.0)
//        gl.glTexCoord2d(1.0,-1.0)
//        gl.glVertex3d(0.5,-0.5,0.0)
//        gl.glEnd()
    }

    override fun init(drawable: GLAutoDrawable) {
        gl = drawable.gl.gL2
        gl.glClearColor(0.2f, 0.2f, 0.2f, 1f)
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT)
        val vertexSource = loadTextFromResource("vertex.shader")
        val fragmentSource = loadTextFromResource("fragment.glsl")
        println(vertexSource)
        program = gl.UCreateProgram(
                gl.UCreateVertexShader(vertexSource),
                gl.UCreateFragmentShader(fragmentSource)
        )
        texture = TextureIO.newTexture(ClassLoader.getSystemResource("v1-s.png"), true, "png")
    }

    override fun dispose(drawable: GLAutoDrawable) {

    }
}