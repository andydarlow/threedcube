package com.cube.marshall

//represents a sequence of bits of arbiturary length. Assumes that the word
//is a multiple of 8 bits
 // onPositions = bits set to 1 in the word 
class BigWord(onPositions : List[Int], bitsInWord : Int ) {
  
  val bitsInAByte = 8
  
  // create 8 bit bytes from the data held in this word
  def getBytes() : List[Int] = {
       val bytes = new Range(0,bitsInWord.toInt-1,bitsInAByte).map(i => (i to i+bitsInAByte-1)) //which bits are in what bytes...
       bytes.map(createByte(_)).toList // work out the bytes from the bits
  }
   //create a byte (as an Int number). The bitByte range tells you what bits are in the byte
   // A byte will be generated from all the bits that fit into in the byte specified by theByte
  private def createByte(theByte: Range) : Int = {
        val bitsSetInByte = theByte.filter(onPositions.contains(_))
                                .map(bitPos => bitPos -theByte(0)).toList            //map to 0-8 (bit pos in byte)
        bitsSetInByte.map(bit=> (1<<bit)).foldLeft(0)((sum,bitNumber)=> sum+bitNumber)  // turn to a byte
  }  
}
