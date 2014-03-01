package com.cube.model.generators

import com.cube.model.AnimationFrame
import com.cube.model.Point

/**
 * generate a scene containing the string passed in to the constructor. The
 * String's first character is centered at 0,0
 */
class StringSceneGenerator(text : String)  extends SceneGenerator {

  lazy val textAsFrame = {
          //effective a seq of chars - each char shifted to the right of the one before.,,
		  def moveChars(charPosition : Int, aString: List[Char]): List[Point] =  aString match {  
		  			case aChar :: rest => CharacterSceneGenerator.convertToPoints(aChar)
		  							      .map(p => p.translate(charPosition*CharacterSceneGenerator.charWidth,0,0)) :::
		  							      moveChars(charPosition+1,rest)
		  			case Nil  => Nil
		  		}
		  		new AnimationFrame(moveChars(0,text.toList)) 
  			}

  def nextFrame(): AnimationFrame = textAsFrame
  
}
