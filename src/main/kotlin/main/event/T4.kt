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

class T4 : GLEventListener {
    lateinit var gl: GL3

    var vertexDataID = 0
    var colorDataID = 0
    var programID = 0
    var vertexArrayID = 0
    var MVP: FloatArray = FloatArray(4 * 4)

    override fun reshape(drawable: GLAutoDrawable?, x: Int, y: Int, width: Int, height: Int) {

    }

    override fun init(drawable: GLAutoDrawable) {
        gl = drawable.gl.gL3
        drawable.gl = DebugGL3(gl)
        gl.glClearColor(0.5f, 0.5f, 0.5f, 1f)
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT or GL3.GL_DEPTH_BUFFER_BIT)

        // デプステストを有効にする
        gl.glEnable(GL3.GL_DEPTH_TEST)
        // 前のものよりもカメラに近ければ、フラグメントを受け入れる
        gl.glDepthFunc(GL3.GL_LESS)

        vertexArrayID = gl.UGenVertexArray()

        val vertexBufferData = floatArrayOf(
                -1.0f, -1.0f, -1.0f, // 三角形1:開始
                -1.0f, -1.0f, 1.0f,
                -1.0f, 1.0f, 1.0f, // 三角形1:終了
                1.0f, 1.0f, -1.0f, // 三角形2:開始
                -1.0f, -1.0f, -1.0f,
                -1.0f, 1.0f, -1.0f, // 三角形2:終了
                1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, 1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, -1.0f,
                1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f, 1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, 1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, -1.0f,
                -1.0f, 1.0f, -1.0f,
                1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, -1.0f,
                -1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, 1.0f,
                1.0f, -1.0f, 1.0f)
        val colorBufferData = floatArrayOf(
                0.583f, 0.771f, 0.014f,
                0.609f, 0.115f, 0.436f,
                0.327f, 0.483f, 0.844f,
                0.822f, 0.569f, 0.201f,
                0.435f, 0.602f, 0.223f,
                0.310f, 0.747f, 0.185f,
                0.597f, 0.770f, 0.761f,
                0.559f, 0.436f, 0.730f,
                0.359f, 0.583f, 0.152f,
                0.483f, 0.596f, 0.789f,
                0.559f, 0.861f, 0.639f,
                0.195f, 0.548f, 0.859f,
                0.014f, 0.184f, 0.576f,
                0.771f, 0.328f, 0.970f,
                0.406f, 0.615f, 0.116f,
                0.676f, 0.977f, 0.133f,
                0.971f, 0.572f, 0.833f,
                0.140f, 0.616f, 0.489f,
                0.997f, 0.513f, 0.064f,
                0.945f, 0.719f, 0.592f,
                0.543f, 0.021f, 0.978f,
                0.279f, 0.317f, 0.505f,
                0.167f, 0.620f, 0.077f,
                0.347f, 0.857f, 0.137f,
                0.055f, 0.953f, 0.042f,
                0.714f, 0.505f, 0.345f,
                0.783f, 0.290f, 0.734f,
                0.722f, 0.645f, 0.174f,
                0.302f, 0.455f, 0.848f,
                0.225f, 0.587f, 0.040f,
                0.517f, 0.713f, 0.338f,
                0.053f, 0.959f, 0.120f,
                0.393f, 0.621f, 0.362f,
                0.673f, 0.211f, 0.457f,
                0.820f, 0.883f, 0.371f,
                0.982f, 0.099f, 0.879f
        )

        vertexDataID = gl.UGenBuffer()
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexDataID)
        //TODO sizeにわたす数値はbute単位 Floatの場合*4byte
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, vertexBufferData.size * 4L, FloatBuffer.wrap(vertexBufferData), GL3.GL_STATIC_DRAW)

        colorDataID = gl.UGenBuffer()
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, colorDataID)
        //TODO sizeにわたす数値はbute単位 Floatの場合*4byte
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, colorBufferData.size * 4L, FloatBuffer.wrap(colorBufferData), GL3.GL_STATIC_DRAW)

        programID = gl.UCreateProgram(
                gl.UCreateVertexShader(loadTextFromResource("gl3/4v.glsl")),
                gl.UCreateFragmentShader(loadTextFromResource("gl3/4f.glsl"))
        )

        val p = glm.perspective(Math.PI.toFloat() / 2f, 4f / 3f, 0.1f, 100f)
        val v = glm.lookAt(
                Vec3(4, 3, -3),
                Vec3(0, 0, 0),
                Vec3(0, 1, 0)
        )
        val m = Mat4(1.0f)

        val buf = FloatBuffer.allocate(4 * 4)
        (p * v * m) to buf
        MVP = buf.array()
    }

    override fun display(drawable: GLAutoDrawable?) {
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT or GL3.GL_DEPTH_BUFFER_BIT)
        gl.glUseProgram(programID)

        val mvpLocation = gl.glGetUniformLocation(programID, "MVP")
        gl.glUniformMatrix4fv(mvpLocation, 1, false, MVP, 0)

        gl.glBindVertexArray(vertexArrayID)



        gl.glEnableVertexAttribArray(0)
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexDataID)
        gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 0, 0L)

        gl.glEnableVertexAttribArray(1)
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, colorDataID)
        gl.glVertexAttribPointer(1, 3, GL3.GL_FLOAT, false, 0, 0L)

        gl.glDrawArrays(GL3.GL_TRIANGLES, 0, 12*3)
        gl.glDisableVertexAttribArray(0)
    }

    override fun dispose(drawable: GLAutoDrawable?) {

    }
}