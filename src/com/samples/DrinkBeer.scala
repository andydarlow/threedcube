package com.samples
import com.cube.application.engine.CubeEngine
import com.cube.model._
import com.cube.model.animationdsl._
import com.cube.model.generators._

object DrinkBeer {
   def materialise(char : Char) : AnimationSequence = {
                (Animation() scale   new Range(5,1,-1)) then
          		(Animation() still   5) then
          		(Animation() scale   (1 to  5)) on char

  }
  
  def main(args : Array[String]) : Unit = {
     CubeEngine.run(
    		 List(
    		    Animation.scrollMessage("DRINK")
    	      ,
    		     materialise('B'), 
                 materialise('E'), 
                 materialise('E'), 
                 materialise('R')
              ,
    		    (Animation() move   (Point(0,8,0) to Point(0,0,0))) then
                (Animation() still  10)  then  
                (Animation() move   (Point(0,0,0) to Point(8,0,0))) and 
                (Animation() rotate (Rotation(0,0,0) to Rotation(0,math.Pi*2,0)) atSpeed 16) 
                on 'N'
              ,
                (Animation() move   (Point(0,0,8) to Point(0,0,0))) then
                (Animation() still  15)  then  
    		    (Animation() move   (Point(0,0,0) to Point(0,8,0))) and 
                (Animation() rotate (Rotation(0,0,0) to Rotation(math.Pi*2,math.Pi*2,0)) atSpeed 16) 
                 on 'O'
              ,
                (Animation() move   (Point(-8,0,-8) to Point(0,0,0)))  then
                (Animation() still  25)  then  
                (Animation() move   (Point(0,0,0) to Point(0,-8,0)))  and
                (Animation() rotate (Rotation(0,0,0) to Rotation(0,math.Pi*4,0)) atSpeed 16) 
    		     on 'W'
              ,
              (Animation() still  10)  on ' '  // empty frames to show end of animation
            )
  	)
  }
}
