//Shubhankar Singh 2019
//MIPS assembler to a text form of its binary representation

//Pledged Shubhankar Singh, This assignment is 
//entirely my own work, except that I also consulted outside course:
//https://www.eg.bucknell.edu/~csci320/mips_web/
//This source was just used heavily to validated that my program was working correctly^
//https://inst.eecs.berkeley.edu/~cs61c/resources/MIPS_Green_Sheet.pdf
//This source is just the MIPS green sheet^

/*
Problem description:
(Up to 30 extra-credit points.) Write a program to convert a line 
or lines of MIPS assembler to a text form of its binary representation. 
Such a program could range from fairly simple (only a subset of instructions, 
limited or no support for labels) to fairly complex (the equivalent of a full assembler).
Credit will depend on how much your program does; a simple program that just works for a subset of instructions 
(including at least one R-format instruction, lw and sw, beq and bne, and j) would be worth 10 points.
*/

//My program supports about 18 instructions. This is nearly half of the MIPS instructions.
//It supports common instructions from R, I, and J types.
//The program (aka assembler) can also recognize labels which took some thinking
//I focused on the MIPS instructions people are more likely to use.
//It took about 11 hours of work more or less.

object MIPStoBinary {

    //for printWriter and reading input files (file IO):
    import java.io._
    import scala.io.Source

    val r_format:Array[(String, String)] = Array(("add","100000"), ("sub","100010"), ("and","100100"), 
                                            ("or","100101"), ("nor","100111"), ("sll","000000"), 
                                            ("srl","000010"), ("slt","101010"), ("jr","001000"))

    val i_format:Array[(String, String)] = Array(("addi","001000"), ("lw","100011"), ("sw","101011"),
                                            ("andi","001100"), ("ori","001101"), ("beq","000100"),
                                            ("bne","000101"))

    val j_format:Array[(String, String)] = Array(("j","000010"), ("jal","000011"))

    val regVal:Array[(String, String)] = Array(("$zero", "00000"), ("$v0", "00010"), ("$v1", "00011"),
                                            ("$a0", "00100"), ("$a1", "00101"), ("$a2", "00110"),
                                            ("$a3", "00111"), ("$t0", "01000"), ("$t1", "01001"),
                                            ("$t2", "01010"), ("$t3", "01011"), ("$t4", "01100"),
                                            ("$t5", "01101"), ("$t6", "01110"), ("$t7", "01111"),
                                            ("$s0", "10000"), ("$s1", "10001"), ("$s2", "10010"),
                                            ("$s3", "10011"), ("$s4", "10100"), ("$s5", "10101"),
                                            ("$s6", "10110"), ("$s7", "10111"), ("$t8", "11000"),
                                            ("$t9", "11001"), ("$gp", "11100"), ("$sp", "11101"),
                                            ("$fp", "11110"), ("$ra", "11111"))
   
   def findIns(str:String, types:Array[(String, String)]):Boolean = {
       var found:Boolean = false
       for(x <- types) {
           if(x._1 == str) {
               found = true
           }
       }
       found
   }

   def returnInsBinary(str:String, types:Array[(String, String)]):String = {
       var binaryRep:String = "binary rep not found"
       for(x <- types) {
           if(x._1 == str) {
               binaryRep = x._2
           }
       }
       binaryRep
   }

   def toBinary(n:Long):String = n match {
       case 0 | 1 => s"$n"
       case _   => s"${toBinary(n/2)}${n%2}"   
    }

    def isAllDigits(x: String) = {
        var validHex:Boolean = true;
        for(c <- x) {
            if(c == 'A' || c == 'B' || c == 'C' || c == 'D' || c == 'E' || c == 'F' || c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f'){
                //valid
            } else {
                //invalid char
                if(!c.isDigit) {
                    validHex = false
                } 
            }
        }
        validHex
    }

   def returnFullInstructionBinary(instruction:String, labels:Array[(String, String)]):String = {
       //split instruction into each of its parts
      var instructionArr = instruction.split(" +")
      while(instructionArr.length < 4) {
          instructionArr = instructionArr :+ ""
      }
      //determine what type of instruction it is
      var instructionType:String = ""
      var validInstruction = true
      var skip = false;

      println(instructionArr(0))
      if(instructionArr(0).contains(":")) {
          //this means the line is a label
          //set skip to true
          skip = true;
      }

      var binary:String = ""
      
      if(!skip) {
          if(findIns(instructionArr(0), r_format)) {
            instructionType = "r"
          } else if(findIns(instructionArr(0), i_format)) {
            instructionType = "i"
          } else if(findIns(instructionArr(0), j_format)) {
            instructionType = "j"
          } else {
            println("Error: instruction type is not valid")
            validInstruction = false
          }

          //other error checking for valid instructions: would go here before parse process.

          if(validInstruction) {
            //for R type instructions
            if(instructionType == "r") {
                binary = binary + "000000"
                var zero:String = instructionArr(0)
                var one:String = instructionArr(1)
                var two:String = instructionArr(2)
                var three:String = instructionArr(3)
                if(instructionArr(0) == "jr") {
                    instructionArr(2) = returnInsBinary(instructionArr(1), regVal)
                    instructionArr(3) = "00000"
                    instructionArr(1) = "00000"
                    binary = binary + instructionArr(2) + instructionArr(3) + instructionArr(1)
                } else if(instructionArr(0) == "sll" || instructionArr(0) == "srl") {
                    instructionArr(2) = "00000"
                    instructionArr(3) = returnInsBinary(two, regVal)
                    instructionArr(1) = returnInsBinary(instructionArr(1), regVal)
                    binary = binary + instructionArr(2) + instructionArr(3) + instructionArr(1)
                } else {
                    binary = binary + returnInsBinary(two, regVal) + returnInsBinary(three, regVal) + returnInsBinary(one, regVal)
                }

                if(instructionArr(0) == "sll" || instructionArr(0) == "srl") {
                    var saVal = toBinary(java.lang.Long.decode(three))
                    var sa:String = "0"*(5-saVal.length) + saVal
                    sa = sa.takeRight(5)
                    binary = binary + sa
                } else {
                    binary = binary + "00000"
                }
                
                binary = binary + returnInsBinary(zero, r_format)
            } 
            //for I type instructions
            if(instructionType == "i") {
                binary = binary + returnInsBinary(instructionArr(0), i_format)
                if(instructionArr(0) == "lw" || instructionArr(0) == "sw") {
                    var optwo = instructionArr(2).split("\\(")
                    instructionArr(3) = optwo(0)
                    instructionArr(2) = optwo(1).dropRight(1)
                    
                    var temp = instructionArr(2)
                    instructionArr(2) = instructionArr(1)
                    instructionArr(1) = temp
                }
                if(instructionArr(0) == "addi" || instructionArr(0) == "andi" || instructionArr(0) == "ori") {
                    var temp = instructionArr(2)
                    instructionArr(2) = instructionArr(1)
                    instructionArr(1) = temp
                }
                binary = binary + returnInsBinary(instructionArr(1), regVal) + returnInsBinary(instructionArr(2), regVal)                //search through and see if instructionArr(3) is in the labels, because
                //that means I need to substitute the label for it's absolute address 
                //which I already have stored in labels. *will re-use returnInsBinary*
                if(!isAllDigits(instructionArr(3).replace("x", ""))) {
                    instructionArr(3) = returnInsBinary(instructionArr(3) + ":", labels)
                    println("******* label addressed")
                }
                var binVal = toBinary(java.lang.Long.decode(instructionArr(3)))
                var imm:String = "0"*(16 - binVal.length) + binVal
                imm = imm.takeRight(16)
                binary = binary + imm
                
            }
            //for J type instructions
            if(instructionType == "j") {
                binary = binary + returnInsBinary(instructionArr(0), j_format)
                //that means I need to substitute the label for it's absolute address 
                //which I already have stored in labels. *will re-use returnInsBinary*
                if(!isAllDigits(instructionArr(1).replace("x", ""))) {
                    instructionArr(1) = returnInsBinary(instructionArr(1) + ":", labels)
                    println("******* label addressed")
                }
                var binVal = toBinary(java.lang.Long.decode(instructionArr(1)))
                var target:String = "0"*(26 - binVal.length) + binVal
                target = target.takeRight(26)
                binary = binary + target
            }
        } else {
            binary = "Error: invalid instruction";
        }
      }

      binary
   }

   def findLabels(lines:List[String]):Array[(String, String)] = {
      //find all labels and store their memory address
      //according to homework 4:
      /*
      "Since programs in this simulator always have their 
      text segments at 0x00400024 () and their data segments 
      at 0x10010000, absolute addresses into either segment 
      can be based on these values."
      */
      var labelAddresses:Array[(String, String)] = new Array[(String, String)](lines.length)
      var index = 0;
      for(line <- lines) {
          if(line.contains(":")) {
              var cleanLine = line.replace(" ", "");
              labelAddresses(index) = (cleanLine, "0x" + (index*4 + 4194340).toHexString)
          } else {
              labelAddresses(index) = ("no_label", "0x0000")
          }
          index = index + 1;
      }
      //now labelAddresses array should be populated with the 
      //correct labels and their corresponding absolute addresses
      var anyLabels:Boolean = false
      for(x <- labelAddresses) {
          if(!x._1.contains("no_label")) {
              anyLabels = true
              println(x._1 + " : " + x._2)
          }     
      }
      if(!anyLabels) {
          println("no labels found")
      }
      labelAddresses
   }

   def main(args: Array[String]) {
      println()
      print("input file name: ")
      var inputFile:String = scala.io.StdIn.readLine()
      println()
      println("Your input file was " + inputFile)
      println()

      println("LABELS -------------------")
      val lines = Source.fromFile(inputFile).getLines.toList
      var labelAddresses = findLabels(lines)
      
      println()
      println("INSTRUCTIONS -------------")
      //now that I have the labels absolute memory address,
      //I can convert mips code to machine language below
      //and save to the output file
      val pw = new PrintWriter(new File("output_" + inputFile))
      for(line <- lines) {
          var cleanLine:String = line.replace(",", "");
          pw.write(returnFullInstructionBinary(cleanLine, labelAddresses) + "\n")
      }
      pw.close

      println()
      println("The output is saved to: output_" + inputFile)
      println()
   }

}