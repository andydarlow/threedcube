package com.cube.model

/**
 * frame of animation. 1 still image in the animation sequence. This is
 * a frame in the real world, not the cube's world (which is restricted to XxXxX dimensions)
 */
case class AnimationFrame(points: List[Point]) 
