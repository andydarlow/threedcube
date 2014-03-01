package com.cube.model.animationdsl
import com.cube.model._
import com.cube.model.generators._

/**
 * this is a sequence of frames in the animation
 * The class uses a  set of animations (basically transforms) and animates
 * the objects the scene to generate frames of animation
 */
class AnimationSequence(generator: SceneGenerator, animation : AbstractAnimation) {

  //play the animation to get an animation sequence (list of frames)
  def play() : List[AnimationFrame] = {
     val transformations = animation.transformations
     transformations.map(matrix => matrix.transform(generator.nextFrame()))
  }
}
