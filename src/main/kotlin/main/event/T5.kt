package main.event

import com.jogamp.opengl.*
import com.jogamp.opengl.util.texture.Texture
import com.jogamp.opengl.util.texture.TextureIO
import glm.vec3.Vec3
import glm.mat4x4.Mat4
import glm.glm
import main.*
import java.nio.FloatBuffer

class T5 : GLEventListener {
    lateinit var gl: GL3

    var vertexDataID = 0
    var colorDataID = 0
    var programID = 0
    var vertexArrayID = 0
    var texture = 0
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

        val uvBufferData = floatArrayOf(
                0.000059f, 1.0f - 0.000004f,
                0.000103f, 1.0f - 0.336048f,
                0.335973f, 1.0f - 0.335903f,
                1.000023f, 1.0f - 0.000013f,
                0.667979f, 1.0f - 0.335851f,
                0.999958f, 1.0f - 0.336064f,
                0.667979f, 1.0f - 0.335851f,
                0.336024f, 1.0f - 0.671877f,
                0.667969f, 1.0f - 0.671889f,
                1.000023f, 1.0f - 0.000013f,
                0.668104f, 1.0f - 0.000013f,
                0.667979f, 1.0f - 0.335851f,
                0.000059f, 1.0f - 0.000004f,
                0.335973f, 1.0f - 0.335903f,
                0.336098f, 1.0f - 0.000071f,
                0.667979f, 1.0f - 0.335851f,
                0.335973f, 1.0f - 0.335903f,
                0.336024f, 1.0f - 0.671877f,
                1.000004f, 1.0f - 0.671847f,
                0.999958f, 1.0f - 0.336064f,
                0.667979f, 1.0f - 0.335851f,
                0.668104f, 1.0f - 0.000013f,
                0.335973f, 1.0f - 0.335903f,
                0.667979f, 1.0f - 0.335851f,
                0.335973f, 1.0f - 0.335903f,
                0.668104f, 1.0f - 0.000013f,
                0.336098f, 1.0f - 0.000071f,
                0.000103f, 1.0f - 0.336048f,
                0.000004f, 1.0f - 0.671870f,
                0.336024f, 1.0f - 0.671877f,
                0.000103f, 1.0f - 0.336048f,
                0.336024f, 1.0f - 0.671877f,
                0.335973f, 1.0f - 0.335903f,
                0.667969f, 1.0f - 0.671889f,
                1.000004f, 1.0f - 0.671847f,
                0.667979f, 1.0f - 0.335851f
        )

        vertexDataID = gl.UGenBuffer()
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vertexDataID)
        //TODO sizeにわたす数値はbute単位 Floatの場合*4byte
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, vertexBufferData.size * 4L, FloatBuffer.wrap(vertexBufferData), GL3.GL_STATIC_DRAW)

        colorDataID = gl.UGenBuffer()
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, colorDataID)
        //TODO sizeにわたす数値はbute単位 Floatの場合*4byte
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, uvBufferData.size * 4L, FloatBuffer.wrap(uvBufferData), GL3.GL_STATIC_DRAW)

        texture = gl.UGenTexture()
        val data = TextureIO.newTextureData(GLProfile.get(GLProfile.GL3), ClassLoader.getSystemResourceAsStream("gl3/5.png"), true, "png")

        gl.USetupTexture(texture, data.width, data.height, data)

        programID = gl.UCreateProgram(
                gl.UCreateVertexShader(loadTextFromResource("gl3/5v.glsl")),
                gl.UCreateFragmentShader(loadTextFromResource("gl3/5f.glsl"))
        )

        val p = glm.perspective(Math.PI.toFloat() / 2f, 4f / 3f, 0.1f, 100f)
        val v = glm.lookAt(
                Vec3(4, 3, 3),
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

        gl.glActiveTexture(GL3.GL_TEXTURE0)
        gl.glBindTexture(GL3.GL_TEXTURE_2D, texture)
        //TODO これやるとエラる
        //gl.glUniform1i(gl.glGetUniformLocation(programID, "myTextureSampler"), texture)


        gl.glDrawArrays(GL3.GL_TRIANGLES, 0, 12 * 3)
        gl.glDisableVertexAttribArray(0)
    }

    override fun dispose(drawable: GLAutoDrawable?) {

    }
}