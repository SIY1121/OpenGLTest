package main.libTset

import glm.mat4x4.Mat4
import glm.vec3.Vec3

class GLObject {
    var pos = Vec3(0.0, 0.0, 0.0)
    var scale = Vec3(1.0, 1.0, 1.0)
    var rotate = Vec3(0.0, 0.0, 0.0)

    open fun draw(){

    }

    open fun onDraw(mvp : Mat4){

    }
}