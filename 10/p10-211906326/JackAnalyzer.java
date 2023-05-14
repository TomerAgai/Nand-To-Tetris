import java.io.File;

/**
 * The JackAnalyzer initiate and activate the Compilation Engine, which create
 * xml file/s from
 * the given jack file/s
 */
public class JackAnalyzer {
    /**
     * 
     * @param args which is the absolute path to the file/directory that needs to be
     *             compiled
     */
    public static void main(String[] args) {
        File output;
        File input = new File(args[0]);
        CompilationEngine eng;
        if (input.isDirectory()) {
            // case of directory that may contain more than one jack file
            for (File f : input.listFiles()) {
                if (f.getName().endsWith("jack")) {
                    output = new File(input.getAbsolutePath().replace("jack", "xml3"));
                    eng = new CompilationEngine(f, output);
                    eng.compileClass();
                    // close the writer
                    eng.closeWriter();
                }
            }
        } else {// case of one file
            output = new File(args[0].replace("jack", "xml3"));
            eng = new CompilationEngine(input, output);
            eng.compileClass();
            // close the writer
            eng.closeWriter();
        }
    }

}
