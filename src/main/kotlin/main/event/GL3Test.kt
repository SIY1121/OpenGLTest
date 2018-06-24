package main.event

import com.jogamp.opengl.DebugGL3
import com.jogamp.opengl.GL3
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import main.*
import java.nio.FloatBuffer

class GL3Test : GLEventListener {
    lateinit var gl: GL3

    var vertexDataID = 0
    var programID = 0
    var vertexArrayID = 0


    override fun reshape(drawable: GLAutoDrawable?, x: Int, y: Int, width: Int, height: Int) {

    }

    override fun init(drawable: GLAutoDrawable) {
        gl = drawable.gl.gL3
        drawable.gl = DebugGL3(gl)
        gl.glClearColor(0.5f, 0.5f, 0.5f, 1f)
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT)


        vertexArrayID = gl.UGenVertexArray()

        val vertexBufferData = floatArrayOf(
                -1.0f, -1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                0.0f, 1.0f, 0.0f)

        vertexDataID = gl.UGenBuffer()
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexDataID)
        //TODO sizeにわたす数値はbute単位 Floatの場合*4byte
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, vertexBufferData.size * 4L, FloatBuffer.wrap(vertexBufferData), GL3.GL_STATIC_DRAW)

        programID = gl.UCreateProgram(
                gl.UCreateVertexShader(loadTextFromResource("gl3/2v.glsl")),
                gl.UCreateFragmentShader(loadTextFromResource("gl3/2f.glsl"))
        )
    }

    override fun display(drawable: GLAutoDrawable?) {
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT)
        gl.glUseProgram(programID)

        gl.glBindVertexArray(vertexArrayID)



        gl.glEnableVertexAttribArray(0)
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexDataID)
        gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 0, 0L)
        gl.glDrawArrays(GL3.GL_TRIANGLES, 0, 3)
        gl.glDisableVertexAttribArray(0)
    }

    override fun dispose(drawable: GLAutoDrawable?) {

    }
}