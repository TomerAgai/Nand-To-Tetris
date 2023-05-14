// push constant 111
// D = 111
@111
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push constant 333
// D = 333
@333
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push constant 888
// D = 888
@888
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// pop static 8
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[16+i] = RAM[SP]
@SP
M=M-1
@StaticTest.8
M=D
// pop static 3
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[16+i] = RAM[SP]
@SP
M=M-1
@StaticTest.3
M=D
// pop static 1
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[16+i] = RAM[SP]
@SP
M=M-1
@StaticTest.1
M=D
// push static 3
// D = RAM[16+i]
@StaticTest.3
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
@StaticTest.1
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
// push static 8
// D = RAM[16+i]
@StaticTest.8
D=M
// RAM[SP] = RAM[16+i] (static location)
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
