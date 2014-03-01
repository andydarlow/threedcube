package com.cube.model
import com.cube.model._
import com.cube.model.generators._
//point in a 3d world.
case class Point(x: Int, y: Int, z: Int) {
      def translate(deltaX:Int,deltaY:Int,deltaZ:Int):Point = {
       Point(x+deltaX,y+deltaY,z+deltaZ)
     }
      
     // return a list of points that move in a diagonal line from this point
     // to the one passed in.
     def to(point:Point): List[Point] = {
        
         var xdelta = (point.x -  x)
         var ydelta = (point.y -  y)
         var zdelta = (point.z -  z) 
         var length = Math.sqrt((xdelta*xdelta)+(ydelta*ydelta)+(zdelta*zdelta))
         
         val translationPoints = 
                   for {i : Int <- 0 to length.toInt} yield translate((xdelta*i/length).toInt, 
                                                                (ydelta*i/length).toInt,
                                                                (zdelta*i/length).toInt)
         translationPoints.toList
     }
}

//useful conversions....
object Point {
     //character to a list of points (so that it can be displayed as a frame)
     implicit def toList(char : Char) : List[Point] = { 
          CharacterSceneGenerator.convertToPoints(char)
     }     
}
