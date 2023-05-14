@256
D=A
@SP
M=D
// push returnAddr
@retSys.init
D=A
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push LCL
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
// push ARG
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
// push THIS
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
// push THAT
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
// ARG = SP - 5 - Nargs
@SP
D=M
@5
D=D-A
@0
D=D-A
@ARG
M=D
// LCL = SP
@SP
D=M
@LCL
M=D
@Sys.init
0;JMP
(retSys.init)
// function Main.fibonacci 0
(Main.fibonacci)
// push 0 : 0 times
// push argument 0
// addr = ARG 0
@0
D=A
@ARG
A=D+M
D=M
// RAM[SP] = RAM[addr]
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push constant 2
// D = 2
@2
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// lt
@SP
A=M-1
D=M
A=A-1
// X-Y
D=M-D
@SMALLER3
D;JLT
@NOTSMALLER3
0;JMP
// X<Y 
(SMALLER3)
D=-1
@LT3
0;JMP
// X>=Y 
(NOTSMALLER3)
D=0
@LT3
0;JMP
(LT3)
@SP
A=M-1
A=A-1
M=D
// SP--
@SP
M=M-1
// if-goto IF_TRUE
@SP
AM=M-1
D=M
@IF_TRUE
D;JNE
// goto IF_FALSE
@IF_FALSE
0;JMP
// label IF_TRUE
(IF_TRUE)
// push argument 0
// addr = ARG 0
@0
D=A
@ARG
A=D+M
D=M
// RAM[SP] = RAM[addr]
@SP
A=M
M=D
// SP++
@SP
M=M+1
// return
// endFrame = LCL (save in R14)
@LCL
D=M
@R14
M=D
// retAddr = *(endFrame - 5) (save in R15)
@5
A=D-A
D=M
@R15
M=D
// *ARG = pop()
// addr = ARG 0
@0
D=A
@ARG
D=D+M
// R13 = addr
@R13
M=D
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[addr] = RAM[SP] (saved in R13)
@SP
M=M-1
@R13
A=M
M=D
// SP = ARG + 1
@ARG
D=M
@SP
M=D+1
// restore THAT THIS ARG and LCL of the caller
@R14
D=M-1
AM=D
D=M
@THAT
M=D
@R14
D=M-1
AM=D
D=M
@THIS
M=D
@R14
D=M-1
AM=D
D=M
@ARG
M=D
@R14
D=M-1
AM=D
D=M
@LCL
M=D
@R15
A=M
0;JMP
// label IF_FALSE
(IF_FALSE)
// push argument 0
// addr = ARG 0
@0
D=A
@ARG
A=D+M
D=M
// RAM[SP] = RAM[addr]
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push constant 2
// D = 2
@2
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// sub
@SP
A=M-1
// D = RAM[SP] 
D=M
// RAM[SP-1] = RAM[SP-1] - D
A=A-1
M=M-D
// SP--
@SP
M=M-1
// call Main.fibonacci 1
// push returnAddr
@retMain.fibonacci13
D=A
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push LCL
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
// push ARG
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
// push THIS
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
// push THAT
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
// ARG = SP - 5 - Nargs
@SP
D=M
@5
D=D-A
@1
D=D-A
@ARG
M=D
// LCL = SP
@SP
D=M
@LCL
M=D
@Main.fibonacci
0;JMP
(retMain.fibonacci13)
// push argument 0
// addr = ARG 0
@0
D=A
@ARG
A=D+M
D=M
// RAM[SP] = RAM[addr]
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push constant 1
// D = 1
@1
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// sub
@SP
A=M-1
// D = RAM[SP] 
D=M
// RAM[SP-1] = RAM[SP-1] - D
A=A-1
M=M-D
// SP--
@SP
M=M-1
// call Main.fibonacci 1
// push returnAddr
@retMain.fibonacci17
D=A
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push LCL
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
// push ARG
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
// push THIS
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
// push THAT
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
// ARG = SP - 5 - Nargs
@SP
D=M
@5
D=D-A
@1
D=D-A
@ARG
M=D
// LCL = SP
@SP
D=M
@LCL
M=D
@Main.fibonacci
0;JMP
(retMain.fibonacci17)
// add
@SP
A=M-1
// D = RAM[SP] 
D=M
// RAM[SP-1] = RAM[SP-1] + D
A=A-1
M=M+D
// SP--
@SP
M=M-1
// return
// endFrame = LCL (save in R14)
@LCL
D=M
@R14
M=D
// retAddr = *(endFrame - 5) (save in R15)
@5
A=D-A
D=M
@R15
M=D
// *ARG = pop()
// addr = ARG 0
@0
D=A
@ARG
D=D+M
// R13 = addr
@R13
M=D
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[addr] = RAM[SP] (saved in R13)
@SP
M=M-1
@R13
A=M
M=D
// SP = ARG + 1
@ARG
D=M
@SP
M=D+1
// restore THAT THIS ARG and LCL of the caller
@R14
D=M-1
AM=D
D=M
@THAT
M=D
@R14
D=M-1
AM=D
D=M
@THIS
M=D
@R14
D=M-1
AM=D
D=M
@ARG
M=D
@R14
D=M-1
AM=D
D=M
@LCL
M=D
@R15
A=M
0;JMP
// function Sys.init 0
(Sys.init)
// push 0 : 0 times
// push constant 4
// D = 4
@4
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// call Main.fibonacci 1
// push returnAddr
@retMain.fibonacci22
D=A
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push LCL
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
// push ARG
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
// push THIS
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
// push THAT
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
// ARG = SP - 5 - Nargs
@SP
D=M
@5
D=D-A
@1
D=D-A
@ARG
M=D
// LCL = SP
@SP
D=M
@LCL
M=D
@Main.fibonacci
0;JMP
(retMain.fibonacci22)
// label WHILE
(WHILE)
// goto WHILE
@WHILE
0;JMP
