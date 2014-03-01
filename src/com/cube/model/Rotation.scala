package com.cube.model

//represents a rotation to a certain angle.
case class Rotation(xRotation : Double, yRotation: Double, zRotation : Double) {

  
  //produces a list of rotations that step from this roation to the one passed in
  // Allows you to create an aimation sequence by stepping through each item
  // in the list
  def to(otherAngle : Rotation) : List[Rotation] = {
         def translate(deltaX:Double,deltaY:Double,deltaZ:Double): Rotation = {
        		 Rotation(xRotation+deltaX,zRotation+deltaY,zRotation+deltaZ)
         }
         var xdelta = (otherAngle.xRotation -  xRotation)
         var ydelta = (otherAngle.yRotation -  yRotation)
         var zdelta = (otherAngle.zRotation -  zRotation) 
         var length = Math.sqrt((xdelta*xdelta)+(ydelta*ydelta)+(zdelta*zdelta))
         var noOfSteps = (length*360/(math.Pi*2)).floor.toInt
         var unitLength = length/noOfSteps
         val translationPoints = 
                   for {i : Int <- 0 to noOfSteps-1} yield translate((xdelta*i/noOfSteps), 
                                                                   (ydelta*i/noOfSteps),
                                                                   (zdelta*i/noOfSteps))
         translationPoints.toList
     }
}
