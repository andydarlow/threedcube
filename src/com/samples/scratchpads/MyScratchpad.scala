package com.samples.scratchpads

object MyScratchpad {
  def main(args : Array[String]) : Unit = {
    def printit(params : String *)  = {
      params.foreach(println(_))
    }
    
    val matches = "marius" :: "tyler" :: "derek" :: "dave" :: "jorge":: "viktor" ::Nil  
    printit(matches : _*)
    
  }
}
