// push constant 7
// D = 7
@7
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
