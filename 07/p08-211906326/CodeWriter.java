import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    protected BufferedWriter writer;
    protected String fileName;
    // static counter of symbolic variables
    protected static int symbolCount = 0;

    public CodeWriter(File target) {
        try {
            writer = new BufferedWriter(new FileWriter(target));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // informs the codeWriter that a new file starts to be translated
    public void setFileName(File file) {
        fileName = file.getName();
    }

    // write the asm instructions that initialize the VM
    // must be placed in the beginning of the .asm file
    public void writeInit() {
        try {
            writer.write("@256" + "\n");
            writer.write("D=A" + "\n");
            writer.write("@SP" + "\n");
            writer.write("M=D" + "\n");
            writeCall("Sys.init", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // writes assembly code that effects the label command
    public void writeLabel(String label) {
        try {
            writer.write("(" + label + ")" + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeGoTo(String label) {
        try {
            writer.write("@" + label + "\n");
            writer.write("0;JMP" + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // writes assembly code that effects the if-goto command
    public void writeIfGoTo(String label) {
        try {
            writer.write("@SP" + "\n");
            writer.write("AM=M-1" + "\n");
            writer.write("D=M" + "\n");
            writer.write("@" + label + "\n");
            // if condition is true we get -1, JNE jumps if the value is not equal to zero
            // (when we get true)
            writer.write("D;JNE" + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // writes assembly code that effects the call to function command
    public void writeCall(String funcName, int args) {
        try {
            String addr;
            writer.write("// push returnAddr" + "\n");
            if (symbolCount == 0)
                addr = "ret" + funcName;
            else {
                addr = "ret" + funcName + symbolCount;
            }
            writer.write("@" + addr + "\n");
            writer.write("D=A" + "\n");
            writer.write("@SP" + "\n");
            writer.write("A=M" + "\n");
            writer.write("M=D" + "\n");
            writer.write("// SP++" + "\n");
            writer.write("@SP" + "\n");
            writer.write("M=M+1" + "\n");
            String[] segs = { "LCL", "ARG", "THIS", "THAT" };
            // push lcl arg this and that to the stack
            for (String seg : segs) {
                writer.write("// push " + seg + "\n");
                writer.write("@" + seg + "\n");
                writer.write("D=M" + "\n");
                writer.write("@SP" + "\n");
                writer.write("A=M" + "\n");
                writer.write("M=D" + "\n");
                writer.write("@SP" + "\n");
                writer.write("M=M+1" + "\n");
            }
            // ARG = SP - 5 - Nargs
            writer.write("// ARG = SP - 5 - Nargs" + "\n");
            writer.write("@SP" + "\n");
            writer.write("D=M" + "\n");
            writer.write("@5" + "\n");
            writer.write("D=D-A" + "\n");
            writer.write("@" + args + "\n");
            writer.write("D=D-A" + "\n");
            writer.write("@ARG" + "\n");
            writer.write("M=D" + "\n");
            // LCL = SP
            writer.write("// LCL = SP" + "\n");
            writer.write("@SP" + "\n");
            writer.write("D=M" + "\n");
            writer.write("@LCL" + "\n");
            writer.write("M=D" + "\n");
            // goto funcion name
            writeGoTo(funcName);/////////////////////////////////////////////////// functionName?????
            // write (funcName) labeled
            writeLabel(addr);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // writes assembly code that effects the function command
    public void writeFunction(String funcName, int args) {
        try {
            writeLabel(funcName);
            writer.write("// push 0 : " + args + " times\n");
            for (int i = 0; i < args; i++) {
                WritePushPop("push", "constant", 0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // writes assembly code that effects the return command
    public void writeReturn() {
        try {
            // endFrame = LCL (save in R14)
            writer.write("// endFrame = LCL (save in R14)" + "\n");
            writer.write("@LCL" + "\n");
            writer.write("D=M" + "\n");
            writer.write("@R14" + "\n");
            writer.write("M=D" + "\n");
            // retAddr = *(endFrame - 5) (save in R15)
            writer.write("// retAddr = *(endFrame - 5) (save in R15)" + "\n");
            writer.write("@5" + "\n");
            writer.write("A=D-A" + "\n");
            writer.write("D=M" + "\n");
            writer.write("@R15" + "\n");
            writer.write("M=D" + "\n");
            // *ARG = pop()
            writer.write("// *ARG = pop()" + "\n");
            WritePushPop("pop", "argument", 0);
            // SP = ARG + 1
            writer.write("// SP = ARG + 1" + "\n");
            writer.write("@ARG" + "\n");
            writer.write("D=M" + "\n");
            writer.write("@SP" + "\n");
            writer.write("M=D+1" + "\n");
            // restore THAT THIS ARG and LCL of the caller
            writer.write("// restore THAT THIS ARG and LCL of the caller" + "\n");
            String[] segs = { "THAT", "THIS", "ARG", "LCL" };
            for (String seg : segs) {
                writer.write("@R14" + "\n");
                writer.write("D=M-1" + "\n");
                writer.write("AM=D" + "\n");
                writer.write("D=M" + "\n");
                writer.write("@" + seg + "\n");
                writer.write("M=D" + "\n");
            }
            // goto retAddr (saved in R15)
            writer.write("@R15" + "\n");
            writer.write("A=M" + "\n");
            writer.write("0;JMP" + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // writes to output file the assembly code that implements the given Arithmetic
    // command
    public void WriteArithmetic(String line) {
        try {
            // go back to the recent SP
            writer.write("@SP" + "\n");
            writer.write("A=M-1" + "\n");
            switch (line) {
                case "add":
                case "sub":
                    String oper = (line.contains("add")) ? "+" : "-";
                    writer.write("// D = RAM[SP] " + "\n");
                    writer.write("D=M" + "\n");
                    writer.write("// RAM[SP-1] = RAM[SP-1] " + oper + " D" + "\n");
                    writer.write("A=A-1" + "\n");
                    writer.write("M=M" + oper + "D" + "\n");
                    writer.write("// SP--" + "\n");
                    writer.write("@SP" + "\n");
                    writer.write("M=M-1" + "\n");
                    break;

                case "neg":
                    writer.write("M=-M" + "\n");
                    break;
                case "and":
                case "or":
                    String op = (line.contains("and")) ? "&" : "|";
                    writer.write("D=M" + "\n");
                    writer.write("// RAM[SP] " + op + " RAM[SP-1] " + "\n");
                    writer.write("A=A-1" + "\n");
                    writer.write("M=D" + op + "M" + "\n");
                    writer.write("// SP--" + "\n");
                    writer.write("@SP" + "\n");
                    writer.write("M=M-1" + "\n");
                    break;

                case "not":
                    writer.write("M=!M" + "\n");
                    break;

                case "eq":
                    writer.write("D=M" + "\n");
                    writer.write("A=A-1" + "\n");
                    writer.write("D=D-M" + "\n");
                    writer.write("// X-Y = 0 " + "\n");
                    writer.write("@ZERO" + symbolCount + "\n");
                    writer.write("D;JEQ" + "\n");
                    writer.write("@NOTZERO" + symbolCount + "\n");
                    writer.write("0;JMP" + "\n");

                    writer.write("// X=Y " + "\n");
                    writer.write("(ZERO" + symbolCount + ")" + "\n");
                    writer.write("D=-1" + "\n");
                    writer.write("@EQ" + symbolCount + "\n");
                    writer.write("0;JMP" + "\n");

                    writer.write("// X!=Y " + "\n");
                    writer.write("(NOTZERO" + symbolCount + ")" + "\n");
                    writer.write("D=0" + "\n");
                    writer.write("@EQ" + symbolCount + "\n");
                    writer.write("0;JMP" + "\n");

                    writer.write("(EQ" + symbolCount + ")" + "\n");
                    writer.write("@SP" + "\n");
                    writer.write("A=M-1" + "\n");
                    writer.write("A=A-1" + "\n");
                    writer.write("M=D" + "\n");

                    writer.write("// SP--" + "\n");
                    writer.write("@SP" + "\n");
                    writer.write("M=M-1" + "\n");
                    break;

                case "gt":
                    writer.write("D=M" + "\n");
                    writer.write("A=A-1" + "\n");
                    writer.write("// X-Y" + "\n");
                    writer.write("D=M-D" + "\n");
                    writer.write("@GREATER" + symbolCount + "\n");
                    writer.write("D;JGT" + "\n");
                    writer.write("@NOTGREATER" + symbolCount + "\n");
                    writer.write("0;JMP" + "\n");

                    writer.write("// X>Y " + "\n");
                    writer.write("(GREATER" + symbolCount + ")" + "\n");
                    writer.write("D=-1" + "\n");
                    writer.write("@GT" + symbolCount + "\n");
                    writer.write("0;JMP" + "\n");

                    writer.write("// X<=Y " + "\n");
                    writer.write("(NOTGREATER" + symbolCount + ")" + "\n");
                    writer.write("D=0" + "\n");
                    writer.write("@GT" + symbolCount + "\n");
                    writer.write("0;JMP" + "\n");

                    writer.write("(GT" + symbolCount + ")" + "\n");
                    writer.write("@SP" + "\n");
                    writer.write("A=M-1" + "\n");
                    writer.write("A=A-1" + "\n");
                    writer.write("M=D" + "\n");

                    writer.write("// SP--" + "\n");
                    writer.write("@SP" + "\n");
                    writer.write("M=M-1" + "\n");
                    break;

                case "lt":
                    writer.write("D=M" + "\n");
                    writer.write("A=A-1" + "\n");
                    writer.write("// X-Y" + "\n");
                    writer.write("D=M-D" + "\n");
                    writer.write("@SMALLER" + symbolCount + "\n");
                    writer.write("D;JLT" + "\n");
                    writer.write("@NOTSMALLER" + symbolCount + "\n");
                    writer.write("0;JMP" + "\n");

                    writer.write("// X<Y " + "\n");
                    writer.write("(SMALLER" + symbolCount + ")" + "\n");
                    writer.write("D=-1" + "\n");
                    writer.write("@LT" + symbolCount + "\n");
                    writer.write("0;JMP" + "\n");

                    writer.write("// X>=Y " + "\n");
                    writer.write("(NOTSMALLER" + symbolCount + ")" + "\n");
                    writer.write("D=0" + "\n");
                    writer.write("@LT" + symbolCount + "\n");
                    writer.write("0;JMP" + "\n");

                    writer.write("(LT" + symbolCount + ")" + "\n");
                    writer.write("@SP" + "\n");
                    writer.write("A=M-1" + "\n");
                    writer.write("A=A-1" + "\n");
                    writer.write("M=D" + "\n");

                    writer.write("// SP--" + "\n");
                    writer.write("@SP" + "\n");
                    writer.write("M=M-1" + "\n");
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // writes to output file the assembly code that implements the given push or pop
    // command
    public void WritePushPop(String cmd, String seg, int index) {
        try {
            // PUSH command
            if (cmd == "push") {
                switch (seg) {
                    case ("constant"):

                        writer.write("// D = " + index + "\n");
                        writer.write("@" + index + "\n");
                        writer.write("D=A" + "\n");
                        writer.write("// RAM[SP] = D" + "\n");
                        writer.write("@SP" + "\n");
                        writer.write("A=M" + "\n");
                        writer.write("M=D" + "\n");
                        break;

                    case ("static"):

                        writer.write("// D = RAM[16+i]" + "\n");
                        writer.write("@" + Parser.sourceName + "." + index + "\n");
                        writer.write("D=M" + "\n");
                        writer.write("// RAM[SP] = RAM[16+i] (static location)" + "\n");
                        writer.write("@SP" + "\n");
                        writer.write("A=M" + "\n");
                        writer.write("M=D" + "\n");
                        break;

                    case ("temp"):

                        writer.write("// RAM[SP] = RAM[5+i] (temp loaction) " + "\n");
                        writer.write("@" + (index + 5) + "\n");
                        writer.write("D=M" + "\n");
                        writer.write("@SP" + "\n");
                        writer.write("A=M" + "\n");
                        writer.write("M=D" + "\n");
                        break;

                    case ("pointer"):
                        String s = "";
                        if (index == 0) {
                            s = "THIS";
                        } else
                            s = "THAT";
                        writer.write("// D = Ram[" + s + "]\n");
                        writer.write("@" + s + "\n");
                        writer.write("D=M" + "\n");
                        writer.write("// RAM[SP] = RAM[" + s + "]\n");
                        writer.write("@SP" + "\n");
                        writer.write("A=M" + "\n");
                        writer.write("M=D" + "\n");
                        break;

                    default: // local argument this and that
                        seg = segmentPointer(seg);
                        writer.write("// addr = " + seg + " " + index + "\n");
                        writer.write("@" + index + "\n");
                        writer.write("D=A" + "\n");
                        writer.write("@" + seg + "\n");
                        writer.write("A=D+M" + "\n");
                        writer.write("D=M" + "\n");
                        writer.write("// RAM[SP] = RAM[addr]" + "\n");
                        writer.write("@SP" + "\n");
                        writer.write("A=M" + "\n");
                        writer.write("M=D" + "\n");
                }

                writer.write("// SP++" + "\n");
                writer.write("@SP" + "\n");
                writer.write("M=M+1" + "\n");

            }
            // POP command
            else {
                switch (seg) {
                    case ("static"):
                        writer.write("// D = RAM[SP]" + "\n");
                        writer.write("@SP" + "\n");
                        writer.write("A=M-1" + "\n");
                        writer.write("D=M" + "\n");

                        writer.write("// RAM[16+i] = RAM[SP]" + "\n");
                        writer.write("@SP" + "\n");
                        writer.write("M=M-1" + "\n");
                        writer.write("@" + Parser.sourceName + "." + index + "\n");
                        writer.write("M=D" + "\n");
                        break;

                    case ("temp"):
                        writer.write("// D = RAM[SP]" + "\n");
                        writer.write("@SP" + "\n");
                        writer.write("A=M-1" + "\n");
                        writer.write("D=M" + "\n");

                        writer.write("// RAM[5+i] = RAM[SP]" + "\n");
                        writer.write("@SP" + "\n");
                        writer.write("M=M-1" + "\n");
                        writer.write("@" + (index + 5) + "\n");
                        writer.write("M=D" + "\n");
                        break;

                    case ("pointer"):
                        String s = "";
                        if (index == 0) {
                            s = "THIS";
                        } else
                            s = "THAT";
                        writer.write("// D = RAM[SP]" + "\n");
                        writer.write("@SP" + "\n");
                        writer.write("A=M-1" + "\n");
                        writer.write("D=M" + "\n");

                        writer.write("// RAM[" + s + "] = RAM[SP]" + "\n");
                        writer.write("@SP" + "\n");
                        writer.write("M=M-1" + "\n");
                        writer.write("@" + s + "\n");
                        writer.write("M=D" + "\n");
                        break;
                    default:// local argument this and that
                        // get the symbolic name of segment
                        seg = segmentPointer(seg);
                        writer.write("// addr = " + seg + " " + index + "\n");
                        writer.write("@" + index + "\n");
                        writer.write("D=A" + "\n");
                        writer.write("@" + seg + "\n");
                        writer.write("D=D+M" + "\n");
                        // save addr in RAM[R13]
                        writer.write("// R13 = addr" + "\n");
                        writer.write("@R13" + "\n");
                        writer.write("M=D" + "\n");
                        // get RAM[SP]
                        writer.write("// D = RAM[SP]" + "\n");
                        writer.write("@SP" + "\n");
                        writer.write("A=M-1" + "\n");
                        writer.write("D=M" + "\n");

                        writer.write("// RAM[addr] = RAM[SP] (saved in R13)" + "\n");
                        writer.write("@SP" + "\n");
                        writer.write("M=M-1" + "\n");
                        writer.write("@R13" + "\n");
                        writer.write("A=M" + "\n");
                        writer.write("M=D" + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // returns the segment in symbolic syntax
    private String segmentPointer(String seg) {
        switch (seg) {
            case "local":
                return "LCL";
            case "argument":
                return "ARG";
            case "this":
                return "THIS";
            case "that":
                return "THAT";
            default:
                return null;
        }
    }

    public void close() throws IOException {
        writer.close();
    }
}
