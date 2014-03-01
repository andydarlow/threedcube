package com.cube.model.generators
import com.cube.model._

/**
 * creates a image in some way. Core to the system. This generates an image
 * that can then be transformed to generate a animation
 * 
 * Simple generates will generate the same image for all frames (e.g. a char
 * generator will always generate the same char for every frame) other generators
 * may generate a different image for each frame. This allows for generators
 * to produce animations (which can then be transformed in 3d space)
 */
trait SceneGenerator {

  //generate the next frame in the animation sequene. The animation should
  //loop
   def nextFrame():AnimationFrame
}

object SceneGenerator {
   //makes the scripts easier to read by removing the boiler plate creation code...
  implicit def toGenerator(char: Char) : SceneGenerator = {new CharacterSceneGenerator(char)}
  implicit def toGenerator(text: String) : SceneGenerator = {new StringSceneGenerator(text)}

}