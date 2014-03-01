package com.cube.marshall

import com.cube.model._
import com.cube.model.animationdsl._
  /**
   * marshalls the data from the cube to a format that can be used with the
   * electronic version.
   * The electronic version uses a set of bytes where each bit
   * in the byte represents whether to light that LED or not. For a 8x8x8 cube
   * there will be 64 bytes
   * 
   * The idead is to place this output into a C program that can then be burnt
   * onto an eprom.
   */
object CubeMarshaller {
 
  /**
   * converts a sequence of frames to something that the electronic version of
   * the cube can use. The data is printed to the screen
   * @param movie a list of frames that you want to render to bytes.
   */
   def marshall(movie: Movie ) = {
      val cubeFrames = movie.play().map(new CubeFrame(_))
     
      def renderFrame(frameAsWord : BigWord) = {
           var bytesInFrame = frameAsWord.getBytes()
           print("{ ")
           for {i <- 0 to bytesInFrame.size-2} print(bytesInFrame(i) +",")
           println(bytesInFrame(bytesInFrame.size-1) + " },")
      }   
	  cubeFrames.foreach(singleFrame=> {renderFrame(convertToWord(singleFrame))})
  
      println(cubeFrames.size + " frames (" + cubeFrames.size*FrameDimension.WIDTH*FrameDimension.WIDTH/1024 + "K )")
  	}
  
     // turn a frame to a set of bits that the hardware cube can display. Each frame is: 8x8x8 bits
     //(each led is a bit - 1 switch on, 0 switch off). The bits are represents by byte values (Actually
     // using ints for bytes to keep it simple)
     private def convertToWord(frame: CubeFrame): BigWord = {
       
          //transpose to electronic cube coords
          def toCubeCoordinateSystem(rawPoint : Point) = {
            Point( rawPoint.x+FrameDimension.WIDTH/2,
                   rawPoint.y+FrameDimension.WIDTH/2,
                   rawPoint.z+FrameDimension.WIDTH/2) 
          }
           ////calculate bit position of points in fictional 
           ////FrameDimension.WIDTH^3 word to represent animation
          def toBitPosition(coordinate : Point) : Int = {
                (coordinate.z * FrameDimension.WIDTH*FrameDimension.WIDTH) + 
                (coordinate.x * FrameDimension.WIDTH) +
                 coordinate.y 
          }
         //get the bit positions of all the 1 bits in the word - the word represents a frame of animation
         //on the cube
         val onBitsPositions  = frame.points.map(toCubeCoordinateSystem(_)).map(toBitPosition(_))                                                             
         new BigWord(onBitsPositions,Math.pow(FrameDimension.WIDTH,3).toInt)
   }
}
