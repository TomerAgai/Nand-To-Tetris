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
// function Sys.init 0
(Sys.init)
// push 0 : 0 times
// push constant 4000
// D = 4000
@4000
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
// push constant 5000
// D = 5000
@5000
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
// call Sys.main 0
// push returnAddr
@retSys.main5
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
@Sys.main
0;JMP
(retSys.main5)
// pop temp 1
// D = RAM[SP]
@SP
A=M-1
D=M
// RAM[5+i] = RAM[SP]
@SP
M=M-1
@6
M=D
// label LOOP
(LOOP)
// goto LOOP
@LOOP
0;JMP
// function Sys.main 5
(Sys.main)
// push 0 : 5 times
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
// push constant 4001
// D = 4001
@4001
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
// push constant 5001
// D = 5001
@5001
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
// push constant 200
// D = 200
@200
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// pop local 1
// addr = LCL 1
@1
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
// push constant 40
// D = 40
@40
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// pop local 2
// addr = LCL 2
@2
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
// pop local 3
// addr = LCL 3
@3
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
// push constant 123
// D = 123
@123
D=A
// RAM[SP] = D
@SP
A=M
M=D
// SP++
@SP
M=M+1
// call Sys.add12 1
// push returnAddr
@retSys.add1221
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
@Sys.add12
0;JMP
(retSys.add1221)
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
// push local 2
// addr = LCL 2
@2
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
// push local 3
// addr = LCL 3
@3
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
// push local 4
// addr = LCL 4
@4
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
// function Sys.add12 0
(Sys.add12)
// push 0 : 0 times
// push constant 4002
// D = 4002
@4002
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
// push constant 5002
// D = 5002
@5002
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
// push constant 12
// D = 12
@12
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
