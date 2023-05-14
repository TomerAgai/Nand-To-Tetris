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
// function Class1.set 0
(Class1.set)
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
// pop static 0
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[16+i] = RAM[SP]
@SP
M=M-1
@Class1.0
M=D
// push argument 1
// addr = ARG 1
@1
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
// pop static 1
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[16+i] = RAM[SP]
@SP
M=M-1
@Class1.1
M=D
// push constant 0
// D = 0
@0
D=A
// RAM[SP] = D
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
// function Class1.get 0
(Class1.get)
// push 0 : 0 times
// push static 0
// D = RAM[16+i]
@Class1.0
D=M
// RAM[SP] = RAM[16+i] (static location)
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push static 1
// D = RAM[16+i]
@Class1.1
D=M
// RAM[SP] = RAM[16+i] (static location)
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
// push constant 6
// D = 6
@6
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push constant 8
// D = 8
@8
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// call Class1.set 2
// push returnAddr
@retClass1.set15
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
@2
D=D-A
@ARG
M=D
// LCL = SP
@SP
D=M
@LCL
M=D
@Class1.set
0;JMP
(retClass1.set15)
// pop temp 0
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[5+i] = RAM[SP]
@SP
M=M-1
@5
M=D
// push constant 23
// D = 23
@23
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push constant 15
// D = 15
@15
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// call Class2.set 2
// push returnAddr
@retClass2.set19
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
@2
D=D-A
@ARG
M=D
// LCL = SP
@SP
D=M
@LCL
M=D
@Class2.set
0;JMP
(retClass2.set19)
// pop temp 0
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[5+i] = RAM[SP]
@SP
M=M-1
@5
M=D
// call Class1.get 0
// push returnAddr
@retClass1.get21
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
@Class1.get
0;JMP
(retClass1.get21)
// call Class2.get 0
// push returnAddr
@retClass2.get22
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
@Class2.get
0;JMP
(retClass2.get22)
// label WHILE
(WHILE)
// goto WHILE
@WHILE
0;JMP
// function Class2.set 0
(Class2.set)
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
// pop static 0
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[16+i] = RAM[SP]
@SP
M=M-1
@Class2.0
M=D
// push argument 1
// addr = ARG 1
@1
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
// pop static 1
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[16+i] = RAM[SP]
@SP
M=M-1
@Class2.1
M=D
// push constant 0
// D = 0
@0
D=A
// RAM[SP] = D
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
// function Class2.get 0
(Class2.get)
// push 0 : 0 times
// push static 0
// D = RAM[16+i]
@Class2.0
D=M
// RAM[SP] = RAM[16+i] (static location)
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push static 1
// D = RAM[16+i]
@Class2.1
D=M
// RAM[SP] = RAM[16+i] (static location)
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
