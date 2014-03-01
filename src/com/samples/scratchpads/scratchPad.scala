package com.samples.scratchpads

object scratchPad {
  def main(args : Array[String]) : Unit = {
      def fred(x:Int)(y:Int) = println("hello "+ x + " " + y)
      println(fred(1)(2))
      val f = fred (2) _ 
      println(f)
    
  }
}
