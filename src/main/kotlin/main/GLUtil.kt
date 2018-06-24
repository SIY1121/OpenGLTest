package main

import com.jogamp.opengl.GL
import com.jogamp.opengl.GL2
import com.jogamp.opengl.util.texture.TextureIO
import java.io.FileOutputStream
import java.io.FileWriter
import java.nio.ByteBuffer
import java.nio.IntBuffer

fun GL2.UGenTexture(): Int {
    val buf = IntBuffer.allocate(1)
    this.glGenTextures(1, buf)
    return buf.get()
}

fun GL2.USetupTexture(texID: Int, w: Int, h: Int, wrapMode: Int = GL.GL_CLAMP_TO_EDGE, filterMode: Int = GL.GL_NEAREST) {
    this.glBindTexture(GL.GL_TEXTURE_2D, texID)
    this.glTextureParameteriEXT(texID, GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, wrapMode)
    this.glTextureParameteriEXT(texID, GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, wrapMode)

    this.glTextureParameteriEXT(texID, GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, filterMode)
    this.glTextureParameteriEXT(texID, GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, filterMode)

    this.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, w, h, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, ByteBuffer.allocate(w * h * 4))

    this.glBindTexture(GL.GL_TEXTURE_2D, 0)
}

fun GL2.UGenFrameBuffer(): Int {
    val buf = IntBuffer.allocate(1)
    this.glGenFramebuffers(1, buf)
    return buf.get()
}

fun GL2.UCreateVertexShader(source: String): Int {
    val id = this.glCreateShader(GL2.GL_VERTEX_SHADER)
    this.glShaderSource(id, 1, kotlin.arrayOf(source), null)
    this.glCompileShader(id)
    return id
}

fun GL2.UCreateFragmentShader(source: String): Int {
    val id = this.glCreateShader(GL2.GL_FRAGMENT_SHADER)
    this.glShaderSource(id, 1, kotlin.arrayOf(source), null)
    this.glCompileShader(id)
    return id
}

fun GL2.UCreateProgram(vtxId: Int, fId: Int, destroyShader: Boolean = true): Int {
    val id = this.glCreateProgram()

    this.glAttachShader(id, vtxId)
    this.glAttachShader(id, fId)
    this.glLinkProgram(id)
    if (destroyShader) {
        this.glDeleteShader(vtxId)
        this.glDeleteShader(fId)
    }


    return id
}

fun GL2.USaveTexture(texId: Int,w : Int,h:Int) {
    this.glBindTexture(GL2.GL_TEXTURE_2D, texId)
    val buf = ByteBuffer.allocate(w * h * 3)
    this.glGetTexImage(GL2.GL_TEXTURE_2D, 0, GL2.GL_BGR, GL2.GL_UNSIGNED_BYTE, buf)
    val out = FileOutputStream("tex.rgb")
    out.write(buf.array())
    out.close()
    gl.glBindTexture(GL2.GL_TEXTURE_2D,0)
}