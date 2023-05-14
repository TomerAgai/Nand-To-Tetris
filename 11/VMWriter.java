import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The vm writer is meant for writing command to the vm output file
 */
public class VMWriter {
    BufferedWriter writer;

    public VMWriter(String input) {
        try {
            writer = new BufferedWriter(new FileWriter(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePush(String segment, int index) {
        try {
            writer.write("push " + segment + " " + index + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePop(String segment, int index) {
        try {
            writer.write("pop " + segment + " " + index + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeArithmetic(String command) {
        try {
            writer.write(command + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLabel(String label, int counter) {
        try {
            writer.write("label " + label + counter + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeGoto(String label, int counter) {
        try {
            writer.write("goto " + label + counter + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeIf(String label, int counter) {
        try {
            writer.write("if-goto " + label + counter + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCall(String name, int nArgs) {
        try {
            writer.write("call " + name + " " + nArgs + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFunction(String name, int nLocals) {
        try {
            writer.write("function " + name + " " + nLocals + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeReturn() {
        try {
            writer.write("return" + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // close the buffered writer
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}