package com.cube.view
import com.cube.model._
import com.cube.model.animationdsl._
import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing._

import javax.media.j3d._
import javax.vecmath._
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;


/**
 * simulated representation of the cube device. Its is a cube of LEDS.
 * This file contains a simulator of the real Cube containing LEDs. The cube
 * is drawn in 3D using java 3d
 */
class Cube {
  
     //speed of each frame in millisecs.
     val FRAMERATE = 100  // millisecs
     
      // each axis in the cube must be between these points.
     val LEDGAP  = 1  // gap between each LED in the 3d view
    
    //maps the model (LED's poistion) to the LED in the view.
    var LEDs : Map[Point,LED]  = Map.empty

    // underlying structure that displays the cube
    var scene : TransformGroup = new TransformGroup()
    
    //----------------------------- constructor --------------------------------
    //setup the cube...
    {
      // create and place the LEDS in the cube
      def createLEDs(): Unit = {
           def mapToViewCoordinate(p:Point) : Point = {Point(p.x*LEDGAP,p.y*LEDGAP,p.z*LEDGAP)}

           LEDs  =  Map.empty ++  (for {x : Int <- FrameDimension.MINAXIS to FrameDimension.MAXAXIS 
        	   					        y : Int <- FrameDimension.MINAXIS to FrameDimension.MAXAXIS
                  	                    z : Int <- FrameDimension.MINAXIS to FrameDimension.MAXAXIS
                  	               } 
                                   yield Point(x,y,z) -> new LED(mapToViewCoordinate(Point(x,y,z))))
       }
       
      //create the Java 3d construct underwhich all the LEDs will be placed
      def createCube() : TransformGroup = {
        	    val LEDGroup : TransformGroup  = new TransformGroup()
                LEDs.values.foreach(led => LEDGroup.addChild(led.as3dScene()))
                LEDGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE)
                LEDGroup
      }
      createLEDs()
      scene = createCube()
    }
    //------------------------------------------------------------------------
    // -----------------  play the movie -------------------------------------
    //------------------------------------------------------------------------
      
    //play the movie passed in
    def play(movie: Movie) = {
        render(movie.play().map(frame => new CubeFrame(frame)))
    }
    
    // renders and shows the frames passed in..
    def render(animation : List[CubeFrame]): Unit = {
      object animator  { //ensure infinite loop of animations....
         var remainingFrames : List[CubeFrame] = animation
         def nextFrame(): CubeFrame = {
                val frames : List[CubeFrame] = remainingFrames match {
                     case Nil => animation //end of animation - restart
                     case f:List[frame]  =>  f 
                }
                remainingFrames = frames.tail
                frames.head
            }
       }
       new Timer(FRAMERATE, new ActionListener  {
                 def actionPerformed(e: ActionEvent) = {
                   renderFrame(animator.nextFrame())                  
                 }                                     
       }).start()
    }
    
    //render the frame on this cube - render the LEDS in the cube...
    def renderFrame(frame: CubeFrame) = {
       //switch off unused LEDs
       LEDs.keys.filter(point => !frame.points.contains(point))
                .foreach(point=>LEDs.get(point) match {case Some(led: LED) => led.off()})
       //switch on lit LEDS in the frame...
       frame.points.foreach(point=>LEDs.get(point) match {case Some(led: LED) => led.on()})
    }
    
    //--------------------------------- end play -----------------------------------
}

  // makes the code more readble by implicitly converting cube to java 3d constructs
  object Cube {
     implicit def toSceneGraph(cube: Cube): TransformGroup = { cube.scene }
}
