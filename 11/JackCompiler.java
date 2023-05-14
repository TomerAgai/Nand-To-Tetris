import java.io.File;

/**
 * The Jack Compiler initiate and activate the Compilation Engine, which create
 * vm file/s from the given jack file/s
 */
public class JackCompiler {
    /**
     * 
     * @param args which is the absolute path to the file/directory that needs to be
     *             compiled
     */
    public static void main(String[] args) {
        File output;
        File input = new File(args[0]);
        CompilationEngine engine;
        if (input.isDirectory()) {
            // case of directory that may contain more than one jack file
            for (File f : input.listFiles()) {
                if (f.getName().endsWith("jack")) {
                    output = new File(f.getAbsolutePath().replace("jack", "vm"));
                    engine = new CompilationEngine(f, output);
                    engine.compileClass();
                    engine.writer.close();
                }
            }
        } else {// case of one file
            output = new File(args[0].replace("jack", "vm"));
            engine = new CompilationEngine(input, output);
            engine.compileClass();
            engine.writer.close();
        }
    }

}
