package com.cube.model


/**
 * frame of animation on the cube. Note that this is a set of points restricted to the
 * dimensions of the cube. Any point outside the cube's dimensions are discarded
 * 
 * The frame only holds the points that should be shown on in the cube.
 */
class CubeFrame {

     
    var points : List[Point]=Nil  // points that are switched on
 
      def this(animation : AnimationFrame) =  { this() 
                                               set(animation.points) }

     
     // adds the points filtering any points that don't belong in the frame..
     private def set(newPoints : List[Point]) : Unit = {
          points = newPoints.filter(inFrame(_))
     }
  
    //----------------------------------------------------------------------
    //below checks to see if the point is in the frame or not.
    private def inFrame(point : Point) : Boolean =  {
       inFrame(point.x) && inFrame(point.y) && inFrame(point.z)
    }
 
    private def inFrame(pointOnAxis : Int) : Boolean = {
       (pointOnAxis >= FrameDimension.MINAXIS)  && (pointOnAxis <= FrameDimension.MAXAXIS)
    } 
}

//attributes of the frame for displaying
object FrameDimension {
      // each axis in the cube must be between these points.
    val WIDTH=8   // becaue its a cube, width = height = depth
    val MINAXIS = -WIDTH/2
    val MAXAXIS =  WIDTH/2

}
