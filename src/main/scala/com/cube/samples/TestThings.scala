package com.cube.samples

import com.cube.application.engine.CubeEngine
import com.cube.model._
import com.cube.model.animationdsl._
import com.cube.model.generators._

object TestThings {
  
   
  def main(args : Array[String]) : Unit = {
     CubeEngine.run(
    		 List(
    		    Animation.scrollMessage("WELCOME")
    	      ,
    		    (Animation() move   (Point(0,8,0) to Point(0,0,0))) then
    		    (Animation() rotate (Rotation(0,0,0) to Rotation(0,math.Pi*2,0)) atSpeed 16)  then 
    		    (Animation() move   (Point(0,0,0) to Point(8,0,0))) on '8'
            ,
         		(Animation() still   25) then 
                (Animation() scale   (1 to  5))  on new XSinWaveSceneGenerator('*') 
            )
    )
  }
}