import java.io.File;
import java.io.IOException;

/**
 * build a VM translator using:
 * Parser to run through a given .vm file
 * CodeWriter to check each line and translate it to hack assembly code
 * 
 * @param file of .vm type
 *             outputs a .asm file in the same folder as the source file (with
 *             the same name)
 */
public class VMTranslator {
    protected static CodeWriter writer;

    public static void main(String[] args) {
        try {
            String path;
            // construct A code writer that writes to an assembly file
            File fileVM = new File(args[0]);
            if (args[0].contains(".vm")) {
                path = args[0].replace(".vm", ".asm");
                writer = new CodeWriter(new File(path));
                translator(writer, fileVM);
            } else {
                path = args[0] + "/" + fileVM.getName() + ".asm";
                // create an array of file names in case we get a folder as an argument
                File[] files = fileVM.listFiles();
                writer = new CodeWriter(new File(path));
                // check for Sys file to go thorugh first
                for (File fileName : files) {
                    if (fileName.getName().contains("Sys")) {
                        writer.writeInit();
                        break;
                    }
                }
                String name;
                files = fileVM.listFiles();
                for (File fileName : files) {
                    // translate .vm files only
                    name = fileName.getName();
                    if (name.endsWith(".vm")) {
                        translator(writer, fileName);
                    }
                }
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void translator(CodeWriter writer, File source) {

        Parser parser = new Parser(source);
        writer.setFileName(source);
        String type;// command type
        while (parser.hasMoreLines()) {
            parser.advance();
            try { // print the whole command for documentation
                writer.writer.write("// " + parser.currentLine + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            type = parser.commandType();
            String arg1 = parser.arg1();
            int arg2;
            switch (type) {
                case "push":
                case "pop":
                    arg2 = parser.arg2();
                    writer.WritePushPop(type, arg1, arg2);
                    break;
                case "arithmetic":
                    writer.WriteArithmetic(parser.currentLine);
                    break;
                case "label":
                    writer.writeLabel(arg1);
                    break;
                case "goto":
                    writer.writeGoTo(arg1);
                    break;
                case "if-goto":
                    writer.writeIfGoTo(arg1);
                    break;
                case "call":
                    arg2 = parser.arg2();
                    writer.writeCall(arg1, arg2);
                    break;
                case "function":
                    arg2 = parser.arg2();
                    writer.writeFunction(arg1, arg2);
                    break;
                case "return":
                    writer.writeReturn();
                    break;
            }
            CodeWriter.symbolCount++;
        }

    }

}
