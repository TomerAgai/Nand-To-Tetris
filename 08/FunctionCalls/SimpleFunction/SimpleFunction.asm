@256
D=A
@SP
M=D
// push returnAddr
@SysInit0
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
@SysInit0
0;JMP
(SysInit0)
// function SimpleFunction.test 2
(SimpleFunction.test)
// push 0 : 2 times
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
// push local 0
// addr = LCL 0
@0
D=A
@LCL
A=D+M
D=M
// RAM[SP] = RAM[addr]
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push local 1
// addr = LCL 1
@1
D=A
@LCL
A=D+M
D=M
// RAM[SP] = RAM[addr]
@SP
A=M
M=D
// SP++
@SP
M=M+1
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
// not
@SP
A=M-1
M=!M
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
@SP
AM=M-1
D=M
@ARG
A=M
M=D
// SP = ARG + 1
@ARG
D=M+1
@SP
M=D
// restore THAT THIS ARG and LCL of the caller
@R14
AM=M-1
D=M
@THAT
M=D
@R14
AM=M-1
D=M
@THIS
M=D
@R14
AM=M-1
D=M
@ARG
M=D
@R14
AM=M-1
D=M
@LCL
M=D
@R15
A=M
0;JMP
