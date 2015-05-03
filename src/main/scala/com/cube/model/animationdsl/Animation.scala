package com.cube.model.animationdsl
import com.cube.model.generators._
import com.cube.model._


/**
 * a set of commands that you can perform in animation. Convenient 1 param
 * methods to make animation code easier to read when definining animations.
 */
abstract class AbstractAnimation {
  
   //override this to generate the list of animations....
   def transformations() : List[Matrix]

  def move(translations: List[Point]) : AbstractAnimation = {
     new AnimationList(translations.map(t=>Matrix.translate(t.x,t.y,t.z)))
  }
 
   def rotate(rotations: List[Rotation]) : AbstractAnimation = {
     new AnimationList(rotations.map(Matrix.rotate(_)))
  }

   def scale(scales: Range) : AbstractAnimation = {
        new AnimationList(scales.toList.map(Matrix.scale(_)))
  }
   //don't move for these number of scenes
   def still(noOfFrames: Int) : AbstractAnimation = {
        new AnimationList((for {i <-1 to noOfFrames } yield Matrix.identity()).toList)
  }
 
   //speed up the animation by ammount specified
  def atSpeed(speed: Int): AbstractAnimation = {
    //speed up by choppping out frames
       new AnimationList((for {i:Int <- 0 to transformations().size-1 if (i % speed == 0)} 
                                                        yield transformations()(i)).toList)
  }

  def over(frames: Int): AbstractAnimation = {
    new SubsetAnimation(this,frames)
  }
  
  def then(animation: AbstractAnimation) : AbstractAnimation = {
    new CompositeAnimation(this,animation)
  }

    // run the animations at the same time. The anmation is ran for the shortest
    // of the 2 sequences
    def and(animation: AbstractAnimation) : AbstractAnimation = {
    new ParallelAnimation(this,animation)
  }
  
  // on - apply the animation to this scene....
  def on(generator: SceneGenerator): AnimationSequence = {
     new AnimationSequence(generator,this)
  }
 
}

//------------------------------------------------------------------------
//----------- CLASSES THAT YOU USE BELOW ---------------------------------
//------------------------------------------------------------------------

//normally the starting point for the animation
case class Animation() extends AbstractAnimation {
       def transformations() : List[Matrix] = {Nil}
}  
//------------------ two sequences joined together ---------------------------  
class CompositeAnimation(animation1 : AbstractAnimation, animation2 : AbstractAnimation ) extends AbstractAnimation {
   
  override def transformations() : List[Matrix] = {
      animation1.transformations ::: animation2.transformations
  } 
}

//------------------ two sequences ran together ---------------------------  
class ParallelAnimation(animation1 : AbstractAnimation, animation2 : AbstractAnimation ) extends AbstractAnimation {
   
  override def transformations() : List[Matrix] = {
      val frames1 = animation1.transformations
      val frames2 = animation2.transformations
      val shortestLength = if(frames1.size < frames2.size) frames1.size else frames2.size
      (for {i <- 0 to shortestLength-1} yield frames1(i) * frames2(i)).toList
  } 
}


//--------------- creates a subset of animations of the ones found in the animation
class SubsetAnimation(animation : AbstractAnimation, frames: Int) extends AbstractAnimation {
  override def transformations() : List[Matrix] = { animation.transformations}
}

//----------- just a set of transforms which is a set of animations ------------
class AnimationList(animations : List[Matrix]) extends AbstractAnimation {     
  override def transformations() : List[Matrix] = animations
}

// convenience calls to make life easier creating animation

object Animation {
  def scrollMessage(text: String) : AnimationSequence = {
    //ok a little bad exposing the generator to get the witdh - better than
    //hard coding but adds a dependency. Will think of some way to abstract the constants
    //to get around the problem.
        val endx = (text.length()+1)* CharacterSceneGenerator.charWidth
       (new Animation() move (Point(CharacterSceneGenerator.charWidth,0,0) to 
                              Point(-endx,0,0))) on text
  }

}
