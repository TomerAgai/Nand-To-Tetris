// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

(START)
//set i for number of bits needed to be painted
    @8191
    D=A
    @i
    M=D
//check the keyboard 
    @KBD
    D=M
    @LOOPWHITE
    D;JEQ //if nothing is pressed goto LOOPWHITE (D=0)
    @LOOPBLACK
    0;JMP //else goto LOOPBLACK


//make the screen black
(LOOPBLACK)   
//paint one bit 
    @i
    D=M
    @SCREEN
    A=A+D
    M=-1
//i = i - 1 
    @i
    M=M-1
    D=M
//if i >= 0 continue, else painting is finished
    @LOOPBLACK
    D;JGE
    @START
    0;JMP


//make the screen white
(LOOPWHITE)   
//paint one bit
    @i
    D=M
    @SCREEN
    A=A+D
    M=0
//i = i - 1 
    @i
    M=M-1
    D=M
//if i >= 0 continue, else painting is finished
    @LOOPWHITE
    D;JGE
    @START
    0;JMP