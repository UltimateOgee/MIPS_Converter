# CS23//Shubhankar Singh 2019


MIPStoBinary:
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
//It took about 11 hours of work more or less.21_2019_Singh
