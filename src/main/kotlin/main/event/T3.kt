package main.event

import com.jogamp.opengl.DebugGL3
import com.jogamp.opengl.GL3
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import glm.vec3.Vec3
import glm.mat4x4.Mat4
import glm.glm
import main.*
import java.nio.FloatBuffer

class T3 : GLEventListener {
    lateinit var gl: GL3

    var vertexDataID = 0
    var programID = 0
    var vertexArrayID = 0
    var MVP: FloatArray = FloatArray(4 * 4)

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
                gl.UCreateVertexShader(loadTextFromResource("gl3/3v.glsl")),
                gl.UCreateFragmentShader(loadTextFromResource("gl3/3f.glsl"))
        )

        val p = glm.perspective(Math.PI.toFloat() / 2f, 4f / 3f, 0.1f, 100f)
        val v = glm.lookAt(
                Vec3(4, 3, 3),
                Vec3(0, 0, 0),
                Vec3(0, 1, 0)
        )
        val m = Mat4(1.0f)

        val buf = FloatBuffer.allocate(4*4)
        (p * v * m) to buf
        MVP = buf.array()
    }

    override fun display(drawable: GLAutoDrawable?) {
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT)
        gl.glUseProgram(programID)

        val mvpLocation = gl.glGetUniformLocation(programID, "MVP")
        gl.glUniformMatrix4fv(mvpLocation, 1, false, MVP, 0)

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