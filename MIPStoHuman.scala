//Shubhankar Singh 2019
//(Up to 15 extra-credit points.) Write a program to convert MIPS machine-language
//instructions to (somewhat) human-readable form. Such a program would take one 
//or more machine-language instructions (text strings representing either 32-bit 
//strings of 0s and 1s or 8-digit hexadecimal numbers) and display the operation 
//and the operands as a line of MIPS assembly source code. For register operands, 
//you can just give them as, e.g., $8, rather than looking up a symbolic name such 
//as $t0 (although you'll get a few more points if you do look up the symbolic name). 
//For absolute addresses you can show them as hexadecimal constants, e.g., 0x04004000. 
//Branch targets are tricky, but you could do more or less what SPIM does, which is 
//to show the byte offset from the updated program counter (i.e., the ``immediate'' 
//value from the instruction times 4).
//Here too credit will depend on how much your program does; you could make it work 
//only for a subset of the possible opcodes (probably sensible). For this one, a 
//program that handles a representative subset of instructions (say add, sub, addi,
// lw, beq, and j) would be worth 10 points.

object MIPStoHuman {
   def main(args: Array[String]) {
      println("Enter your MIPS instruction:")
      var instruction:String = scala.io.StdIn.readLine()
      println("Your instruction was " + instruction)
   }
}