package com.cube.application.engine
import com.cube.model._
import com.cube.view._
import com.cube.marshall._
import com.cube.model.animationdsl._
import com.cube.model.generators._

//simple object to give standard sequence for running cube
object CubeEngine {
  
  def run(animation: List[AnimationSequence])  = {
     val show = Movie(animation)
     CubeMarshaller.marshall(show)    
     val cube = new Cube()
     val world = new Cube3DWorld(cube)
     cube.play(show) 
     world.show()
  }
}
