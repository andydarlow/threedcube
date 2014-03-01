package com.cube.model.animationdsl

import com.cube.model.AnimationFrame

//a movie that is played on our cube (which is a sequence of animations). Note
//the the movie may play out in in a location that isn't visible to the observer
//(doesn't map to a led in the cube)
// a move is created dynamically from a list of animation sequences. 
case class Movie(frames : List[AnimationSequence]) {
  
  //play this movie to get the frames of animations
  def play() : List[AnimationFrame] = frames.flatMap(_.play()) 
}