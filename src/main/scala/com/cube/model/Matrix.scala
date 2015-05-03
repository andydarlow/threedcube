package com.cube.model

import  com.cube.model._
import javax.vecmath._

/**
 * immutable wrapper around the java 3d matrix. classe. Represents
 * a 4x4 matrix that you use transform points in a 3d world (check on line
 * for tutorials on 4x4 matrix to see how they are used)
 */
class Matrix(matrix : Matrix4f ) {

  def getMatrix() = matrix
  
  // move a point by passed in by the values contained in the matrix. Creates
  // a new point that is the tranformation of the point
  def transformPoint(point: Point): Point = {
        val source : Point3f = new Point3f(point.x,point.y,point.z)
        val output : Point3f = new Point3f()
        matrix.transform(source,output)
        Point(Math.round(output.x),
              Math.round(output.y), 
              Math.round(output.z))
   }
  
    //transforms a set of points to a new location using this matrix
    def transform(points : List[Point]) : List[Point] = {
         points.map(p=>transformPoint(p))
    }
 
    // translate an animation using this matrix 
    def transform(frame : AnimationFrame) : AnimationFrame = {
         new AnimationFrame(transform(frame.points))
    }
 
   //multiply 2 matrix together to produce a new matrix. 
   def *(other :  Matrix) : Matrix = {
        val newMatrix :  Matrix4f = new Matrix4f(matrix)
        newMatrix.mul(other.getMatrix())       
        new Matrix(newMatrix)
   }
}

//------------------------------------------------------------------------------
// useful singleton methods on matrix. Used to produce a matrix that will
// perform a certain task
object Matrix {
   
  //create a matrix that will translate a point
  def translate(x: Int,y: Int, z: Int): Matrix = {
       val newMatrix :  Matrix4f = new Matrix4f()
       newMatrix.set(new Vector3f(x,y,z))
       new Matrix(newMatrix)
  }

  //create a matrix that will rotate a point by the amounts passed in - values in radians
  def rotate(angle: Rotation): Matrix = {  
       val newMatrix :  Matrix4f = new Matrix4f()
       val yMatrix :  Matrix4f = new Matrix4f()
       val zMatrix :  Matrix4f = new Matrix4f()
       newMatrix.rotX(angle.xRotation.asInstanceOf[Float])
       yMatrix.rotY(angle.yRotation.asInstanceOf[Float])
       zMatrix.rotZ(angle.zRotation.asInstanceOf[Float])
       newMatrix.mul(yMatrix)
       newMatrix.mul(zMatrix)
       new Matrix(newMatrix)
  }

  //create a matrix that will scale a point by the amount passed in.
  def scale(scale: Float): Matrix = {
       val newMatrix :  Matrix4f = new Matrix4f()
       newMatrix.set(scale)
       new Matrix(newMatrix)
  }
  
  def identity(): Matrix = {
       val newMatrix :  Matrix4f = new Matrix4f()
       newMatrix.setIdentity()
       new Matrix(newMatrix)

  }
  
}

