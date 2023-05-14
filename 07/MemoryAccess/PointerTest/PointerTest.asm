// push constant 3030
// D = 3030
@3030
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// pop pointer 0
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[THIS] = RAM[SP]
@SP
M=M-1
@THIS
M=D
// push constant 3040
// D = 3040
@3040
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// pop pointer 1
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[THAT] = RAM[SP]
@SP
M=M-1
@THAT
M=D
// push constant 32
// D = 32
@32
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// pop this 2
// addr = THIS 2
@2
D=A
@THIS
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
// push constant 46
// D = 46
@46
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// pop that 6
// addr = THAT 6
@6
D=A
@THAT
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
// push pointer 0
// D = Ram[THIS]
@THIS
D=M
// RAM[SP] = RAM[THIS]
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push pointer 1
// D = Ram[THAT]
@THAT
D=M
// RAM[SP] = RAM[THAT]
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
// push this 2
// addr = THIS 2
@2
D=A
@THIS
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
// push that 6
// addr = THAT 6
@6
D=A
@THAT
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
