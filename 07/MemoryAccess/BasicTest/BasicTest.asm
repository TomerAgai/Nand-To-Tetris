// push constant 10
// D = 10
@10
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// pop local 0
// addr = LCL 0
@0
D=A
@LCL
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
// push constant 21
// D = 21
@21
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push constant 22
// D = 22
@22
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// pop argument 2
// addr = ARG 2
@2
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
// pop argument 1
// addr = ARG 1
@1
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
// push constant 36
// D = 36
@36
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// pop this 6
// addr = THIS 6
@6
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
// push constant 42
// D = 42
@42
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// push constant 45
// D = 45
@45
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// pop that 5
// addr = THAT 5
@5
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
// pop that 2
// addr = THAT 2
@2
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
// push constant 510
// D = 510
@510
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// pop temp 6
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[5+i] = RAM[SP]
@SP
M=M-1
@11
M=D
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
// push that 5
// addr = THAT 5
@5
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
// push this 6
// addr = THIS 6
@6
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
// push this 6
// addr = THIS 6
@6
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
// push temp 6
// RAM[SP] = RAM[5+i] (temp loaction) 
@11
D=M
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
