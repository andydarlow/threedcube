package com.cube.view

//represents an angle in radians

case class Angle(angle : Double) {
  
      //increase (can be negative delta)
      def increase(delta : Double): Angle = {
         var newAngle = angle + delta
         newAngle = newAngle match {      
           case a if a < 0         => a + math.Pi*2
           case a if a > math.Pi*2 => a - math.Pi*2
           case a  => a
         }
         new Angle(newAngle)
      } 
}

//useful auto conversions...
object Angle {
    implicit def toDouble(a : Angle) : Double = {a.angle}
}

  